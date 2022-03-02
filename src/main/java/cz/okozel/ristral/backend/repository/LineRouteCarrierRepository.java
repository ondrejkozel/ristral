package cz.okozel.ristral.backend.repository;

import cz.okozel.ristral.backend.entity.lines.Line;
import cz.okozel.ristral.backend.entity.lines.LineRouteCarrier;
import cz.okozel.ristral.backend.repository.generic.GenericSchemaRepository;

import java.util.List;

public interface LineRouteCarrierRepository extends GenericSchemaRepository<LineRouteCarrier> {
    List<LineRouteCarrier> findAllByAssociatedLineEquals(Line associatedLine);
    void deleteAllByAssociatedLineEquals(Line associatedLine);
}
