package cz.okozel.ristral.frontend.views.prehled;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Highlight extends VerticalLayout {

    Span text;
    private boolean jenomUAdminu;

    public Highlight(String titulek, boolean jenomUAdminu) {
        this(titulek, "", jenomUAdminu);
    }

    public Highlight(String titulek, String hodnota, boolean jenomUAdminu) {
        this.jenomUAdminu = jenomUAdminu;
        H2 h2 = new H2(titulek);
        text = new Span(hodnota);
        //
        h2.addClassNames("font-normal", "m-0", "text-secondary", "text-xs");
        text.addClassNames("font-semibold", "text-3xl");
        //
        add(h2, text);
        addClassName("p-l");
        setPadding(false);
        setSpacing(false);
    }

    public void setHodnota(String hodnota) {
        text.setText(hodnota);
    }

    public boolean isJenomUAdminu() {
        return jenomUAdminu;
    }

}
