package cz.okozel.ristral.backend.uzivatele;

import cz.okozel.ristral.backend.schema.Schema;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("UZIVATEL_ORG")
public class UzivatelOrg extends Uzivatel {

    public UzivatelOrg() {
        super();
    }

    public UzivatelOrg(String jmeno, String email, String heslo, Schema schema) {
        super(jmeno, email, heslo, schema);
    }

}
