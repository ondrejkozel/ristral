package cz.okozel.ristral.backend.service.entity;

import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.zastavky.RezimObsluhy;
import cz.okozel.ristral.backend.entity.zastavky.Zastavka;
import cz.okozel.ristral.backend.repository.ZastavkaRepository;
import cz.okozel.ristral.backend.service.entity.generic.GenericSchemaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZastavkaService extends GenericSchemaService<Zastavka, ZastavkaRepository> {

    public ZastavkaService(ZastavkaRepository hlavniRepositar) {
        super(hlavniRepositar);
    }

    public List<Zastavka> findAllByRezimObsluhy(Schema schema, RezimObsluhy rezimObsluhy) {
        return hlavniRepositar.findBySchemaEqualsAndRezimObsluhyEquals(schema, rezimObsluhy);
    }

}
