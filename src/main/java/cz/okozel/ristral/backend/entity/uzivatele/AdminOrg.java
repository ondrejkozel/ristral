package cz.okozel.ristral.backend.entity.uzivatele;

import cz.okozel.ristral.backend.entity.schema.Schema;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN_ORG")
public class AdminOrg extends UzivatelOrg {

    public AdminOrg() {
        super();
    }

    public AdminOrg(String uzivatelskeJmeno, String jmeno, String email, String heslo, Schema schema) {
        super(uzivatelskeJmeno, jmeno, email, heslo, schema);
    }

}
