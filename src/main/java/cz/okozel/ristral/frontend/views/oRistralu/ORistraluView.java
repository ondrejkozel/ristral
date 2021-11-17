package cz.okozel.ristral.frontend.views.oRistralu;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import cz.okozel.ristral.frontend.MainLayout;

@PageTitle("O Ristralu")
@Route(value = "o-ristralu", layout = MainLayout.class)
@AnonymousAllowed
public class ORistraluView extends VerticalLayout {

    public ORistraluView() {
        add(
                new H1("Ristral"),
                new Paragraph("Ristral je jednoduchý informační systém dopravního podniku. Hlavním cílem je, aby systém splňoval požadavky pro zajištění plynulého chodu dopravní společnosti (databáze zastávek, linek, vozidel, řidičů; plánování jízd a přidělování řidičům; statistiky provozu a možné optimalizace). Ristral bude přístupný jako webová aplikace jednotlivcům stejně jako organizacím a to i pro komerční použití.")
        );
        setClassName("pr-l");
    }

}
