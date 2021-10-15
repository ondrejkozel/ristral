package cz.okozel.ristral.backend.aktivity;

import cz.okozel.ristral.backend.uzivatele.Uzivatel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Třída reprezentující určitý úkon uživatele.
 */
@Entity
@Table(name = "aktivity")
public class Aktivita {

    /**
     * identifikátor aktivity
     */
    @Id
    @GeneratedValue()
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private TypAktivity typ;

    @Size(max = 35)
    @NotBlank
    private String titulek;

    @Size(max = 100)
    @NotBlank
    private String popis;

    @NotNull
    private LocalDateTime casUskutecneni;

    @ManyToOne()
    @NotNull
    private Uzivatel akter;

    public Aktivita() {}

    public Aktivita(TypAktivity typ, String titulek, String popis, LocalDateTime casUskutecneni, Uzivatel akter) {
        this.typ = typ;
        this.titulek = titulek;
        this.popis = popis;
        this.casUskutecneni = casUskutecneni;
        this.akter = akter;
    }

    public Long getId() {
        return id;
    }

    public TypAktivity getTyp() {
        return typ;
    }

    public String getTitulek() {
        return titulek;
    }

    public String getPopis() {
        return popis;
    }

    public LocalDateTime getCasUskutecneni() {
        return casUskutecneni;
    }

    public Uzivatel getAkter() {
        return akter;
    }
}