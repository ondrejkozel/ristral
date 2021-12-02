package cz.okozel.ristral.backend.entity.uzivatele;

import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.schema.TypSchematu;
import cz.okozel.ristral.backend.service.RegistratorService;
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

    public static void prevedNaOsobniUcet(SuperadminOrg uzivatel, SchemaService schemaService, UzivatelService uzivatelService) {
        uzivatel.getSchema().setNazev(uzivatel.getJmeno());
        uzivatel.getSchema().setTypSchematu(TypSchematu.OSOBNI);
        schemaService.save(uzivatel.getSchema());
        //
        uzivatelService.deleteAll(uzivatel.getSchema());
        uzivatelService.save(uzivatel.getOsobniUzivatel());
    }

    @Override
    public void prevedNaUcetOrganizace(SchemaService schemaService, UzivatelService uzivatelService) {
        LOGGER.warn(String.format("Superadministrátora %s (%s) nemá smysl převádět na účet organizace, jelikož ním už je.", this.getUzivatelskeJmeno(), this.getSchema().getNazev()));
    }

    public void prevedNaOsobniUcet(SchemaService schemaService, UzivatelService uzivatelService) {
        prevedNaOsobniUcet(this, schemaService, uzivatelService);
    }

    public boolean nastavRoliUctu(Uzivatel ucetKUprave, Role novaRole, UzivatelService uzivatelService) {
        if (ucetKUprave.getRole() == Role.SUPERADMIN_ORG && !ucetKUprave.equals(this)) return false;
        if (ucetKUprave.getRole() == novaRole) return true;
        switch (novaRole) {
            case UZIVATEL_ORG:
                uzivatelService.delete(ucetKUprave);
                uzivatelService.save(ucetKUprave.getUzivatelOrg());
                break;
            case ADMIN_ORG:
                uzivatelService.delete(ucetKUprave);
                uzivatelService.save(ucetKUprave.getAdminOrg());
                break;
            case SUPERADMIN_ORG:
                uzivatelService.delete(ucetKUprave);
                uzivatelService.save(ucetKUprave.getSuperadminOrg());
            default:
                return false;
        }
        return true;
    }

    public void zaregistrujAPridejUcet(Uzivatel ucetKPridani, RegistratorService registratorService) {
        if (ucetKPridani.getRole() == Role.OSOBNI_UZIVATEL) return;
        //
        ucetKPridani.setSchema(getSchema());
        registratorService.zaregistrujPodrizenyUcet(ucetKPridani);
    }

}
