package cz.okozel.ristral.backend.entity.uzivatele;

import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.schema.TypSchematu;
import cz.okozel.ristral.backend.service.entity.AktivitaService;
import cz.okozel.ristral.backend.service.entity.SchemaService;
import cz.okozel.ristral.backend.service.entity.UzivatelService;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("OSOBNI_UZIVATEL")
public class OsobniUzivatel extends AdminOrg {

    public OsobniUzivatel() {
        super();
    }

    public OsobniUzivatel(String uzivatelskeJmeno, String jmeno, String email, String heslo, Schema schema) {
        super(uzivatelskeJmeno, jmeno, email, heslo, schema);
    }

    public static void prevedNaUcetOrganizace(OsobniUzivatel uzivatel, AktivitaService aktivitaService, SchemaService schemaService, UzivatelService uzivatelService) {
        aktivitaService.deleteAll(uzivatel);
        //
        uzivatel.getSchema().setNazev(String.format("Organizace %s", uzivatel.getJmeno()));
        uzivatel.getSchema().setTypSchematu(TypSchematu.ORGANIZACE);
        schemaService.save(uzivatel.getSchema());
        //
        uzivatelService.delete(uzivatel);
        uzivatelService.save(uzivatel.getSuperadminOrg());
    }

    public void prevedNaUcetOrganizace(AktivitaService aktivitaService, SchemaService schemaService, UzivatelService uzivatelService) {
        prevedNaUcetOrganizace(this, aktivitaService, schemaService, uzivatelService);
    }

}
