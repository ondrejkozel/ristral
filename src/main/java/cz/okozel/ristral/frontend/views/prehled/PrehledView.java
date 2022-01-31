package cz.okozel.ristral.frontend.views.prehled;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.PlotOptionsPie;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import cz.okozel.ristral.backend.entity.trips.TripRouteCarrier;
import cz.okozel.ristral.frontend.views.prehled.ServiceHealth.Status;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.List;
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
        board.addRow(createSoonestTripsCell(), createServiceModeDistributionCell(), createVehicleTypeDistributionCell());
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

    private Grid<TripRouteCarrier> soonestTripsGrid;
    private CellHeader soonestTripsHeader;

    private Component createSoonestTripsCell() {
        soonestTripsHeader = new CellHeader("Nejbližší jízdy");
        //
        soonestTripsGrid = new Grid<>();
        soonestTripsGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        soonestTripsGrid.setAllRowsVisible(true);
        soonestTripsGrid.addColumn(tripRouteCarrier -> tripRouteCarrier.getTimeOfDeparture().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))).setHeader("Odjezd");
        soonestTripsGrid.addColumn(tripRouteCarrier -> tripRouteCarrier.getTimeOfArrival().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))).setHeader("Příjezd");
        soonestTripsGrid.addColumn(tripRouteCarrier -> tripRouteCarrier.getAssociatedTrip().getLineLabel()).setHeader("Linka");
        soonestTripsGrid.addColumn(tripRouteCarrier -> tripRouteCarrier.getAssociatedTrip().getVehicleName()).setHeader("Vozidlo");
        soonestTripsGrid.getColumns().forEach(tripRouteCarrierColumn -> tripRouteCarrierColumn.setAutoWidth(true));
        return new StandartCell(soonestTripsHeader, soonestTripsGrid);
    }

    public void setSoonestTripsGridItems(List<TripRouteCarrier> list) {
        soonestTripsGrid.setItems(list);
    }

    public void setSoonestTripsHeaderSubtitle(String subtitle) {
        soonestTripsHeader.setSubtitle(subtitle);
    }

    private void configureChart(Chart chart) {
        Configuration configuration = chart.getConfiguration();
        configuration.getExporting().setEnabled(true);
        //
        PlotOptionsPie plotOptionsPie = new PlotOptionsPie();
        plotOptionsPie.setInnerSize("60%");
        plotOptionsPie.setShowInLegend(true);
        plotOptionsPie.setAllowPointSelect(true);
        //
        configuration.getTooltip().setValueDecimals(1);
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
        CellHeader header = new CellHeader("Rozložení režimů obsluhy zastávek", "Množství zastávek s daným režimem obsluhy");
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
