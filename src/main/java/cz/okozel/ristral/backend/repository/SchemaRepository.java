package cz.okozel.ristral.backend.repository;

import cz.okozel.ristral.backend.schema.Schema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchemaRepository extends JpaRepository<Schema, Long> {
}
