package cz.okozel.ristral.frontend.views.uzivateleCrud;

import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import cz.okozel.ristral.backend.entity.uzivatele.UzivatelOrg;
import cz.okozel.ristral.frontend.views.crud.GenericCrudView;

import java.util.ArrayList;
import java.util.List;

public class UzivateleCrudView extends GenericCrudView<UzivatelOrg> {

    public UzivateleCrudView() {
        setCrudTexts("Nový uživatel", "Upravit uživatele", "Odstranit uživatele");
    }

    @Override
    public void postInicialization() {
        deleteAllColumnsExcept("email", "jmeno", "uzivatelskeJmeno");
        renameColumn("jmeno", "Jméno");
        renameColumn("uzivatelskeJmeno", "Uživatelské jméno");
        //
        Grid.Column<UzivatelOrg> role = getCrud().getGrid().addColumn(new ComponentRenderer<>(Span::new, roleComponentUpdater));
        role.setHeader("Role");
        role.setWidth("60px");
        role.setFlexGrow(0);
        role.setTextAlign(ColumnTextAlign.END);
        //
        List<Grid.Column<UzivatelOrg>> sloupce = new ArrayList<>(getColumns());
        sloupce.add(2, sloupce.remove(0));
        sloupce.add(0, sloupce.remove(4));
        setColumnOrder(sloupce);
    }

    private final SerializableBiConsumer<Span, UzivatelOrg> roleComponentUpdater = (span, uzivatel) -> {
        span.setClassName(uzivatel.getRole().getIkona().getNazevTridy());
        span.setTitle(uzivatel.getRole().getNazev());
    };

}
