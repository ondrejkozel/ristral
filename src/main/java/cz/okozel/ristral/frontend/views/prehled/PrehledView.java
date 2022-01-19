package cz.okozel.ristral.frontend.views.prehled;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import cz.okozel.ristral.frontend.views.prehled.ServiceHealth.Status;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class PrehledView extends Main {

    private final Map<DashboardHighlight, HighlightCell> highlights;

    public enum DashboardHighlight {
        STOPS,
        VEHICLES,
        LINES,
        USERS
    }

    public PrehledView() {
        highlights = new HashMap<>();
        highlights.put(DashboardHighlight.STOPS, new HighlightCell("Počet zastávek"));
        highlights.put(DashboardHighlight.VEHICLES, new HighlightCell("Počet vozidel"));
        highlights.put(DashboardHighlight.LINES, new HighlightCell("Počet linek"));
        highlights.put(DashboardHighlight.USERS, new HighlightCell("Počet uživatelů", "", "badge"));
        //
        addClassName("prehled-view");
        Board board = new Board();
        Row highlightsRow = board.addRow();
        for (DashboardHighlight highlight : DashboardHighlight.values()) highlightsRow.add(highlights.get(highlight));
        board.addRow(createServiceHealth(), createServiceModeDistributionCell(), createVehicleTypeDistributionCell());
        add(board);
    }

    public void setHiglightVisible(DashboardHighlight highlight, boolean visible) {
        highlights.get(highlight).setVisible(visible);
    }

    public void setHighlightText(DashboardHighlight highlight, String value) {
        highlights.get(highlight).setText(value);
    }

    public void setHighlightBadgeText(DashboardHighlight highlight, String value) {
        highlights.get(highlight).setBadgeText(value);
    }

    private Component createServiceHealth() {
        CellHeader header = new CellHeader("Service health", "Input / output");

        // Grid
        Grid<ServiceHealth> grid = new Grid();
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setAllRowsVisible(true);

        grid.addColumn(new ComponentRenderer<>(serviceHealth -> {
            Span status = new Span();
            String statusText = getStatusDisplayName(serviceHealth);
            status.getElement().setAttribute("aria-label", "Status: " + statusText);
            status.getElement().setAttribute("title", "Status: " + statusText);
            status.getElement().getThemeList().add(getStatusTheme(serviceHealth));
            return status;
        })).setHeader("").setFlexGrow(0).setAutoWidth(true);
        grid.addColumn(ServiceHealth::getCity).setHeader("City").setFlexGrow(1);
        grid.addColumn(ServiceHealth::getInput).setHeader("Input").setAutoWidth(true).setTextAlign(ColumnTextAlign.END);
        grid.addColumn(ServiceHealth::getOutput).setHeader("Output").setAutoWidth(true)
                .setTextAlign(ColumnTextAlign.END);

        grid.setItems(new ServiceHealth(Status.EXCELLENT, "Münster", 324, 1540),
                new ServiceHealth(Status.OK, "Cluj-Napoca", 311, 1320),
                new ServiceHealth(Status.FAILING, "Ciudad Victoria", 300, 1219));

        return new StandartCell(header, grid);
    }

    private void configureChart(Chart chart) {
        Configuration configuration = chart.getConfiguration();
        configuration.getExporting().setEnabled(true);
        //
        PlotOptionsPie plotOptionsPie = new PlotOptionsPie();
        plotOptionsPie.setInnerSize("60%");
        //
        configuration.setPlotOptions(plotOptionsPie);
    }

    private Chart vehicleTypeDistributionChart;

    private Component createVehicleTypeDistributionCell() {
        CellHeader header = new CellHeader("Rozložení typů vozidel", "Množství vozidel daného typu");
        //
        vehicleTypeDistributionChart = new Chart(ChartType.PIE);
        configureChart(vehicleTypeDistributionChart);
        //
        return new StandartCell(header, vehicleTypeDistributionChart);
    }

    public void configureVehicleDistributionChart(Consumer<Configuration> configurationConsumer) {
        configurationConsumer.accept(vehicleTypeDistributionChart.getConfiguration());
    }

    private Chart serviceModeDistributionChart;

    private Component createServiceModeDistributionCell() {
        CellHeader header = new CellHeader("Rozložení režimů obsluhy", "Množství zastávek s daným režimem obsluhy");
        //
        serviceModeDistributionChart = new Chart(ChartType.PIE);
        configureChart(serviceModeDistributionChart);
        //
        return new StandartCell(header, serviceModeDistributionChart);
    }

    public void configureServiceModeDistributionChart(Consumer<Configuration> configurationConsumer) {
        configurationConsumer.accept(serviceModeDistributionChart.getConfiguration());
    }


    private static class StandartCell extends VerticalLayout {
        public StandartCell(CellHeader header, Component body) {
            add(header, body);
            addClassName("p-l");
            setPadding(false);
            setSpacing(false);
            getElement().getThemeList().add("spacing-l");
        }
    }

    private static class CellHeader extends HorizontalLayout {
        H2 title;
        Span subtitle;

        CellHeader(String titleText) {
            this(titleText, "");
        }

        CellHeader(String titleText, String subtitleText) {
            title = new H2(titleText);
            title.addClassNames("text-xl", "m-0");
            //
            subtitle = new Span(subtitleText);
            subtitle.addClassNames("text-secondary", "text-xs");
            //
            VerticalLayout column = new VerticalLayout(title, subtitle);
            column.setPadding(false);
            column.setSpacing(false);
            //
            add(column);
            setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
            setSpacing(false);
            setWidthFull();
        }

        void setTitle(String titleText) {
            title.setTitle(titleText);
        }

        void setSubtitle(String subtitleText) {
            subtitle.setText(subtitleText);
        }

    }

    private String getStatusDisplayName(ServiceHealth serviceHealth) {
        Status status = serviceHealth.getStatus();
        if (status == Status.OK) {
            return "Ok";
        } else if (status == Status.FAILING) {
            return "Failing";
        } else if (status == Status.EXCELLENT) {
            return "Excellent";
        } else {
            return status.toString();
        }
    }

    private String getStatusTheme(ServiceHealth serviceHealth) {
        Status status = serviceHealth.getStatus();
        String theme = "badge primary small";
        if (status == Status.EXCELLENT) {
            theme += " success";
        } else if (status == Status.FAILING) {
            theme += " error";
        }
        return theme;
    }

}
