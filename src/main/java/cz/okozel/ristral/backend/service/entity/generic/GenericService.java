package cz.okozel.ristral.backend.service.entity.generic;

import cz.okozel.ristral.backend.entity.AbstractEntity;
import cz.okozel.ristral.backend.repository.generic.GenericRepository;

import java.util.List;
import java.util.Optional;

/**
 * generická služba pro AbstractEntity
 */
public abstract class GenericService<T extends AbstractEntity, R extends GenericRepository<T>> {

    protected final R hlavniRepositar;

    public GenericService(R hlavniRepositar) {
        this.hlavniRepositar = hlavniRepositar;
    }

    public Optional<T> find(long id) {
        return hlavniRepositar.findById(id);
    }

    public List<T> findAll() {
        return hlavniRepositar.findAll();
    }

    public void save(T objekt) {
        hlavniRepositar.save(objekt);
    }

    public void saveAll(Iterable<T> objekty) {
        hlavniRepositar.saveAll(objekty);
    }

    public long count() {
        return hlavniRepositar.count();
    }

    public void delete(T objekt) {
        hlavniRepositar.delete(objekt);
    }

}
