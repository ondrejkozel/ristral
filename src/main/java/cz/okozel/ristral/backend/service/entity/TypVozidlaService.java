package cz.okozel.ristral.backend.service.entity;

import cz.okozel.ristral.backend.entity.vozidla.TypVozidla;
import cz.okozel.ristral.backend.repository.TypVozidlaRepository;
import cz.okozel.ristral.backend.service.entity.generic.GenericSchemaService;
import org.springframework.stereotype.Service;

@Service
public class TypVozidlaService extends GenericSchemaService<TypVozidla, TypVozidlaRepository> {

    public TypVozidlaService(TypVozidlaRepository hlavniRepositar) {
        super(hlavniRepositar);
    }

}
