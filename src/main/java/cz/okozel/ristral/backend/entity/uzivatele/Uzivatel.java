package cz.okozel.ristral.backend.entity.uzivatele;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.aktivity.Aktivita;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.vztahy.NavazujeObousmernyVztah;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Třída reprezentující jakéhokoliv registrovaného uživatele.
 */
@Entity
@Table(name = "uzivatele")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "typ_uzivatele", discriminatorType = DiscriminatorType.STRING)
public abstract class Uzivatel extends AbstractSchemaEntity implements NavazujeObousmernyVztah<Aktivita> {

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
     * uživatelovo zahashované heslo
     */
    @NotNull
    @JsonIgnore
    private String heslo;

    @OneToMany(mappedBy = "akter", cascade = CascadeType.ALL)
    private List<Aktivita> aktivity;

    /**
     * Vytvoří novou instanci čistého uživatele.
     */
    public Uzivatel() {}

    /**
     * Vytvoří novou instanci uživatele.
     * @param jmeno křestní a příjmení
     * @param email emailová adresa
     * @param heslo zahashované heslo
     * @param schema schéma, ve kterém se má nový uživatel nacházet
     */
    public Uzivatel(String jmeno, String email, String heslo, Schema schema) {
        super(schema);
        this.jmeno = jmeno;
        this.email = email;
        this.heslo = heslo;
        this.aktivity = new ArrayList<>();
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

    public List<Aktivita> getAktivity() {
        return Collections.unmodifiableList(aktivity);
    }

    @Override
    public boolean overSpojeniS(Aktivita objekt) {
        return aktivity.contains(objekt);
    }

    @Override
    public void navazSpojeniS(Aktivita objekt) {
        aktivity.add(objekt);
    }

    @Override
    public void rozvazSpojeniS(Aktivita objekt) {
        aktivity.remove(objekt);
    }

    @Override
    public String toString() {
        return String.format("%s %s – %s (schema %s)", getRole(), jmeno, email, getSchema());
    }
}
