package cz.okozel.ristral.backend.service.entity;

import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.zastavky.RezimObsluhy;
import cz.okozel.ristral.backend.repository.RezimObsluhyRepository;
import cz.okozel.ristral.backend.service.entity.generic.GenericSchemaService;
import org.springframework.stereotype.Service;

@Service
public class RezimObsluhyService extends GenericSchemaService<RezimObsluhy, RezimObsluhyRepository> {

    public RezimObsluhyService(RezimObsluhyRepository hlavniRepositar) {
        super(hlavniRepositar);
    }

    @Override
    public void delete(RezimObsluhy objekt) {
        if (objekt.isSmazatelny()) super.delete(objekt);
    }

    public RezimObsluhy findVychoziRezim(Schema schema) {
        return hlavniRepositar.findBySchemaEqualsAndAndSmazatelny(schema, false);
    }

}
