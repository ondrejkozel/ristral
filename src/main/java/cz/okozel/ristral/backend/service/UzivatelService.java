package cz.okozel.ristral.backend.service;

import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.repository.UzivatelRepository;
import cz.okozel.ristral.backend.service.generic.GenericSchemaService;
import org.springframework.stereotype.Service;

@Service
public class UzivatelService extends GenericSchemaService<Uzivatel, UzivatelRepository> {

    public UzivatelService(UzivatelRepository uzivatelRepository) {
        super(uzivatelRepository);
    }

    public Uzivatel findByUzivatelskeJmeno(String uzivatelskeJmeno) {
        return hlavniRepositar.findByUzivatelskeJmenoEquals(uzivatelskeJmeno);
    }

    public boolean jeTotoUzivateskeJmenoObsazene(String uzivatelskeJmeno) {
        return hlavniRepositar.countUzivatelByUzivatelskeJmenoEquals(uzivatelskeJmeno) > 0;
    }

}
