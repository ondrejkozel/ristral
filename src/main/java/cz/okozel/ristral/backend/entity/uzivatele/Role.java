package cz.okozel.ristral.backend.entity.uzivatele;

import cz.okozel.ristral.frontend.LineAwesomeIcon;

import java.util.HashMap;

public enum Role {
    UZIVATEL_ORG("řadový uživatel", "uzivatelOrg", LineAwesomeIcon.UZIVATEL, UzivatelOrg.class),
    ADMIN_ORG("administrátor ", "adminOrg", LineAwesomeIcon.ADMINISTRATOR, AdminOrg.class),
    OSOBNI_UZIVATEL("uživatel osobního účtu", "osobni", LineAwesomeIcon.UZIVATEL, OsobniUzivatel.class),
    SUPERADMIN_ORG("superadministrátor", "superadminOrg", LineAwesomeIcon.SUPERADMINISTRATOR, SuperadminOrg.class);

    private final String nazev;
    private final String kratkyNazev;
    private final LineAwesomeIcon ikona;
    private final Class<? extends Uzivatel> trida;

    Role(String nazev, String kratkyNazev, LineAwesomeIcon ikona, Class<? extends Uzivatel> trida) {
        this.nazev = nazev;
        this.kratkyNazev = kratkyNazev;
        this.ikona = ikona;
        this.trida = trida;
    }

    public String getNazev() {
        return nazev;
    }

    public String getKratkyNazev() {
        return kratkyNazev;
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
        return ROLE.get(trida);
    }

    public LineAwesomeIcon getIkona() {
        return ikona;
    }

}
