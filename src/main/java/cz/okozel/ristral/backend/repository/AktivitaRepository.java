package cz.okozel.ristral.backend.repository;

import cz.okozel.ristral.backend.entity.aktivity.Aktivita;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.repository.generic.GenericSchemaRepository;

import java.util.List;

public interface AktivitaRepository extends GenericSchemaRepository<Aktivita> {

    List<Aktivita> findAllByAkterEquals(Uzivatel akter);

    void deleteAktivitaByAkterEquals(Uzivatel akter);

}
