package cz.okozel.ristral.backend.repository;

import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.zastavky.RezimObsluhy;
import cz.okozel.ristral.backend.repository.generic.GenericSchemaRepository;

public interface RezimObsluhyRepository extends GenericSchemaRepository<RezimObsluhy> {
    RezimObsluhy findBySchemaEqualsAndUpravitelny(Schema schema, boolean upravitelny);
}
