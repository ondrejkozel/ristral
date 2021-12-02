package cz.okozel.ristral.frontend.views.uzivateleCrud;

import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.frontend.views.crud.GenericCrudView;

import java.util.ArrayList;
import java.util.List;

public class UzivateleCrudView extends GenericCrudView<Uzivatel> {

    public UzivateleCrudView() {
        setCrudTexty("Nový uživatel", "Upravit uživatele", "Odstranit uživatele");
    }

    @Override
    public void poInicializaci() {
        odstranSloupceAzNa("email", "jmeno", "uzivatelskeJmeno");
        prejmenujSloupec("jmeno", "Jméno");
        prejmenujSloupec("uzivatelskeJmeno", "Uživatelské jméno");
        //
        Grid.Column<Uzivatel> role = getCrud().getGrid().addColumn(new ComponentRenderer<>(Span::new, roleComponentUpdater));
        role.setHeader("Role");
        role.setWidth("60px");
        role.setFlexGrow(0);
        role.setTextAlign(ColumnTextAlign.END);
        //
        List<Grid.Column<Uzivatel>> sloupce = new ArrayList<>(getSloupce());
        sloupce.add(2, sloupce.remove(0));
        sloupce.add(0, sloupce.remove(4));
        nastavNovePoradiSloupcu(sloupce);
    }

    private final SerializableBiConsumer<Span, Uzivatel> roleComponentUpdater = (span, uzivatel) -> {
        span.setClassName(uzivatel.getRole().getIkona().getNazevTridy());
        span.setTitle(uzivatel.getRole().getNazev());
    };

}
