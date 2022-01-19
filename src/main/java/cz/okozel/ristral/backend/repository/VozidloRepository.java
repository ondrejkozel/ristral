package cz.okozel.ristral.backend.repository;

import cz.okozel.ristral.backend.entity.vozidla.TypVozidla;
import cz.okozel.ristral.backend.entity.vozidla.Vozidlo;
import cz.okozel.ristral.backend.repository.generic.GenericSchemaRepository;

public interface VozidloRepository extends GenericSchemaRepository<Vozidlo> {
    long countVozidloByTypEquals(TypVozidla typVozidla);
}
