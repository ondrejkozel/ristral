package cz.okozel.ristral.backend.service;

import cz.okozel.ristral.backend.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class GenerickaService<T extends AbstractEntity, R extends JpaRepository<T, Long>> {
    private final R hlavniRepositar;

    public GenerickaService(R hlavniRepositar) {
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
}
