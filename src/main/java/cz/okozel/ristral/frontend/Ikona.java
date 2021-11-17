package cz.okozel.ristral.frontend;

import com.vaadin.flow.component.html.Span;

public enum Ikona {
    VITEJTE("la la-bus"),
    O_RISTRALU("la la-info"),
    PREHLED("la la-chart-area");

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
