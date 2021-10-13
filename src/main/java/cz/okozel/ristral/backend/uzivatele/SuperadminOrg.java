package cz.okozel.ristral.backend.uzivatele;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SUPERADMIN_ORG")
public class SuperadminOrg extends OsobniUzivatel {

    public SuperadminOrg() {
        super();
    }

    public SuperadminOrg(String jmeno, String email, String heslo) {
        super(jmeno, email, heslo);
    }

}
