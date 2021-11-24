package cz.okozel.ristral.backend.entity.zastavky;

import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.schema.Schema;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "zastavky")
public class Zastavka extends AbstractSchemaEntity {

    @Size(max = 50)
    @NotBlank
    private String nazev;

    @Size(max = 250)
    @NotNull
    private String popis;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn
    @NotNull
    private RezimObsluhy rezimObsluhy;

    public Zastavka() {}

    public Zastavka(String nazev, String popis, RezimObsluhy rezimObsluhy, Schema schema) {
        super(schema);
        this.nazev = nazev;
        this.popis = popis;
        setRezimObsluhy(rezimObsluhy);
    }

    public Zastavka(String nazev, Schema schema) {
        this(nazev, "", null, schema);
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public RezimObsluhy getRezimObsluhy() {
        return rezimObsluhy;
    }

    public void setRezimObsluhy(RezimObsluhy rezimObsluhy) {
        this.rezimObsluhy = rezimObsluhy;
    }
}
