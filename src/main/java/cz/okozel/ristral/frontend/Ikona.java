package cz.okozel.ristral.frontend;

import com.vaadin.flow.component.html.Span;

public enum Ikona {
    BUS("la la-bus"),
    GRAF("la la-chart-area"),
    ZASTAVKA("las la-map-pin"),
    INFO("la la-info");

    private String lineAwesome;

    Ikona(String lineAwesome) {
        this.lineAwesome = lineAwesome;
    }

    public Span getSpan() {
        Span span = new Span();
        span.addClassNames(lineAwesome);
        return span;
    }
}
