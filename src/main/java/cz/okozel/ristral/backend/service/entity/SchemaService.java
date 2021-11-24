package cz.okozel.ristral.backend.service.entity;

import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.repository.SchemaRepository;
import cz.okozel.ristral.backend.service.entity.generic.GenericService;
import org.springframework.stereotype.Service;

@Service
public class SchemaService extends GenericService<Schema, SchemaRepository> {

    public SchemaService(SchemaRepository hlavniRepositar) {
        super(hlavniRepositar);
    }

}
