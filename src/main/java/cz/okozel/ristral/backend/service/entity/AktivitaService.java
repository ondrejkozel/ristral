package cz.okozel.ristral.backend.service.entity;

import cz.okozel.ristral.backend.entity.aktivity.Aktivita;
import cz.okozel.ristral.backend.repository.AktivitaRepository;
import cz.okozel.ristral.backend.service.entity.generic.GenericSchemaService;
import org.springframework.stereotype.Service;

@Service
public class AktivitaService extends GenericSchemaService<Aktivita, AktivitaRepository> {

    public AktivitaService(AktivitaRepository hlavniRepositar) {
        super(hlavniRepositar);
    }

}
