package cz.okozel.ristral.backend.entity;

import cz.okozel.ristral.backend.entity.schema.Schema;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * Třída, která je předkem všech JPA entit patřících do schéma.
 */
@MappedSuperclass
public abstract class AbstractSchemaEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn
    @NotNull
    private Schema schema;

    public AbstractSchemaEntity() {}

    public AbstractSchemaEntity(Schema schema) {
        this.schema = schema;
    }

    public AbstractSchemaEntity(Long id) {
        super(id);
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }
}
