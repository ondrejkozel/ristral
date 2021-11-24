package cz.okozel.ristral.frontend.presenters.crud;

import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.grid.Grid;
import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.repository.generic.GenericRepository;
import cz.okozel.ristral.backend.service.entity.generic.GenericSchemaService;
import cz.okozel.ristral.frontend.presenters.Presenter;
import cz.okozel.ristral.frontend.views.crud.GenericCrudView;

public abstract class GenericCrudPresenter<T extends AbstractSchemaEntity, V extends GenericCrudView<T>> extends Presenter<V> {

    private boolean vicenasobnyVyber = false;

    public GenericCrudPresenter(Class<T> tridaObjektu, GenericDataProvider<T, ? extends GenericSchemaService<T, ? extends GenericRepository<T>>> dataProvider) {
        nastavCrud(tridaObjektu, dataProvider, vytvorEditor());
        getContent().poInicializaci();
        getContent().addObnovitClickListener(event -> dataProvider.refreshAll());
        getContent().addVicenasobnyVyberClickListener(event -> toggleVicenasobnyVyber());
    }

    private void toggleVicenasobnyVyber() {
        vicenasobnyVyber = !vicenasobnyVyber;
        getContent().getCrud().getGrid().setSelectionMode(vicenasobnyVyber ? Grid.SelectionMode.MULTI : Grid.SelectionMode.NONE);
        getContent().setVicenasobnyVyberChecked(vicenasobnyVyber);
    }

    private void nastavCrud(Class<T> tridaObjektu, GenericDataProvider<T, ? extends GenericSchemaService<T, ? extends GenericRepository<T>>> dataProvider, CrudEditor<T> editor) {
        Crud<T> crud = getContent().getCrud();
        crud.setBeanType(tridaObjektu);
        crud.setEditor(editor);
        crud.setDataProvider(dataProvider);
        crud.addSaveListener(event -> dataProvider.uloz(event.getItem()));
        crud.addDeleteListener(event -> dataProvider.smaz(event.getItem()));
        getContent().nastavI18n();
    }

    protected abstract CrudEditor<T> vytvorEditor();

}
