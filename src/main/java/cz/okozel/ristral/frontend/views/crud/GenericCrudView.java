package cz.okozel.ristral.frontend.views.crud;

import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import cz.okozel.ristral.backend.entity.AbstractEntity;
import cz.okozel.ristral.backend.repository.generic.GenericRepository;
import cz.okozel.ristral.backend.service.generic.GenericService;
import cz.okozel.ristral.frontend.presenters.crud.GenericDataProvider;

public class GenericCrudView<T extends AbstractEntity> extends VerticalLayout {

    private Crud<T> crud = new Crud<>();

    public GenericCrudView() {
        add(
                vytvorMenuBar(),
                crud
        );
    }

    /**
     * metodu volá presenter
     */
    public void nastavCrud(Class<T> tridaObjektu, GenericDataProvider<T, ? extends GenericService<T, ? extends GenericRepository<T>>> dataProvider) {
        crud = new Crud<>(tridaObjektu, vytvorEditor(tridaObjektu));
        crud.setDataProvider(dataProvider);
    }

    private MenuBar vytvorMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.addItem("Soubor");
        menuBar.addItem("Upravit");
        menuBar.addItem("Nápověda");
        return menuBar;
    }

    /**
     * očekává se, že je přepsána potomkem
     */
    protected CrudEditor<T> vytvorEditor(Class<T> tridaObjektu) {
        return null;
    }

}
