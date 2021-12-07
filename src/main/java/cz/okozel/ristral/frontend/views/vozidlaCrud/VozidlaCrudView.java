package cz.okozel.ristral.frontend.views.vozidlaCrud;

import com.vaadin.flow.component.grid.Grid;
import cz.okozel.ristral.backend.entity.vozidla.Vozidlo;
import cz.okozel.ristral.frontend.views.crud.GenericCrudView;

import java.util.ArrayList;
import java.util.List;

public class VozidlaCrudView extends GenericCrudView<Vozidlo> {

    public VozidlaCrudView() {
        setCrudTexts("Nové vozidlo", "Upravit vozidlo", "Odstranit vozidlo");
    }

    @Override
    public void postInicialization() {
        deleteAllColumnsExcept("nazev", "obsaditelnost", "popis", "typ");
        renameColumn("nazev", "Název");
        setColumnExpandRatio("nazev", 7);
        setColumnExpandRatio("popis", 18);
        setColumnExpandRatio("typ", 2);
        List<Grid.Column<Vozidlo>> sloupce = new ArrayList<>(getColumns());
        sloupce.add(0, sloupce.remove(3));
        sloupce.add(2, sloupce.remove(3));
        setColumnOrder(sloupce);
    }

}
