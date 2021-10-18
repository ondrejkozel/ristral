package cz.okozel.ristral.backend.service.generic;


import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.repository.generic.GenericSchemaRepository;
import cz.okozel.ristral.backend.schema.Schema;

import java.util.List;

/**
 * generická služba pro AbstractSchemaEntity
 */
public abstract class GenerickaSchemaService<T extends AbstractSchemaEntity, R extends GenericSchemaRepository<T>> extends GenerickaService<T, R> {
    public GenerickaSchemaService(R hlavniRepositar) {
        super(hlavniRepositar);
    }

    public List<T> findAllBySchema(Schema schema) {
        return hlavniRepositar.findAllBySchema(schema);
    }
}
