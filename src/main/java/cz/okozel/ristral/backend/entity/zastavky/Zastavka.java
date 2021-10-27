package cz.okozel.ristral.backend.entity.zastavky;

import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.vztahy.NavazujeObousmernyVztah;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "zastavky")
public class Zastavka extends AbstractSchemaEntity implements NavazujeObousmernyVztah<RezimObsluhy> {

    @Size(max = 50)
    @NotBlank
    private String nazev;

    @Size(max = 250)
    @NotNull
    private String popis;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn
    private RezimObsluhy rezimObsluhy;

    public Zastavka() {}

    public Zastavka(String nazev, String popis, RezimObsluhy rezimObsluhy, Schema schema) {
        super(schema);
        this.nazev = nazev;
        this.popis = popis;
        vynutPritomnostSpojeni(rezimObsluhy);
    }

    public String getNazev() {
        return nazev;
    }

    public String getPopis() {
        return popis;
    }

    public RezimObsluhy getRezimObsluhy() {
        return rezimObsluhy;
    }

    @Override
    public boolean overSpojeniS(RezimObsluhy objekt) {
        return rezimObsluhy != null && rezimObsluhy.equals(objekt);
    }

    @Override
    public void navazSpojeniS(RezimObsluhy objekt) {
        rezimObsluhy = objekt;
    }

    @Override
    public void rozvazSpojeniS(RezimObsluhy objekt) {
        rezimObsluhy = null;
    }
}
