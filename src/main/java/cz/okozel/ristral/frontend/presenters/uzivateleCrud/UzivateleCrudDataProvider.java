package cz.okozel.ristral.frontend.presenters.uzivateleCrud;

import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.service.entity.UzivatelService;
import cz.okozel.ristral.frontend.presenters.crud.GenericDataProvider;

public class UzivateleCrudDataProvider extends GenericDataProvider<Uzivatel, UzivatelService> {

    public UzivateleCrudDataProvider(UzivatelService service, Class<Uzivatel> tridaObjektu, Schema schema) {
        super(service, tridaObjektu, schema);
    }

}
