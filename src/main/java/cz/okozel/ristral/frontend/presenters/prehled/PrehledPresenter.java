package cz.okozel.ristral.frontend.presenters.prehled;

import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.uzivatele.Role;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.entity.vozidla.TypVozidla;
import cz.okozel.ristral.backend.entity.zastavky.RezimObsluhy;
import cz.okozel.ristral.backend.security.PrihlasenyUzivatel;
import cz.okozel.ristral.backend.service.entity.*;
import cz.okozel.ristral.frontend.MainLayout;
import cz.okozel.ristral.frontend.presenters.Presenter;
import cz.okozel.ristral.frontend.views.prehled.PrehledView;

import javax.annotation.security.PermitAll;
import java.util.List;

@PageTitle("Přehled")
@Route(value = "prehled", layout = MainLayout.class)
@PermitAll
public class PrehledPresenter extends Presenter<PrehledView> implements BeforeEnterObserver {

    private final Uzivatel loggedOnUser;
    private final Schema aktSchema;
    private final ZastavkaService zastavkaService;
    private final VozidloService vozidloService;
    private final TypVozidlaService typVozidlaService;
    private final UzivatelService uzivatelService;
    private final RezimObsluhyService rezimObsluhyService;

    private long stopCount, vehicleCount, lineCount, userCount;

    public PrehledPresenter(PrihlasenyUzivatel prihlasenyUzivatel, ZastavkaService zastavkaService, VozidloService vozidloService, TypVozidlaService typVozidlaService, UzivatelService uzivatelService, RezimObsluhyService rezimObsluhyService) {
        //noinspection OptionalGetWithoutIsPresent
        this.loggedOnUser = prihlasenyUzivatel.getPrihlasenyUzivatel().get();
        this.aktSchema = loggedOnUser.getSchema();
        this.zastavkaService = zastavkaService;
        this.vozidloService = vozidloService;
        this.typVozidlaService = typVozidlaService;
        this.uzivatelService = uzivatelService;
        this.rezimObsluhyService = rezimObsluhyService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        stopCount = zastavkaService.count(aktSchema);
        vehicleCount = vozidloService.count(aktSchema);
        userCount = uzivatelService.count(aktSchema);
        //
        PrehledView content = getContent();
        configureHighlights(content);
        content.configureVehicleDistributionChart(this::configureVehicleDistributionChart);
        content.configureServiceModeDistributionChart(this::configureServiceModeDistributionChart);
    }

    private void configureHighlights(PrehledView content) {
        content.setHighlightText(PrehledView.DashboardHighlight.STOPS, String.valueOf(stopCount));
        content.setHighlightText(PrehledView.DashboardHighlight.VEHICLES, String.valueOf(vehicleCount));
        if (loggedOnUser.getRole() == Role.ADMIN_ORG || loggedOnUser.getRole() == Role.SUPERADMIN_ORG) {
            content.setHighlightText(PrehledView.DashboardHighlight.USERS, String.valueOf(userCount));
            content.setHighlightBadgeText(PrehledView.DashboardHighlight.USERS, computeRegularUserPercentage() + " % řadových účtů");
        }
        else content.setHiglightVisible(PrehledView.DashboardHighlight.USERS, false);
    }

    /**
     * se současným návrhem nelze lépe vypočítat podíl řadových účtů – nelze countTypUzivatele v databázi
     */
    private int computeRegularUserPercentage() {
        long regularUserCount = uzivatelService.findAll(aktSchema).stream()
                .filter(uzivatel -> uzivatel.getRole() == Role.UZIVATEL_ORG)
                .count();
        return (int) (regularUserCount * 100 / userCount);
    }

    private void configureVehicleDistributionChart(Configuration configuration) {
        DataSeries series = new DataSeries();
        List<TypVozidla> vehicleTypes = typVozidlaService.findAll(aktSchema);
        vehicleTypes.forEach(typVozidla -> series.add(getPercentageDataItem(typVozidla.getNazev(), vozidloService.count(typVozidla), vehicleCount)));
        series.setName("%");
        configuration.addSeries(series);
    }

    private void configureServiceModeDistributionChart(Configuration configuration) {
        DataSeries series = new DataSeries();
        List<RezimObsluhy> serviceModes = rezimObsluhyService.findAll(aktSchema);
        serviceModes.forEach(rezimObsluhy -> series.add(getPercentageDataItem(rezimObsluhy.getNazev(), zastavkaService.count(rezimObsluhy), stopCount)));
        series.setName("%");
        configuration.addSeries(series);
    }

    private DataSeriesItem getPercentageDataItem(String title, long count, long whole) {
        return new DataSeriesItem(title, count / (float) whole * 100);
    }

}
