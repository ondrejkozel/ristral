package cz.okozel.ristral.backend.repository;

import cz.okozel.ristral.backend.entity.lines.Line;
import cz.okozel.ristral.backend.entity.lines.LineRouteCarrier;
import cz.okozel.ristral.backend.repository.generic.GenericSchemaRepository;

public interface LineRouteCarrierRepository extends GenericSchemaRepository<LineRouteCarrier> {
    void deleteAllByAssociatedLineEquals(Line associatedLine);
}
