package cz.okozel.ristral.frontend.views.lineEdit;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import cz.okozel.ristral.backend.entity.lines.EmptyRouteException;
import cz.okozel.ristral.backend.entity.lines.LineRouteCarrier;
import cz.okozel.ristral.backend.entity.lines.LineRouteLinkData;
import cz.okozel.ristral.backend.entity.routes.Route;
import cz.okozel.ristral.backend.entity.routes.RouteUtils;
import cz.okozel.ristral.backend.entity.zastavky.Zastavka;
import cz.okozel.ristral.frontend.LineAwesomeIcon;
import cz.okozel.ristral.frontend.presenters.linkyCrud.LinkyCrudPresenter;
import cz.okozel.ristral.frontend.views.crud.GenericCrudView;

import java.util.List;
import java.util.function.Consumer;

public class LineEditView extends VerticalLayout {

    public LineEditView() {
        add(buildMenuBar());
        add(buildCurrentLineLabel());
        add(buildRouteBoards());
        add(buildNoRoutesLabel());
        //
        buildCrud();
    }

    private MenuItem newRoute;

    public void setNewRouteMenuItemVisible(boolean visible) {
        newRoute.setVisible(visible);
    }

    public void addNewRouteMenuItemAction(ComponentEventListener<ClickEvent<MenuItem>> listener) {
        newRoute.addClickListener(listener);
    }

    private MenuBar buildMenuBar() {
        MenuBar menuBar = new MenuBar();
        newRoute = menuBar.addItem("Nová trasa");
        return menuBar;
    }

    private Label currentLineLabel;

    public void setCurrentLineLabel(String name) {
        currentLineLabel.setText(String.format("Nyní upravujete trasy linky %s.", name));
    }

    private Label buildCurrentLineLabel() {
        currentLineLabel = new Label();
        return currentLineLabel;
    }

    private VerticalLayout visibleRoutesLayout;
    private VerticalLayout invisibleRoutesLayout;

    public void setVisibleRoutesLayoutVisible(boolean visible) {
        visibleRoutesLayout.setVisible(visible);
    }
    public void setInvisibleRoutesLayoutVisible(boolean visible) {
        invisibleRoutesLayout.setVisible(visible);
    }

    private HorizontalLayout visibleRoutesBoard;
    private HorizontalLayout invisibleRoutesBoard;

    private Component buildRouteBoards() {
        visibleRoutesLayout = new VerticalLayout();
        H2 visibleRoutesHeadline = new H2("Aktivní trasy");
        visibleRoutesBoard = new HorizontalLayout();
        visibleRoutesBoard.addClassName("routes-board");
        visibleRoutesLayout.add(visibleRoutesHeadline, visibleRoutesBoard);
        //
        invisibleRoutesLayout = new VerticalLayout();
        H2 invisibleRoutesHeadline = new H2("Neaktivní trasy");
        invisibleRoutesBoard = new HorizontalLayout();
        invisibleRoutesBoard.addClassName("routes-board");
        invisibleRoutesLayout.add(invisibleRoutesHeadline, invisibleRoutesBoard);
        //
        VerticalLayout finalLayout = new VerticalLayout(visibleRoutesLayout, invisibleRoutesLayout);
        finalLayout.setPadding(false);
        return finalLayout;
    }

    public void populateVisibleRoutes(List<LineRouteCarrier> visibleRoutes) {
        populateRoutes(visibleRoutes, visibleRoutesBoard);
    }

    public void populateInvisibleRoutes(List<LineRouteCarrier> invisibleRoutes) {
        populateRoutes(invisibleRoutes, invisibleRoutesBoard);
    }

    private void populateRoutes(List<LineRouteCarrier> routes, HorizontalLayout board) {
        board.removeAll();
        board.add(routes.stream().map(RouteProfile::new).toArray(RouteProfile[]::new));
    }

    private Label noRoutesLabel;

    public void setNoRoutesLabelVisible(boolean visible) {
        noRoutesLabel.setVisible(visible);
    }

    private Label buildNoRoutesLabel() {
        noRoutesLabel = new Label("Prozatím nebyly vytvořeny žádné trasy. Přidejte nějakou kliknutím na tlačítko nahoře. \uD83D\uDE0A");
        return noRoutesLabel;
    }

    private Consumer<LineRouteCarrier> setRouteVisible, setRouteInvisible;

    public void setSetRouteVisibleMenuItemAction(Consumer<LineRouteCarrier> setRouteVisible) {
        this.setRouteVisible = setRouteVisible;
    }

