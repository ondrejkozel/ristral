package cz.okozel.ristral.backend.entity.vozidla;

import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.schema.Schema;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "vehicle_types")
public class TypVozidla extends AbstractSchemaEntity {

    @Size(max = 50)
    @NotBlank
    private String nazev;

    public TypVozidla() {}

    public TypVozidla(String nazev, Schema schema) {
        super(schema);
        this.nazev = nazev;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    @Override
    public String toString() {
        return nazev;
    }

}
