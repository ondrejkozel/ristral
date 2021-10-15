package cz.okozel.ristral.backend.uzivatele;

import cz.okozel.ristral.backend.schema.Schema;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("OSOBNI_UZIVATEL")
public class OsobniUzivatel extends AdminOrg {

    public OsobniUzivatel() {
        super();
    }

    public OsobniUzivatel(String jmeno, String email, String heslo, Schema schema) {
        super(jmeno, email, heslo, schema);
    }

}
