package cz.okozel.ristral.backend.service;

import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.schema.TypSchematu;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.entity.zastavky.RezimObsluhy;
import cz.okozel.ristral.backend.service.entity.RezimObsluhyService;
import cz.okozel.ristral.backend.service.entity.SchemaService;
import cz.okozel.ristral.backend.service.entity.UzivatelService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistratorService {

    private final UzivatelService uzivatelService;
    private final SchemaService schemaService;
    private final RezimObsluhyService rezimObsluhyService;
    private final PasswordEncoder passwordEncoder;

    public RegistratorService(UzivatelService uzivatelService, SchemaService schemaService, RezimObsluhyService rezimObsluhyService, PasswordEncoder passwordEncoder) {
        this.uzivatelService = uzivatelService;
        this.schemaService = schemaService;
        this.rezimObsluhyService = rezimObsluhyService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean zaregistrujOsobniUcetAVytvorMuNoveSchema(Uzivatel uzivatel) {
        if (uzivatelService.jeTotoUzivateskeJmenoObsazene(uzivatel.getUzivatelskeJmeno())) return false;
        Schema schema = new Schema(TypSchematu.OSOBNI, uzivatel.getUzivatelskeJmeno());
        schemaService.save(schema);
        //
        rezimObsluhyService.save(RezimObsluhy.vytvorVychoziRezimBezZnameni(schema));
        //
        uzivatel.setSchema(schema);
        nastavHesloAUloz(uzivatel);
        return true;
    }

    public boolean zaregistrujPodrizenyUcet(Uzivatel uzivatel) {
        if (uzivatelService.jeTotoUzivateskeJmenoObsazene(uzivatel.getUzivatelskeJmeno())) return false;
        nastavHesloAUloz(uzivatel);
        return true;
    }

    private void nastavHesloAUloz(Uzivatel uzivatel) {
        uzivatel.setHeslo(passwordEncoder.encode(uzivatel.getHeslo()));
        uzivatelService.save(uzivatel);
    }

}
