package cz.okozel.ristral.backend.entity.uzivatele;

import java.util.HashMap;

public enum Role {
    UZIVATEL_ORG("uživatel organizace", UzivatelOrg.class),
    ADMIN_ORG("administrátor organizace", AdminOrg.class),
    OSOBNI_UZIVATEL("uživatel osobního účtu", OsobniUzivatel.class),
    SUPERADMIN_ORG("superadministrátor organizace", SuperadminOrg.class);

    private final String nazev;
    private final Class<? extends Uzivatel> trida;

    Role(String nazev, Class<? extends Uzivatel> trida) {
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

    private static final HashMap<Class<? extends Uzivatel>, Role> ROLE = new HashMap<>();

    static {
        for (Role value : values()) {
            ROLE.put(value.trida, value);
        }
    }

    public static Role getRole(Class<? extends Uzivatel> trida) {
        for (Role role : values()) if (role.trida.equals(trida)) return role;
        return null;
    }

}
