package cz.okozel.ristral.backend.entity.uzivatele;

import cz.okozel.ristral.backend.entity.schema.Schema;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("UZIVATEL_ORG")
public class UzivatelOrg extends Uzivatel {

    public UzivatelOrg() {
        super();
    }

    public UzivatelOrg(String uzivatelskeJmeno, String jmeno, String email, String heslo, Schema schema) {
        super(uzivatelskeJmeno, jmeno, email, heslo, schema);
    }

    public SuperadminOrg vytvorSuperadminOrg() {
        return getSuperadminOrg();
    }

}
