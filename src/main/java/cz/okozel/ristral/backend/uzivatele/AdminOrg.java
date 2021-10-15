package cz.okozel.ristral.backend.uzivatele;

import cz.okozel.ristral.backend.schema.Schema;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN_ORG")
public class AdminOrg extends UzivatelOrg {

    public AdminOrg() {
        super();
    }

    public AdminOrg(String jmeno, String email, String heslo, Schema schema) {
        super(jmeno, email, heslo, schema);
    }

}
