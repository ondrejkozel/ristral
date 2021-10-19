package cz.okozel.ristral.backend.entity.uzivatele;

public enum TypUzivatele {
    UZIVATEL_ORG("uživatel organizace", UzivatelOrg.class),
    ADMIN_ORG("administrátor organizace", AdminOrg.class),
    OSOBNI_UZIVATEL("uživatel osobního účtu", OsobniUzivatel.class),
    SUPERADMIN_ORG("superadministrátor organizace", SuperadminOrg.class);

    private String nazev;
    private Class<? extends Uzivatel> trida;

    TypUzivatele(String nazev, Class<? extends Uzivatel> trida) {
        this.nazev = nazev;
        this.trida = trida;
    }

    public String getNazev() {
        return nazev;
    }

    private Class<? extends Uzivatel> getTrida() {
        return trida;
    }

    @Override
    public String toString() {
        return nazev;
    }

    public static TypUzivatele getTypUzivatele(Class<? extends Uzivatel> trida) {
        for (TypUzivatele typUzivatele : values()) {
            if (typUzivatele.trida.equals(trida)) return typUzivatele;
        }
        return null;
    }

}
