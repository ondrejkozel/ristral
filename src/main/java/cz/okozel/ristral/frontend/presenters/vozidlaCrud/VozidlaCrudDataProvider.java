package cz.okozel.ristral.frontend.presenters.vozidlaCrud;

import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.vozidla.Vozidlo;
import cz.okozel.ristral.backend.service.entity.VozidloService;
import cz.okozel.ristral.frontend.presenters.crud.GenericDataProvider;

public class VozidlaCrudDataProvider extends GenericDataProvider<Vozidlo, VozidloService> {

    public VozidlaCrudDataProvider(VozidloService service, Class<Vozidlo> tridaObjektu, Schema schema) {
        super(service, tridaObjektu, schema);
    }

}
