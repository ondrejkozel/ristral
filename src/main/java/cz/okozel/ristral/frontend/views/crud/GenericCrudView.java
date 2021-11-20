package cz.okozel.ristral.frontend.views.crud;

import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import cz.okozel.ristral.backend.entity.AbstractEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GenericCrudView<T extends AbstractEntity> extends VerticalLayout {

    protected Crud<T> crud;

    public GenericCrudView() {
        crud = new Crud<>();
        add(
                vytvorMenuBar(),
                crud
        );
    }

    public Crud<T> getCrud() {
        return crud;
    }

    public void poInicializaci() {
    }

    public void odstranSloupceAzNa(List<String> kliceVyjimek) {
        //kopie vyjímek pro případ, kdy do předaného seznamu nešlo přidávat objekty
        List<String> kliceVyjimekKopie = new ArrayList<>(kliceVyjimek);
        kliceVyjimekKopie.add("vaadin-crud-edit-column");
        crud.getGrid().getColumns().forEach(sloupec -> {
            if (!kliceVyjimekKopie.contains(sloupec.getKey())) crud.getGrid().removeColumn(sloupec);
        });
    }

    public void prejmenujSloupce(Map<String, String> sloupceNaPrejmenovani) {
        crud.getGrid().getColumns().forEach(sloupec -> {
            if (sloupceNaPrejmenovani.containsKey(sloupec.getKey())) sloupec.setHeader(sloupceNaPrejmenovani.get(sloupec.getKey()));
        });
    }

    public void prejmenujSloupec(String klic, String novaHlavicka) {
        List<Grid.Column<T>> sloupce = crud.getGrid().getColumns();
        Optional<Grid.Column<T>> kPrejmenovani = sloupce.stream().filter(sloupec -> sloupec.getKey().equals(klic)).findAny();
        kPrejmenovani.ifPresent(sloupec -> sloupec.setHeader(novaHlavicka));
    }

    private MenuBar vytvorMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.addItem("Soubor");
        menuBar.addItem("Upravit");
        menuBar.addItem("Nápověda");
        return menuBar;
    }

}
