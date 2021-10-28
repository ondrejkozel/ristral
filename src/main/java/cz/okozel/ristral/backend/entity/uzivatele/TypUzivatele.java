package cz.okozel.ristral.backend.entity.uzivatele;

import java.util.HashMap;

public enum TypUzivatele {
    UZIVATEL_ORG("uživatel organizace", UzivatelOrg.class),
    ADMIN_ORG("administrátor organizace", AdminOrg.class),
    OSOBNI_UZIVATEL("uživatel osobního účtu", OsobniUzivatel.class),
    SUPERADMIN_ORG("superadministrátor organizace", SuperadminOrg.class);

    private final String nazev;
    private final Class<? extends Uzivatel> trida;

    TypUzivatele(String nazev, Class<? extends Uzivatel> trida) {
        this.nazev = nazev;
        this.trida = trida;
    }

    public String getNazev() {
        return nazev;
    }

    @Override
    public String toString() {
        return nazev;
    }

    private static final HashMap<Class<? extends Uzivatel>, TypUzivatele> TYPY_UZIVATELU = new HashMap<>();

    static {
        for (TypUzivatele value : values()) {
            TYPY_UZIVATELU.put(value.trida, value);
        }
        System.out.println(TYPY_UZIVATELU);
    }

    public static TypUzivatele getTypUzivatele(Class<? extends Uzivatel> trida) {
        for (TypUzivatele typUzivatele : values()) {
            if (typUzivatele.trida.equals(trida)) return typUzivatele;
        }
        return null;
    }

}
