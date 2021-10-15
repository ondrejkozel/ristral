package cz.okozel.ristral.backend.uzivatele;

import cz.okozel.ristral.backend.schema.Schema;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SUPERADMIN_ORG")
public class SuperadminOrg extends OsobniUzivatel {

    public SuperadminOrg() {
        super();
    }

    public SuperadminOrg(String jmeno, String email, String heslo, Schema schema) {
        super(jmeno, email, heslo, schema);
    }

}
