package cz.okozel.ristral.backend.repository.generic;

import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.schema.Schema;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface GenericSchemaRepository<T extends AbstractSchemaEntity> extends GenericRepository<T> {
    @Query("select u from #{#entityName} u where u.schema = ?1")
    List<T> findAllBySchemaEquals(Schema schema);

    @Query("select count(u) from #{#entityName} u where u.schema = ?1")
    long countBySchemaEquals(Schema schema);
}
