package cz.okozel.ristral.backend.entity.vozidla;

import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.vztahy.NavazujeObousmernyVztah;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "vozidla")
public class Vozidlo extends AbstractSchemaEntity implements NavazujeObousmernyVztah<TypVozidla> {

    @Size(max = 50)
    @NotBlank
    private String nazev;

    @Size(max = 250)
    @NotNull
    private String popis;

    /**
     * maximální počet pasažérů
     */
    @Size(min = 1)
    private Integer obsaditelnost;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn()
    @NotNull
    private TypVozidla typ;

    public Vozidlo() {}

    public Vozidlo(String nazev, String popis, Integer obsaditelnost, TypVozidla typ, Schema schema) {
        super(schema);
        this.nazev = nazev;
        this.popis = popis;
        this.obsaditelnost = obsaditelnost;
        vynutPritomnostSpojeni(typ);
    }

    public String getNazev() {
        return nazev;
    }

    public String getPopis() {
        return popis;
    }

    public Integer getObsaditelnost() {
        return obsaditelnost;
    }

    public TypVozidla getTyp() {
        return typ;
    }

    @Override
    public boolean overSpojeniS(TypVozidla objekt) {
        return typ != null && typ.equals(objekt);
    }

    @Override
    public void navazSpojeniS(TypVozidla objekt) {
        typ = objekt;
    }

    @Override
    public void rozvazSpojeniS(TypVozidla objekt) {
        typ = null;
    }

}
