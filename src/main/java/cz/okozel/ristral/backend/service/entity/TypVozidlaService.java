package cz.okozel.ristral.backend.service.entity;

import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.vozidla.TypVozidla;
import cz.okozel.ristral.backend.repository.TypVozidlaRepository;
import cz.okozel.ristral.backend.service.entity.generic.GenericSchemaService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TypVozidlaService extends GenericSchemaService<TypVozidla, TypVozidlaRepository> {

    private final VozidloService vozidloService;

    public TypVozidlaService(TypVozidlaRepository hlavniRepositar, VozidloService vozidloService) {
        super(hlavniRepositar);
        this.vozidloService = vozidloService;
    }

    public List<TypVozidla> smazNepouzivaneTypyVozidel(Schema schema) {
        List<TypVozidla> typyVozidel = hlavniRepositar.findAllBySchemaEquals(schema);
        Set<TypVozidla> pouzivaneTypyVozidel = new HashSet<>();
        vozidloService.findAll(schema).forEach(vozidlo -> pouzivaneTypyVozidel.add(vozidlo.getTyp()));
        typyVozidel.removeAll(pouzivaneTypyVozidel);
        hlavniRepositar.deleteAll(typyVozidel);
        return typyVozidel;
    }

}
