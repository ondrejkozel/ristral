package cz.okozel.ristral.frontend.views.prehled;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class HighlightCell extends VerticalLayout {

    private final Span text;
    private final Span badge;

    public HighlightCell(String titulek) {
        this(titulek, "");
    }

    public HighlightCell(String title, String text) {
        H2 h2 = new H2(title);
        this.text = new Span(text);
        //
        h2.addClassNames("font-normal", "m-0", "text-secondary", "text-xs");
        this.text.addClassNames("font-semibold", "text-3xl");
        //
        add(h2, this.text);
        addClassName("p-l");
        setPadding(false);
        setSpacing(false);
        //
        badge = new Span();
    }

    public HighlightCell(String title, String text, String badgeTheme) {
        this(title, text);
        badge.getElement().getThemeList().add(badgeTheme);
        add(badge);
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void setBadgeText(String text) {
        badge.setText(text);
    }

}
