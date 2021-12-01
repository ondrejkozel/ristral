package cz.okozel.ristral.backend.service;

import cz.okozel.ristral.backend.entity.aktivity.Aktivita;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.repository.AktivitaRepository;
import cz.okozel.ristral.backend.service.generic.GenericSchemaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AktivitaService extends GenericSchemaService<Aktivita, AktivitaRepository> {

    public AktivitaService(AktivitaRepository hlavniRepositar) {
        super(hlavniRepositar);
    }

    public List<Aktivita> findAll(Uzivatel uzivatel) {
        return hlavniRepositar.findAllByAkterEquals(uzivatel);
    }

}
