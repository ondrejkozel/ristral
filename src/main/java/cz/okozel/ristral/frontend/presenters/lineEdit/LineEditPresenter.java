package cz.okozel.ristral.frontend.presenters.lineEdit;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import cz.okozel.ristral.backend.entity.lines.Line;
import cz.okozel.ristral.backend.entity.lines.LineRouteCarrier;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.security.PrihlasenyUzivatel;
import cz.okozel.ristral.backend.service.entity.LineRouteService;
import cz.okozel.ristral.backend.service.entity.LineService;
import cz.okozel.ristral.frontend.LineAwesomeIcon;
import cz.okozel.ristral.frontend.MainLayout;
import cz.okozel.ristral.frontend.presenters.Presenter;
import cz.okozel.ristral.frontend.presenters.linkyCrud.LinkyCrudPresenter;
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
    private Uzivatel currentUser;

    public LineEditPresenter(PrihlasenyUzivatel prihlasenyUzivatel, LineService lineService, LineRouteService lineRouteService) {
        //noinspection OptionalGetWithoutIsPresent
        currentUser = prihlasenyUzivatel.getPrihlasenyUzivatel().get();
        this.lineService = lineService;
        this.lineRouteService = lineRouteService;
        //
        getContent().setSetRouteVisibleMenuItemAction(routeCarrier -> {
            routeCarrier.setVisible(true);
            lineRouteService.save(routeCarrier);
            newLineActive();
        });
        getContent().setSetRouteInvisibleMenuItemAction(routeCarrier -> {
            routeCarrier.setVisible(false);
            lineRouteService.save(routeCarrier);
            newLineActive();
        });
        getContent().setDeleteRouteMenuItemAction(routeCarrier -> {
            lineRouteService.delete(routeCarrier);
            newLineActive();
        });
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        Optional<Line> optionalLine = lineService.find(Long.parseLong(parameter));
        if (optionalLine.isPresent() && optionalLine.get().getSchema().equals(currentUser.getSchema())) {
            currentLine = optionalLine.get();
            //
            newLineActive();
        }
        else {
            currentLine = null;
            showUnknownLineDialog();
        }
    }

    private void newLineActive() {
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

    private void showUnknownLineDialog() {
        Dialog dialog = new Dialog();
        H3 headline = new H3("Neznámá linka \uD83D\uDE22");
        //
        Paragraph paragraph = new Paragraph();
        paragraph.setText("Lituji, linka s tímto označením buď neexistuje, nebo k ní nemáte přístup. Je přihlášený správný uživatel?");
        paragraph.setMaxWidth("400px");
        //
        Button button = new Button("Všechny linky", LineAwesomeIcon.LINKA.getSpan());
        button.addClickListener(event -> {
            UI.getCurrent().navigate(LinkyCrudPresenter.class);
            dialog.close();
        });
        //
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(button);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        //
        dialog.add(headline, paragraph, buttonLayout);
        dialog.setModal(false);
        dialog.open();
    }
}
