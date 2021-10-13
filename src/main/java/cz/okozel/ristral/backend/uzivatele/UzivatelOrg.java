package cz.okozel.ristral.backend.uzivatele;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("UZIVATEL_ORG")
public class UzivatelOrg extends Uzivatel {

    public UzivatelOrg() {
        super();
    }

    public UzivatelOrg(String jmeno, String email, String heslo) {
        super(jmeno, email, heslo);
    }

}
