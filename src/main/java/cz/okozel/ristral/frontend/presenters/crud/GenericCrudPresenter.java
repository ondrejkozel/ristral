package cz.okozel.ristral.frontend.presenters.crud;

import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.repository.generic.GenericRepository;
import cz.okozel.ristral.backend.service.generic.GenericSchemaService;
import cz.okozel.ristral.frontend.presenters.Presenter;
import cz.okozel.ristral.frontend.views.crud.GenericCrudView;

public abstract class GenericCrudPresenter<T extends AbstractSchemaEntity, V extends GenericCrudView<T>> extends Presenter<V> {

    public GenericCrudPresenter(Class<T> tridaObjektu, GenericDataProvider<T, ? extends GenericSchemaService<T, ? extends GenericRepository<T>>> dataProvider) {
        nastavCrud(tridaObjektu, dataProvider, vytvorEditor());
        getContent().poInicializaci();
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
