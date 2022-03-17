package cz.okozel.ristral.backend.entity.aktivity;

import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Třída reprezentující určitý úkon uživatele.
 */
@Entity
@Table(name = "activities")
public class Aktivita extends AbstractSchemaEntity {

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

    public Aktivita() {}

    public Aktivita(TypAktivity typ, String titulek, String popis, LocalDateTime casUskutecneni, Schema schema) {
        super(schema);
        this.typ = typ;
        this.titulek = titulek;
        this.popis = popis;
        this.casUskutecneni = casUskutecneni;
    }

    public Aktivita(TypAktivity typ, String titulek, String popis, LocalDateTime casUskutecneni, Uzivatel akter) {
        this(typ, titulek, popis, casUskutecneni, akter != null ? akter.getSchema() : null);
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

    @Override
    public String toString() {
        return titulek;
    }

}
