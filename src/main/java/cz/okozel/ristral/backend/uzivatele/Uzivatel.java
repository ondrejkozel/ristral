package cz.okozel.ristral.backend.uzivatele;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Třída reprezentující jakéhokoliv registrovaného uživatele.
 */
@Entity
@Table(name = "uzivatele")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "typ_uzivatele", discriminatorType = DiscriminatorType.STRING)
public abstract class Uzivatel {

    /**
     * identifikátor uživatele
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * jméno uživatele
     */
    @Size(min = 1, max = 50)
    @NotBlank
    private String jmeno;

    /**
     * email uživatele
     */
    @Email
    private String email;

    /**
     * uživatelovo zahashované heslo
     */
    @JsonIgnore
    private String heslo;

    /**
     * Vytvoří novou instanci čistého uživatele.
     */
    public Uzivatel() {}

    /**
     * Vytvoří novou instanci uživatele.
     * @param jmeno křestní a příjmení
     * @param email emailová adresa
     * @param heslo zahashované heslo
     */
    public Uzivatel(String jmeno, String email, String heslo) {
        this.jmeno = jmeno;
        this.email = email;
        this.heslo = heslo;
    }

    public Long getId() {
        return id;
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

    public TypUzivatele getTyp() {
        return TypUzivatele.getTypUzivatele(this.getClass());
    }
}
