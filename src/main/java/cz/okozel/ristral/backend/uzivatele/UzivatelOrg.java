package cz.okozel.ristral.backend.uzivatele;

import javax.persistence.Entity;

@Entity
public class UzivatelOrg extends Uzivatel {

    public UzivatelOrg(String jmeno) {
        super(jmeno);
    }

    public UzivatelOrg() {
        super();
    }
}
