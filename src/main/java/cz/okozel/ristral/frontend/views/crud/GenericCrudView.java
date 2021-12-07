package cz.okozel.ristral.frontend.views.crud;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
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

    private String newString, edit, delete;
    protected Crud<T> crud;

    public GenericCrudView() {
        newString = "Nový";
        edit = "Upravit";
        delete = "Odstranit";
        crud = new Crud<>();
        add(
                createMenuBar(),
                crud
        );
        setSizeFull();
    }

    /**
     * tato metoda se volá až po inicializaci gridu
     */
    public void prepareI18n() {
        CrudI18n crudI18n = new CrudI18n();
        crudI18n.setCancel("Zrušit");
        crudI18n.setDeleteItem("Odstranit...");
        crudI18n.setEditItem(edit);
        crudI18n.setNewItem(newString);
        crudI18n.setSaveItem("Uložit");
        crudI18n.setConfirm(buildConfirmations());
        crud.setI18n(crudI18n);
    }

    private CrudI18n.Confirmations buildConfirmations() {
        CrudI18n.Confirmations.Confirmation cancel = new CrudI18n.Confirmations.Confirmation();
        cancel.setTitle("Zahodit změny");
        cancel.setContent("Máte neuložené změny. Opravdu si je přejete zahodit?");
        CrudI18n.Confirmations.Confirmation.Button tlacidlaZahodit = new CrudI18n.Confirmations.Confirmation.Button();
        tlacidlaZahodit.setConfirm("Zahodit");
        tlacidlaZahodit.setDismiss("Zrušit");
        cancel.setButton(tlacidlaZahodit);
        //
        CrudI18n.Confirmations.Confirmation delete = new CrudI18n.Confirmations.Confirmation();
        delete.setTitle(this.delete);
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

    public void setCrudTexts(String novyObjekt, String titulekEditoru, String odstranitObjekt) {
        newString = novyObjekt;
        edit = titulekEditoru;
        delete = odstranitObjekt;
    }

    public Crud<T> getCrud() {
        return crud;
    }

    /**
     * pro práci s inicializovaným gridem
     */
    public void postInicialization() {
    }

    public void deleteAllColumnsExcept(String... kliceVyjimek) {
        List<String> kliceVyjimekKopie = new ArrayList<>(List.of(kliceVyjimek));
        kliceVyjimekKopie.add("vaadin-crud-edit-column");
        crud.getGrid().getColumns().forEach(sloupec -> {
            if (!kliceVyjimekKopie.contains(sloupec.getKey())) crud.getGrid().removeColumn(sloupec);
        });
    }

    public void setColumnExpandRatio(String klic, int expandRatio) {
        Optional<Grid.Column<T>> keZmene = crud.getGrid().getColumns().stream().filter(sloupec -> sloupec.getKey().equals(klic)).findAny();
        keZmene.ifPresent(sloupec -> sloupec.setFlexGrow(expandRatio));
    }

    public void renameColumn(String klic, String novaHlavicka) {
        List<Grid.Column<T>> sloupce = crud.getGrid().getColumns();
        Optional<Grid.Column<T>> kPrejmenovani = sloupce.stream().filter(sloupec -> sloupec.getKey().equals(klic)).findAny();
        kPrejmenovani.ifPresent(sloupec -> sloupec.setHeader(novaHlavicka));
    }

    public List<Grid.Column<T>> getColumns() {
        return crud.getGrid().getColumns();
    }

    public void setColumnOrder(List<Grid.Column<T>> sloupce) {
        crud.getGrid().setColumnOrder(sloupce);
    }

    public void setColumnsResizable(boolean resizable) {
        crud.getGrid().getColumns().forEach(sloupec -> sloupec.setResizable(resizable));
    }

    private MenuItem soubor;
    private MenuItem obnovit, multiVyber;

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        soubor = menuBar.addItem("Soubor");
        //
        multiVyber = soubor.getSubMenu().addItem("Vícenásobný výběr");
        multiVyber.setCheckable(true);
        multiVyber.setVisible(false);
//        soubor.getSubMenu().add(new Hr());
        //
        obnovit = soubor.getSubMenu().addItem("Obnovit");
        //
        menuBar.addItem("Upravit", event -> Notification.show("Zatím nic neumím, ale už brzo to tak nebude!"));
        //
        menuBar.addItem("Nápověda", event -> Notification.show("Zatím nic neumím, ale už brzo to tak nebude!"));
        return menuBar;
    }

    public void pridejMenuItemPodSoubor(String text, ComponentEventListener<ClickEvent<MenuItem>> listener, boolean checkable) {
        pridejMenuItemPodMenuItem(soubor, text, listener, checkable);
    }

    private void pridejMenuItemPodMenuItem(MenuItem menuItem, String text, ComponentEventListener<ClickEvent<MenuItem>> listener, boolean checkable) {
        menuItem.getSubMenu().addItem(text, listener).setCheckable(checkable);
    }

    public void pridejHrPodSoubor() {
        pridejHrPodMenuItem(soubor);
    }

    private void pridejHrPodMenuItem(MenuItem menuItem) {
        menuItem.getSubMenu().add(new Hr());
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

    public void setReadOnly(boolean readOnly) {
        if (readOnly) Crud.removeEditColumn(crud.getGrid());
        crud.setToolbarVisible(!readOnly);
    }

}
