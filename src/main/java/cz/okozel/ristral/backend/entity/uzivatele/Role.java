package cz.okozel.ristral.backend.entity.uzivatele;

import java.util.HashMap;

public enum Role {
    UZIVATEL_ORG("uživatel organizace", "uzivatelOrg", UzivatelOrg.class),
    ADMIN_ORG("administrátor organizace", "adminOrg", AdminOrg.class),
    OSOBNI_UZIVATEL("uživatel osobního účtu", "osobni", OsobniUzivatel.class),
    SUPERADMIN_ORG("superadministrátor organizace", "superadminOrg", SuperadminOrg.class);

    private final String nazev;
    private final String kratkyNazev;
    private final Class<? extends Uzivatel> trida;

    Role(String nazev, String kratkyNazev, Class<? extends Uzivatel> trida) {
        this.nazev = nazev;
        this.kratkyNazev = kratkyNazev;
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

    public String getKratkyNazev() {
        return kratkyNazev;
    }

}
