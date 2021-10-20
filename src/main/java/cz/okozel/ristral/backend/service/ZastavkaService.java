package cz.okozel.ristral.backend.service;

import cz.okozel.ristral.backend.entity.zastavky.Zastavka;
import cz.okozel.ristral.backend.repository.ZastavkaRepository;
import cz.okozel.ristral.backend.service.generic.GenericSchemaService;

public class ZastavkaService extends GenericSchemaService<Zastavka, ZastavkaRepository> {

    public ZastavkaService(ZastavkaRepository hlavniRepositar) {
        super(hlavniRepositar);
    }

}
