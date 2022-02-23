package cz.okozel.ristral.backend.repository;

import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.zastavky.RezimObsluhy;
import cz.okozel.ristral.backend.entity.zastavky.Zastavka;
import cz.okozel.ristral.backend.repository.generic.GenericSchemaRepository;

import java.util.List;

public interface ZastavkaRepository extends GenericSchemaRepository<Zastavka> {
    List<Zastavka> findBySchemaEqualsAndRezimObsluhyEquals(Schema schema, RezimObsluhy rezimObsluhy);
    long countZastavkaByRezimObsluhyEquals(RezimObsluhy rezimObsluhy);
}
