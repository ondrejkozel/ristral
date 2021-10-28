package cz.okozel.ristral.backend.entity.aktivity;

import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.entity.vztahy.NavazujeObousmernyVztah;

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
public class Aktivita extends AbstractSchemaEntity implements NavazujeObousmernyVztah<Uzivatel> {

    @Enumerated(value = EnumType.STRING)
    @NotNull
    private TypAktivity typ;

    @Size(max = 35)
    @NotBlank
    private String titulek;

    @Size(max = 100)
    @NotBlank
    private String popis;

    @NotNull
    private LocalDateTime casUskutecneni;

    @ManyToOne
    @JoinColumn
    @NotNull
    private Uzivatel akter;

    public Aktivita() {}

    public Aktivita(TypAktivity typ, String titulek, String popis, LocalDateTime casUskutecneni, Uzivatel akter, Schema schema) {
        super(schema);
        this.typ = typ;
        this.titulek = titulek;
        this.popis = popis;
        this.casUskutecneni = casUskutecneni;
        vynutPritomnostSpojeni(akter);
    }

    public Aktivita(TypAktivity typ, String titulek, String popis, LocalDateTime casUskutecneni, Uzivatel akter) {
        this(typ, titulek, popis, casUskutecneni, akter, akter != null ? akter.getSchema() : null);
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

    @Override
    public String toString() {
        return titulek;
    }

    @Override
    public boolean overSpojeniS(Uzivatel objekt) {
        return akter != null && akter.equals(objekt);
    }

    @Override
    public void navazSpojeniS(Uzivatel objekt) {
        akter = objekt;
    }

    @Override
    public void rozvazSpojeniS(Uzivatel objekt) {
        akter = null;
    }
}
