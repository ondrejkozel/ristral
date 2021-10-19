package cz.okozel.ristral.backend.service;

import cz.okozel.ristral.backend.repository.SchemaRepository;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.service.generic.GenerickaService;
import org.springframework.stereotype.Service;

@Service
public class SchemaService extends GenerickaService<Schema, SchemaRepository> {

    public SchemaService(SchemaRepository hlavniRepositar) {
        super(hlavniRepositar);
    }

}
