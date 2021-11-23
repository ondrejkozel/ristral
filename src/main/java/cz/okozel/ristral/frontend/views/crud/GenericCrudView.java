package cz.okozel.ristral.frontend.views.crud;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudI18n;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import cz.okozel.ristral.backend.entity.AbstractEntity;

import java.util.ArrayList;
import java.util.List;
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
        setSizeFull();
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

    public void odstranSloupceAzNa(String... kliceVyjimek) {
        //kopie vyjímek pro případ, kdy do předaného seznamu nešlo přidávat objekty
        List<String> kliceVyjimekKopie = new ArrayList<>(List.of(kliceVyjimek));
        kliceVyjimekKopie.add("vaadin-crud-edit-column");
        crud.getGrid().getColumns().forEach(sloupec -> {
            if (!kliceVyjimekKopie.contains(sloupec.getKey())) crud.getGrid().removeColumn(sloupec);
        });
    }

    public void setExpandRatioSloupce(String klic, int expandRatio) {
        Optional<Grid.Column<T>> keZmene = crud.getGrid().getColumns().stream().filter(sloupec -> sloupec.getKey().equals(klic)).findAny();
        keZmene.ifPresent(sloupec -> sloupec.setFlexGrow(expandRatio));
    }

    public void prejmenujSloupec(String klic, String novaHlavicka) {
        List<Grid.Column<T>> sloupce = crud.getGrid().getColumns();
        Optional<Grid.Column<T>> kPrejmenovani = sloupce.stream().filter(sloupec -> sloupec.getKey().equals(klic)).findAny();
        kPrejmenovani.ifPresent(sloupec -> sloupec.setHeader(novaHlavicka));
    }

    public void setRoztahovatelneSloupce() {
        crud.getGrid().getColumns().forEach(sloupec -> sloupec.setResizable(true));
    }

    MenuItem obnovit, multiVyber;

    private MenuBar vytvorMenuBar() {
        MenuBar menuBar = new MenuBar();
        MenuItem soubor = menuBar.addItem("Soubor");
        //
//        multiVyber = soubor.getSubMenu().addItem("Vícenásobný výběr");
//        multiVyber.setCheckable(true);
//        soubor.getSubMenu().add(new Hr());
        //
        obnovit = soubor.getSubMenu().addItem("Obnovit");
        //
        menuBar.addItem("Upravit", event -> Notification.show("Zatím nic neumím, ale už brzo to tak nebude!"));
        //
        menuBar.addItem("Nápověda", event -> Notification.show("Zatím nic neumím, ale už brzo to tak nebude!"));
        return menuBar;
    }

    public void addObnovitClickListener(ComponentEventListener<ClickEvent<MenuItem>> obnovitClickListener) {
        obnovit.addClickListener(obnovitClickListener);
    }

    public void addVicenasobnyVyberClickListener(ComponentEventListener<ClickEvent<MenuItem>> vicenasobnyVyberClickListener) {
        multiVyber.addClickListener(vicenasobnyVyberClickListener);
    }

    public void setVicenasobnyVyberChecked(boolean vybrano) {
        multiVyber.setChecked(vybrano);
    }

}
