package cz.okozel.ristral.backend.repository;

import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.repository.generic.GenericSchemaRepository;

public interface UzivatelRepository extends GenericSchemaRepository<Uzivatel> {

    Uzivatel findByUzivatelskeJmenoEquals(String uzivatelskeJmeno);

}
