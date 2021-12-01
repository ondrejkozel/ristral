package cz.okozel.ristral.backend.repository;

import cz.okozel.ristral.backend.entity.zastavky.RezimObsluhy;
import cz.okozel.ristral.backend.repository.generic.GenericSchemaRepository;

import java.util.List;

public interface PeriodaNaZnameniRepository extends GenericSchemaRepository<RezimObsluhy.PeriodaNaZnameni> {
    List<RezimObsluhy.PeriodaNaZnameni> findAllByRezimObsluhyEquals(RezimObsluhy rezimObsluhy);
}
