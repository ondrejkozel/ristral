package cz.okozel.ristral.frontend.presenters.zastavky;

import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.zastavky.Zastavka;
import cz.okozel.ristral.backend.service.ZastavkaService;
import cz.okozel.ristral.frontend.presenters.crud.GenericDataProvider;

public class ZastavkyDataProvider extends GenericDataProvider<Zastavka, ZastavkaService> {

    public ZastavkyDataProvider(ZastavkaService zastavkaService, Schema schema) {
        super(zastavkaService, Zastavka.class, schema);
    }

}
