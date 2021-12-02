package cz.okozel.ristral.backend.service;

import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.schema.TypSchematu;
import cz.okozel.ristral.backend.entity.uzivatele.OsobniUzivatel;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.entity.zastavky.RezimObsluhy;
import cz.okozel.ristral.backend.service.entity.AktivitaService;
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
    private final AktivitaService aktivitaService;

    public RegistratorService(UzivatelService uzivatelService, SchemaService schemaService, RezimObsluhyService rezimObsluhyService, PasswordEncoder passwordEncoder, AktivitaService aktivitaService) {
        this.uzivatelService = uzivatelService;
        this.schemaService = schemaService;
        this.rezimObsluhyService = rezimObsluhyService;
        this.passwordEncoder = passwordEncoder;
        this.aktivitaService = aktivitaService;
    }

    public boolean zaregistruj(Uzivatel uzivatel) {
        if (uzivatelService.jeTotoUzivateskeJmenoObsazene(uzivatel.getUzivatelskeJmeno())) return false;
        Schema schema = new Schema(TypSchematu.OSOBNI, uzivatel.getUzivatelskeJmeno());
        uzivatel.setSchema(schema);
        uzivatel.setHeslo(passwordEncoder.encode(uzivatel.getHeslo()));
        schemaService.save(schema);
        uzivatelService.save(uzivatel);
        //
        rezimObsluhyService.save(RezimObsluhy.vytvorVychoziRezimBezZnameni(schema));
        return true;
    }

    public void prevedNaUcetOrganizace(OsobniUzivatel uzivatel) {
        aktivitaService.deleteAll(uzivatel);
        //
        uzivatel.getSchema().setNazev(String.format("Organizace %s", uzivatel.getJmeno()));
        uzivatel.getSchema().setTypSchematu(TypSchematu.ORGANIZACE);
        schemaService.save(uzivatel.getSchema());
        //
        uzivatelService.delete(uzivatel);
        uzivatelService.save(uzivatel.vytvorSuperadminOrg());
    }

}
