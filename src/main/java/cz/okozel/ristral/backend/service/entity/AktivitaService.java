package cz.okozel.ristral.backend.service.entity;

import cz.okozel.ristral.backend.entity.aktivity.Aktivita;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.repository.AktivitaRepository;
import cz.okozel.ristral.backend.service.entity.generic.GenericSchemaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AktivitaService extends GenericSchemaService<Aktivita, AktivitaRepository> {

    public AktivitaService(AktivitaRepository hlavniRepositar) {
        super(hlavniRepositar);
    }

    public List<Aktivita> findAll(Uzivatel uzivatel) {
        return hlavniRepositar.findAllByAkterEquals(uzivatel);
    }

    @Transactional
    public void deleteAll(Uzivatel akter) {
        hlavniRepositar.deleteAktivitaByAkterEquals(akter);
    }

}
