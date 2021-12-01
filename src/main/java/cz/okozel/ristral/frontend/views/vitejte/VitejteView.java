package cz.okozel.ristral.frontend.views.vitejte;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import cz.okozel.ristral.frontend.customComponents.PrihlasitSeButton;
import cz.okozel.ristral.frontend.customComponents.ZaregistrovatSeButton;

public class VitejteView extends VerticalLayout {

    private final Paragraph vyborne, prihlasteSe;
    private final HorizontalLayout tlacitkaAutentikace;

    public VitejteView() {
        vyborne = new Paragraph("Nyní můžete začít používat aplikaci.");
        prihlasteSe = new Paragraph("Nyní se prosím přihlaste.");
        tlacitkaAutentikace = new HorizontalLayout(
                PrihlasitSeButton.getPrihlasitSeButtonRouterLink(),
                ZaregistrovatSeButton.getZaregistrovatSeButtonRouterLink()
        );
        //
        add(
                new H1("Ristral"),
                new Paragraph("Vítejte v Ristralu! Ristral je řídicí informační systém dopravního podniku."),
                prihlasteSe,
                vyborne,
                tlacitkaAutentikace
        );
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    public void setUzivatelPrihlaseny(boolean prihlaseny) {
        prihlasteSe.setVisible(!prihlaseny);
        vyborne.setVisible(prihlaseny);
        tlacitkaAutentikace.setVisible(!prihlaseny);
    }

}
