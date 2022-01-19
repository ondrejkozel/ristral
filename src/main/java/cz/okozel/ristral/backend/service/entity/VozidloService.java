package cz.okozel.ristral.backend.service.entity;

import cz.okozel.ristral.backend.entity.vozidla.TypVozidla;
import cz.okozel.ristral.backend.entity.vozidla.Vozidlo;
import cz.okozel.ristral.backend.repository.VozidloRepository;
import cz.okozel.ristral.backend.service.entity.generic.GenericSchemaService;
import org.springframework.stereotype.Service;

@Service
public class VozidloService extends GenericSchemaService<Vozidlo, VozidloRepository> {

    public VozidloService(VozidloRepository hlavniRepositar) {
        super(hlavniRepositar);
    }

    public long count(TypVozidla typVozidla) {
        return hlavniRepositar.countVozidloByTypEquals(typVozidla);
    }

}
