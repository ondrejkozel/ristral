package cz.okozel.ristral.backend.service;

import cz.okozel.ristral.backend.repository.SchemaRepository;
import cz.okozel.ristral.backend.repository.UzivatelRepository;
import cz.okozel.ristral.backend.schema.Schema;
import cz.okozel.ristral.backend.schema.TypSchematu;
import cz.okozel.ristral.backend.uzivatele.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
public class SchemaService {
    private SchemaRepository schemaRepository;

    public SchemaService(SchemaRepository schemaRepository) {
        this.schemaRepository = schemaRepository;
    }

    public List<Schema> getAll() {
        return schemaRepository.findAll();
    }

    public void add(Schema schema) {
        schemaRepository.save(schema);
    }

}
