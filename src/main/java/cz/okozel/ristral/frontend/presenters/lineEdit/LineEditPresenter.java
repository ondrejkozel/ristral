package cz.okozel.ristral.frontend.presenters.lineEdit;

import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import cz.okozel.ristral.backend.entity.lines.Line;
import cz.okozel.ristral.backend.entity.lines.LineRouteCarrier;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.security.PrihlasenyUzivatel;
import cz.okozel.ristral.backend.service.entity.LineRouteService;
import cz.okozel.ristral.backend.service.entity.LineService;
import cz.okozel.ristral.frontend.MainLayout;
import cz.okozel.ristral.frontend.presenters.Presenter;
import cz.okozel.ristral.frontend.views.lineEdit.LineEditView;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PageTitle("Správa tras")
@com.vaadin.flow.router.Route(value = "lines/edit", layout = MainLayout.class)
@PermitAll
public class LineEditPresenter extends Presenter<LineEditView> implements HasUrlParameter<String> {

    private final LineService lineService;
    private final LineRouteService lineRouteService;
    private Line currentLine;
    private final Uzivatel currentUser;

    public LineEditPresenter(PrihlasenyUzivatel prihlasenyUzivatel, LineService lineService, LineRouteService lineRouteService) {
        //noinspection OptionalGetWithoutIsPresent
        currentUser = prihlasenyUzivatel.getPrihlasenyUzivatel().get();
        this.lineService = lineService;
        this.lineRouteService = lineRouteService;
        //
        getContent().setSetRouteVisibleMenuItemAction(routeCarrier -> {
            routeCarrier.setVisible(true);
            lineRouteService.save(routeCarrier);
            refresh();
        });
        getContent().setSetRouteInvisibleMenuItemAction(routeCarrier -> {
            routeCarrier.setVisible(false);
            lineRouteService.save(routeCarrier);
            refresh();
        });
        getContent().getCrud().addDeleteListener(event -> {
            lineRouteService.delete(event.getItem());
            refresh();
        });
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        Optional<Line> optionalLine = lineService.find(Long.parseLong(parameter));
        if (optionalLine.isPresent() && optionalLine.get().getSchema().equals(currentUser.getSchema())) {
            currentLine = optionalLine.get();
            //
            refresh();
        }
        else {
            currentLine = null;
            disableAll();
            getContent().showUnknownLineDialog();
        }
    }

    private void disableAll() {
        getContent().setVisibleRoutesLayoutVisible(false);
        getContent().setInvisibleRoutesLayoutVisible(false);
        getContent().setNoRoutesLabelVisible(false);
        getContent().setNewRouteMenuItemVisible(false);
    }

    private void refresh() {
        getContent().setCurrentLineLabel(currentLine.getLabel());
        //
        var allLineRoutes = new ArrayList<>(new ArrayList<>(lineRouteService.findAll(currentLine)));
        var visibleLineRoutes = allLineRoutes.stream().filter(LineRouteCarrier::isVisible).collect(Collectors.toList());
        allLineRoutes.removeAll(visibleLineRoutes);
        var invisibleLineRoutes = List.copyOf(allLineRoutes);
        //
        getContent().setVisibleRoutesLayoutVisible(!visibleLineRoutes.isEmpty());
        getContent().setInvisibleRoutesLayoutVisible(!invisibleLineRoutes.isEmpty());
        //
        getContent().populateVisibleRoutes(visibleLineRoutes);
        getContent().populateInvisibleRoutes(invisibleLineRoutes);
        //
        getContent().setNoRoutesLabelVisible(visibleLineRoutes.isEmpty() && invisibleLineRoutes.isEmpty());
    }
}
