package cz.okozel.ristral.frontend;

import com.vaadin.flow.component.html.Span;

public enum LineAwesomeIcon {
    BUS("la la-bus"),
    GRAF("la la-chart-area"),
    ZASTAVKA("las la-map-pin"),
    NASTAVENI("las la-cog"),
    INFO("la la-info"),
    UZIVATELE("las la-users"),
    UZIVATEL("las la-user"),
    ADMINISTRATOR("las la-user-edit"),
    SUPERADMINISTRATOR("las la-user-cog");

    private final String nazevTridy;

    LineAwesomeIcon(String nazevTridy) {
        this.nazevTridy = nazevTridy;
    }

    public Span getSpan() {
        Span span = new Span();
        span.setClassName(nazevTridy);
        return span;
    }

    public String getNazevTridy() {
        return nazevTridy;
    }

}
