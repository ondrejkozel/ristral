package cz.okozel.ristral.backend.service.generic;


import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.repository.generic.GenericSchemaRepository;

import java.util.List;

/**
 * generická služba pro AbstractSchemaEntity
 */
public abstract class GenericSchemaService<T extends AbstractSchemaEntity, R extends GenericSchemaRepository<T>> extends GenericService<T, R> {

    public GenericSchemaService(R hlavniRepositar) {
        super(hlavniRepositar);
    }

    public List<T> findAll(Schema schema) {
        return hlavniRepositar.findAllBySchemaEquals(schema);
    }

    public long count(Schema schema) {
        return hlavniRepositar.countBySchemaEquals(schema);
    }

}
