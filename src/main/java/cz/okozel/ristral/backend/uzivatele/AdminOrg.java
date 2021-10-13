package cz.okozel.ristral.backend.uzivatele;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN_ORG")
public class AdminOrg extends UzivatelOrg {

    public AdminOrg() {
        super();
    }

    public AdminOrg(String jmeno, String email, String heslo) {
        super(jmeno, email, heslo);
    }

}
