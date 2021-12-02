package cz.okozel.ristral.backend.entity.uzivatele;

import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.service.entity.AktivitaService;
import cz.okozel.ristral.backend.service.entity.SchemaService;
import cz.okozel.ristral.backend.service.entity.UzivatelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SUPERADMIN_ORG")
public class SuperadminOrg extends OsobniUzivatel {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuperadminOrg.class);

    public SuperadminOrg() {
        super();
    }

    public SuperadminOrg(String uzivatelskeJmeno, String jmeno, String email, String heslo, Schema schema) {
        super(uzivatelskeJmeno, jmeno, email, heslo, schema);
    }

    @Override
    public void prevedNaUcetOrganizace(AktivitaService aktivitaService, SchemaService schemaService, UzivatelService uzivatelService) {
        LOGGER.warn(String.format("Superadministrátora %s (%s) nemá smysl převádět na účet organizace, jelikož ním už je.", this.getUzivatelskeJmeno(), this.getSchema().getNazev()));
    }


}
