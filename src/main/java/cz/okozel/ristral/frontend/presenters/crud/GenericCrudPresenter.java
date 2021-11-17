package cz.okozel.ristral.frontend.presenters.crud;

import cz.okozel.ristral.backend.entity.AbstractEntity;
import cz.okozel.ristral.backend.repository.generic.GenericRepository;
import cz.okozel.ristral.backend.service.generic.GenericService;
import cz.okozel.ristral.frontend.presenters.Presenter;
import cz.okozel.ristral.frontend.views.crud.GenericCrudView;

public abstract class GenericCrudPresenter<T extends AbstractEntity, V extends GenericCrudView<T>> extends Presenter<V> {

    public GenericCrudPresenter(Class<T> tridaObjektu, GenericDataProvider<T, ? extends GenericService<T, ? extends GenericRepository<T>>> dataProvider) {
        getContent().nastavCrud(tridaObjektu, dataProvider);
    }

}
