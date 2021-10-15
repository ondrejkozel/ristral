package cz.okozel.ristral.backend.service;

import cz.okozel.ristral.backend.aktivity.Aktivita;
import cz.okozel.ristral.backend.repository.AktivitaRepository;
import org.springframework.stereotype.Service;

@Service
public class AktivitaService extends GenerickaService<Aktivita, AktivitaRepository> {

    public AktivitaService(AktivitaRepository hlavniRepositar) {
        super(hlavniRepositar);
    }

}
