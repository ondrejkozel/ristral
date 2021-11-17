package cz.okozel.ristral.frontend.presenters.zastavky;

import cz.okozel.ristral.backend.entity.zastavky.Zastavka;
import cz.okozel.ristral.backend.service.ZastavkaService;
import cz.okozel.ristral.frontend.presenters.crud.GenericDataProvider;

public class ZastavkyDataProvider extends GenericDataProvider<Zastavka, ZastavkaService> {

    public ZastavkyDataProvider(ZastavkaService zastavkaService) {
        super(zastavkaService, Zastavka.class);
    }

}
