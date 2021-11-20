package cz.okozel.ristral.frontend.views.crud;

import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudI18n;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import cz.okozel.ristral.backend.entity.AbstractEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GenericCrudView<T extends AbstractEntity> extends VerticalLayout {

    private String novy, upravit, odstranit;
    protected Crud<T> crud;

    public GenericCrudView() {
        novy = "Nový";
        upravit = "Upravit";
        odstranit = "Odstranit";
        crud = new Crud<>();
        add(
                vytvorMenuBar(),
                crud
        );
    }

    /**
     * tato metoda se volá až po inicializaci gridu
     */
    public void nastavI18n() {
        CrudI18n crudI18n = new CrudI18n();
        crudI18n.setCancel("Zrušit");
        crudI18n.setDeleteItem("Odstranit...");
        crudI18n.setEditItem(upravit);
        crudI18n.setNewItem(novy);
        crudI18n.setSaveItem("Uložit");
        crudI18n.setConfirm(buildPotvrzeni());
        crud.setI18n(crudI18n);
    }

    private CrudI18n.Confirmations buildPotvrzeni() {
        CrudI18n.Confirmations.Confirmation cancel = new CrudI18n.Confirmations.Confirmation();
        cancel.setTitle("Zahodit změny");
        cancel.setContent("Máte neuložené změny. Opravdu si je přejete zahodit?");
        CrudI18n.Confirmations.Confirmation.Button tlacidlaZahodit = new CrudI18n.Confirmations.Confirmation.Button();
        tlacidlaZahodit.setConfirm("Zahodit");
        tlacidlaZahodit.setDismiss("Zrušit");
        cancel.setButton(tlacidlaZahodit);
        //
        CrudI18n.Confirmations.Confirmation delete = new CrudI18n.Confirmations.Confirmation();
        delete.setTitle(odstranit);
        delete.setContent("Opravdu si přejete smazat tento objekt? Tato akce je nevratná.");
        CrudI18n.Confirmations.Confirmation.Button tlacidlaSmazat = new CrudI18n.Confirmations.Confirmation.Button();
        tlacidlaSmazat.setConfirm("Smazat");
        tlacidlaSmazat.setDismiss("Zrušit");
        delete.setButton(tlacidlaSmazat);
        //
        CrudI18n.Confirmations confirmations = new CrudI18n.Confirmations();
        confirmations.setCancel(cancel);
        confirmations.setDelete(delete);
        return confirmations;
    }

    public void setCrudTexty(String novyObjekt, String titulekEditoru, String odstranitObjekt) {
        novy = novyObjekt;
        upravit = titulekEditoru;
        odstranit = odstranitObjekt;
    }

    public Crud<T> getCrud() {
        return crud;
    }

    /**
     * pro práci s inicializovaným gridem
     */
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
        menuBar.getItems().forEach(menuItem -> menuItem.addClickListener(event -> Notification.show("Zatím nic neumím, ale už brzo to tak nebude!")));
        return menuBar;
    }

}