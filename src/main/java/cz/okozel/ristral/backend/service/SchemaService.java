package cz.okozel.ristral.backend.service;

import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.repository.SchemaRepository;
import cz.okozel.ristral.backend.service.generic.GenericService;
import org.springframework.stereotype.Service;

@Service
public class SchemaService extends GenericService<Schema, SchemaRepository> {

    public SchemaService(SchemaRepository hlavniRepositar) {
        super(hlavniRepositar);
    }

}
