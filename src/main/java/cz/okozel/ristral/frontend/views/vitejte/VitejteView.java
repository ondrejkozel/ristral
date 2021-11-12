package cz.okozel.ristral.frontend.views.vitejte;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import cz.okozel.ristral.frontend.customComponents.PrihlasitSeButton;
import cz.okozel.ristral.frontend.customComponents.ZaregistrovatSeButton;

@PageTitle("Vítejte")
public class VitejteView extends VerticalLayout {

    public VitejteView() {
        add(
                new H1("Ristral"),
                new Paragraph("Vítejte v Ristralu! Ristral je řídicí informační systém dopravního podniku."),
                new Paragraph("Nyní se prosím přihlaste."),
                new HorizontalLayout(
                        PrihlasitSeButton.getPrihlasitSeButtonRouterLink(),
                        ZaregistrovatSeButton.getZaregistrovatSeButtonRouterLink()
                )
        );
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
