package cz.okozel.ristral.backend.entity.uzivatele;

import cz.okozel.ristral.backend.entity.schema.Schema;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SUPERADMIN_ORG")
public class SuperadminOrg extends OsobniUzivatel {

    public SuperadminOrg() {
        super();
    }

    public SuperadminOrg(String uzivatelskeJmeno, String jmeno, String email, String heslo, Schema schema) {
        super(uzivatelskeJmeno, jmeno, email, heslo, schema);
    }

}
