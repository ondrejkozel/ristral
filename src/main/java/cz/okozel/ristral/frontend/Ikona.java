package cz.okozel.ristral.frontend;

import com.vaadin.flow.component.html.Span;

public enum Ikona {
    VITEJTE("la la-bus"),
    PREHLED("la la-chart-area"),
    ZASTAVKY("las la-map-pin"),
    O_RISTRALU("la la-info");

    private String lineAwesome;

    Ikona(String lineAwesome) {
        this.lineAwesome = lineAwesome;
    }

    public Span get() {
        Span span = new Span();
        span.addClassNames(lineAwesome);
        return span;
    }
}
