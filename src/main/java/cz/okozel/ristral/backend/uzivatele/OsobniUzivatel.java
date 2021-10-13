package cz.okozel.ristral.backend.uzivatele;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("OSOBNI_UZIVATEL")
public class OsobniUzivatel extends AdminOrg {

    public OsobniUzivatel() {
        super();
    }

    public OsobniUzivatel(String jmeno, String email, String heslo) {
        super(jmeno, email, heslo);
    }

}
