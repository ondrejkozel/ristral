package cz.okozel.ristral.backend.entity.vozidla;

import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.schema.Schema;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "typy_vozidel")
public class TypVozidla extends AbstractSchemaEntity {

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

}
