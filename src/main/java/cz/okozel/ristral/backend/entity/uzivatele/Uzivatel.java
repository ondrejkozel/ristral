package cz.okozel.ristral.backend.entity.uzivatele;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.schema.Schema;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * Třída reprezentující jakéhokoliv registrovaného uživatele.
 */
@Entity
@Table(name = "uzivatele")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "typ_uzivatele", discriminatorType = DiscriminatorType.STRING)
public abstract class Uzivatel extends AbstractSchemaEntity {

    /**
     * jméno uživatele
     */
    @Size(max = 50)
    @NotBlank
    private String jmeno;

    /**
     * email uživatele
     */
    @Email
    @NotBlank
    private String email;

    /**
     * uživatelské jméno
     */
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^(\\w|\\.)*$", message = "může obsahovat pouze písmena bez diakritiky, čísla, podtržítko a tečku")
    @NotBlank
    private String uzivatelskeJmeno;

    /**
     * uživatelovo zahashované heslo
     */
    @NotNull
    @JsonIgnore
    private String heslo;

    /**
     * Vytvoří novou instanci čistého uživatele.
     */
    public Uzivatel() {}

    /**
     * Vytvoří novou instanci uživatele.
     * @param uzivatelskeJmeno uživatelské jméno
     * @param jmeno křestní a příjmení
     * @param email emailová adresa
     * @param heslo zahashované heslo
     * @param schema schéma, ve kterém se má nový uživatel nacházet
     */
    public Uzivatel(String uzivatelskeJmeno, String jmeno, String email, String heslo, Schema schema) {
        super(schema);
        this.uzivatelskeJmeno = uzivatelskeJmeno;
        this.jmeno = jmeno;
        this.email = email;
        this.heslo = heslo;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return Role.getRole(this.getClass());
    }

    public boolean isAtLeastAdmin() {
        Role role = getRole();
        return role == Role.ADMIN_ORG || role == Role.SUPERADMIN_ORG;
    }

    public String getUzivatelskeJmeno() {
        return uzivatelskeJmeno;
    }

    public void setUzivatelskeJmeno(String uzivatelskeJmeno) {
        this.uzivatelskeJmeno = uzivatelskeJmeno;
    }

    public String getHeslo() {
        return heslo;
    }

    public void setHeslo(String heslo) {
        this.heslo = heslo;
    }

    protected UzivatelOrg getUzivatelOrg() {
        return new UzivatelOrg(uzivatelskeJmeno, jmeno, email, heslo, getSchema());
    }

    protected AdminOrg getAdminOrg() {
        return new AdminOrg(uzivatelskeJmeno, jmeno, email, heslo, getSchema());
    }

    protected OsobniUzivatel getOsobniUzivatel() {
        return new OsobniUzivatel(uzivatelskeJmeno, jmeno, email, heslo, getSchema());
    }

    protected SuperadminOrg getSuperadminOrg() {
        return new SuperadminOrg(uzivatelskeJmeno, jmeno, email, heslo, getSchema());
    }

    @Override
    public String toString() {
        return String.format("%s %s – %s (schema %s)", getRole(), jmeno, email, getSchema());
    }

}
