package cz.okozel.ristral.backend.entity.vozidla;

import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.schema.Schema;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "vozidla")
public class Vozidlo extends AbstractSchemaEntity {

    @Size(max = 50)
    @NotBlank
    private String nazev;

    @Size(max = 250)
    @NotNull
    private String popis;

    /**
     * maximální počet pasažérů
     */
    @Min(0)
    private int obsaditelnost;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn()
    @NotNull
    private TypVozidla typ;

    public Vozidlo() {}

    public Vozidlo(String nazev, String popis, int obsaditelnost, TypVozidla typ, Schema schema) {
        super(schema);
        this.nazev = nazev;
        this.popis = popis;
        this.obsaditelnost = obsaditelnost;
        this.typ = typ;
    }

    public String getNazev() {
        return nazev;
    }

    public String getPopis() {
        return popis;
    }

    public int getObsaditelnost() {
        return obsaditelnost;
    }

    public TypVozidla getTyp() {
        return typ;
    }

}
