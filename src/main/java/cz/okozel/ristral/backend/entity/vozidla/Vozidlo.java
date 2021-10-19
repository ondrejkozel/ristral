package cz.okozel.ristral.backend.entity.vozidla;

import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.schema.Schema;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
    @Size(min = 1)
    private Integer kapacita;

    @ManyToOne()
    @JoinColumn()
    @NotNull
    private TypVozidla typ;

    public Vozidlo() {}

    public Vozidlo(String nazev, String popis, Integer kapacita, TypVozidla typ, Schema schema) {
        super(schema);
        this.nazev = nazev;
        this.popis = popis;
        this.kapacita = kapacita;
        this.typ = typ;
    }

    public String getNazev() {
        return nazev;
    }

    public String getPopis() {
        return popis;
    }

    public Integer getKapacita() {
        return kapacita;
    }

    public TypVozidla getTyp() {
        return typ;
    }
}
