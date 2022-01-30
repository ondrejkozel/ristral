package cz.okozel.ristral.backend.repository;

import cz.okozel.ristral.backend.entity.lines.Line;
import cz.okozel.ristral.backend.entity.vozidla.TypVozidla;
import cz.okozel.ristral.backend.repository.generic.GenericSchemaRepository;

public interface LineRepository extends GenericSchemaRepository<Line> {
    long countLineByPrefVehicleTypeEquals(TypVozidla prefVehicleType);
}
