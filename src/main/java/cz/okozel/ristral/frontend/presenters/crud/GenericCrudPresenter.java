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

    private boolean multiselect = false;

    public GenericCrudPresenter(Class<T> tridaObjektu, GenericDataProvider<T, ? extends GenericSchemaService<T, ? extends GenericRepository<T>>> dataProvider) {
        setCrud(tridaObjektu, dataProvider, createEditor());
        getContent().postInicialization();
        getContent().addObnovitClickListener(event -> dataProvider.refreshAll());
        getContent().addVicenasobnyVyberClickListener(event -> toggleMultipleSelection());
    }

    private void toggleMultipleSelection() {
        multiselect = !multiselect;
        getContent().getCrud().getGrid().setSelectionMode(multiselect ? Grid.SelectionMode.MULTI : Grid.SelectionMode.NONE);
        getContent().setVicenasobnyVyberChecked(multiselect);
    }

    private void setCrud(Class<T> tridaObjektu, GenericDataProvider<T, ? extends GenericSchemaService<T, ? extends GenericRepository<T>>> dataProvider, CrudEditor<T> editor) {
        Crud<T> crud = getContent().getCrud();
        crud.setBeanType(tridaObjektu);
        crud.setEditor(editor);
        crud.setDataProvider(dataProvider);
        crud.addSaveListener(event -> dataProvider.uloz(event.getItem()));
        crud.addDeleteListener(event -> dataProvider.smaz(event.getItem()));
        getContent().prepareI18n();
    }

    protected abstract CrudEditor<T> createEditor();

}
