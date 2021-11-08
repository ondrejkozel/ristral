package cz.okozel.ristral.frontend.views.vitejte;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;

@PageTitle("Vítejte")
public class VitejteView extends VerticalLayout {

    public VitejteView() {
        add(new H2("Ristral"));
        add(new Paragraph("Vítejte v Ristralu! Ristral je řídicí informační systém dopravního podniku."));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
