package cz.okozel.ristral.frontend.views.lineEdit;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import cz.okozel.ristral.backend.entity.lines.LineRouteLinkData;
import cz.okozel.ristral.backend.entity.routes.NamedView;
import cz.okozel.ristral.backend.entity.routes.Route;
import cz.okozel.ristral.backend.entity.routes.RouteUtils;
import cz.okozel.ristral.backend.entity.zastavky.Zastavka;
import cz.okozel.ristral.frontend.LineAwesomeIcon;

import java.util.List;

public class LineEditView extends VerticalLayout {

    public LineEditView() {
        add(buildMenuBar());
        add(buildCurrentLineLabel());
        add(buildRouteBoards());
        add(buildNoRoutesLabel());
    }

    private MenuItem newRoute;

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

    private Board visibleRoutesBoard;

    private Board invisibleRoutesBoard;
    private Component buildRouteBoards() {
        visibleRoutesLayout = new VerticalLayout();
        H2 visibleRoutesHeadline = new H2("Aktivní trasy");
        visibleRoutesBoard = new Board();
        visibleRoutesBoard.addClassName("routes-board");
        visibleRoutesLayout.add(visibleRoutesHeadline, visibleRoutesBoard);
        //
        invisibleRoutesLayout = new VerticalLayout();
        H2 invisibleRoutesHeadline = new H2("Neaktivní trasy");
        invisibleRoutesBoard = new Board();
        invisibleRoutesBoard.addClassName("routes-board");
        invisibleRoutesLayout.add(invisibleRoutesHeadline, invisibleRoutesBoard);
        //
        VerticalLayout finalLayout = new VerticalLayout(visibleRoutesLayout, invisibleRoutesLayout);
        finalLayout.setPadding(false);
        return finalLayout;
    }

    public void populateVisibleRoutes(List<NamedView<Route<Zastavka, LineRouteLinkData>>> visibleRoutes) {
        populateRoutes(visibleRoutes, visibleRoutesBoard);
    }

    public void populateInvisibleRoutes(List<NamedView<Route<Zastavka, LineRouteLinkData>>> invisibleRoutes) {
        populateRoutes(invisibleRoutes, invisibleRoutesBoard);
    }

    private void populateRoutes(List<NamedView<Route<Zastavka, LineRouteLinkData>>> routes, Board board) {
        board.addRow(routes.stream().map(RouteProfile::new).toArray(RouteProfile[]::new));
    }

    private Label noRoutesLabel;

    public void setNoRoutesLabelVisible(boolean visible) {
        noRoutesLabel.setVisible(visible);
    }

    private Label buildNoRoutesLabel() {
        noRoutesLabel = new Label("Prozatím nebyly vytvořeny žádné trasy. Přidejte nějakou kliknutím na tlačítko nahoře. \uD83D\uDE0A");
        return noRoutesLabel;
    }

    private static class RouteProfile extends VerticalLayout {


        public RouteProfile(NamedView<Route<Zastavka, LineRouteLinkData>> routeView) {
            addClassName("route-profile");
            setSizeFull();
            //
            if (routeView.isVisible()) addClassName("route-profile-visible");
            else addClassName("route-profile-invisible");
            //
            Label header = new Label();
            header.add(VaadinIcon.ROAD.create());
            header.add(" " + routeView.getName());
            header.addClassName("tucne");
            add(header);
            //
            buildInfo(routeView.getData());
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