    public void setSetRouteInvisibleMenuItemAction(Consumer<LineRouteCarrier> setRouteInvisible) {
        this.setRouteInvisible = setRouteInvisible;
    }

    private Crud<LineRouteCarrier> crud;

    public Crud<LineRouteCarrier> getCrud() {
        return crud;
    }

    private void buildCrud() {
        crud = new Crud<>();
        crud.setBeanType(LineRouteCarrier.class);
        crud.setI18n(GenericCrudView.buildCrudI18n("Nová trasa", "Upravit trasu", "Odstranit trasu"));
        //
        Div div = new Div(crud);
        div.addClassName("display-none");
        add(div);
    }

    public void showUnknownLineDialog() {
        Dialog dialog = new Dialog();
        H3 headline = new H3("Neznámá linka \uD83D\uDE22");
        //
        Paragraph paragraph = new Paragraph();
        paragraph.setText("Lituji, linka s tímto označením buď neexistuje, nebo k ní nemáte přístup. Je přihlášený správný uživatel?");
        paragraph.setMaxWidth("400px");
        //
        Button button = new Button("Všechny linky", LineAwesomeIcon.LINKA.getSpan());
        button.addClickListener(event -> closeAndRedirect(dialog));
        //
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.add(button);
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        //
        dialog.add(headline, paragraph, buttonLayout);
        dialog.setModal(true);
        dialog.addDialogCloseActionListener(event -> closeAndRedirect(dialog));
        dialog.open();
    }

    private void closeAndRedirect(Dialog dialog) {
        UI.getCurrent().navigate(LinkyCrudPresenter.class);
        dialog.close();
    }

    private class RouteProfile extends VerticalLayout {

        public RouteProfile(LineRouteCarrier routeCarrier) {
            addClassName("route-profile");
            //
            if (routeCarrier.isVisible()) addClassName("route-profile-visible");
            else addClassName("route-profile-invisible");
            //
            buildHeader(routeCarrier);
            //
            try {
                buildInfo(routeCarrier.buildLineRoute().getData());
            } catch (EmptyRouteException e) {
                buildNoStopsInfo();
            }
        }

        private void buildHeader(LineRouteCarrier routeView) {
            Label headerLabel = new Label();
            headerLabel.add(VaadinIcon.ROAD.create());
            headerLabel.add(" " + routeView.getName());
            headerLabel.addClassName("tucne");
            //
            Button headerButton = new Button(VaadinIcon.ELLIPSIS_DOTS_V.create());
            headerButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            headerButton.addClassName("edit-route-button");
            //
            buildContextMenu(headerButton, routeView);
            //
            Span span = new Span();
            //
            HorizontalLayout header = new HorizontalLayout(headerLabel, span, headerButton);
            header.addClassName("m-0");
            header.setFlexGrow(1, span);
            header.setWidthFull();
            header.setAlignItems(Alignment.CENTER);
            add(header);
        }

        private void buildNoStopsInfo() {
            add(new Label("Trasa nemá žádné zastávky."));
            addClassName("no-stops");
        }

        private void buildContextMenu(Button headerButton, LineRouteCarrier routeView) {
            ContextMenu contextMenu = new ContextMenu(headerButton);
            contextMenu.setOpenOnClick(true);
            //
            contextMenu.addItem("Upravit...", event -> crud.edit(routeView, Crud.EditMode.EXISTING_ITEM));
            if (routeView.isVisible()) contextMenu.addItem("Přepnou na neaktivní", event -> setRouteInvisible.accept(routeView));
            else contextMenu.addItem("Přepnout na aktivní", event -> setRouteVisible.accept(routeView));
        }

        private void buildInfo(Route<Zastavka, LineRouteLinkData> route) {
            Label start = new Label("výchozí ");
            start.add(LineAwesomeIcon.ZASTAVKA.getSpan());
            start.add(String.format(": %s", route.from().getNazev()));
            //
            Label end = new Label("konečná ");
            end.add(LineAwesomeIcon.ZASTAVKA.getSpan());
            end.add(String.format(": %s", route.to().getNazev()));
            //
            Label count = new Label("počet ");
            count.add(LineAwesomeIcon.ZASTAVKA.getSpan());
            count.add(String.format(": %s", route.length()));
            //
            Label duration = new Label(String.format("délka: %s", RouteUtils.durationToString(RouteUtils.computeRouteDuration(route))));
            //
            add(start, end, count, duration);
        }
    }
}
