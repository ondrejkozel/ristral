package cz.okozel.ristral.backend.service;

import cz.okozel.ristral.backend.entity.aktivity.Aktivita;
import cz.okozel.ristral.backend.repository.AktivitaRepository;
import cz.okozel.ristral.backend.service.generic.GenerickaSchemaService;
import org.springframework.stereotype.Service;

@Service
public class AktivitaService extends GenerickaSchemaService<Aktivita, AktivitaRepository> {

    public AktivitaService(AktivitaRepository hlavniRepositar) {
        super(hlavniRepositar);
    }

}
