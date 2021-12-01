package cz.okozel.ristral.frontend.presenters.zastavkyCrud;

import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.zastavky.Zastavka;
import cz.okozel.ristral.backend.service.entity.ZastavkaService;
import cz.okozel.ristral.frontend.presenters.crud.GenericDataProvider;

public class ZastavkyCrudDataProvider extends GenericDataProvider<Zastavka, ZastavkaService> {

    public ZastavkyCrudDataProvider(ZastavkaService zastavkaService, Schema schema) {
        super(zastavkaService, Zastavka.class, schema);
    }

}
