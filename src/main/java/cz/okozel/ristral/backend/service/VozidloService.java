package cz.okozel.ristral.backend.service;

import cz.okozel.ristral.backend.entity.vozidla.Vozidlo;
import cz.okozel.ristral.backend.repository.VozidloRepository;
import cz.okozel.ristral.backend.service.generic.GenericSchemaService;
import org.springframework.stereotype.Service;

@Service
public class VozidloService extends GenericSchemaService<Vozidlo, VozidloRepository> {

    public VozidloService(VozidloRepository hlavniRepositar) {
        super(hlavniRepositar);
    }

}
