package cz.okozel.ristral.backend.entity.uzivatele;

import cz.okozel.ristral.backend.entity.schema.Schema;

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

}
