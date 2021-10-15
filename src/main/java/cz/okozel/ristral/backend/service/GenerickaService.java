package cz.okozel.ristral.backend.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class GenerickaService<T, R extends JpaRepository<T, Long>> {
    private final R hlavniRepositar;

    public GenerickaService(R hlavniRepositar) {
        this.hlavniRepositar = hlavniRepositar;
    }

    public List<T> getAll() {
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
