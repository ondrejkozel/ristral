package cz.okozel.ristral.backend.service.entity;

import cz.okozel.ristral.backend.entity.zastavky.RezimObsluhy;
import cz.okozel.ristral.backend.repository.PeriodaNaZnameniRepository;
import cz.okozel.ristral.backend.service.entity.generic.GenericSchemaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeriodaNaZnameniService extends GenericSchemaService<RezimObsluhy.PeriodaNaZnameni, PeriodaNaZnameniRepository> {

    public PeriodaNaZnameniService(PeriodaNaZnameniRepository hlavniRepositar) {
        super(hlavniRepositar);
    }

    public List<RezimObsluhy.PeriodaNaZnameni> findAllByRezimObsluhy(RezimObsluhy rezimObsluhy) {
        return hlavniRepositar.findAllByRezimObsluhyEquals(rezimObsluhy);
    }

}
