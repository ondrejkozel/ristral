package cz.okozel.ristral.backend.entity.vozidla;

import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.schema.Schema;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "typy_vozidel")
public class TypVozidla extends AbstractSchemaEntity {

    private String nazev;

    @OneToMany(mappedBy = "typ", cascade = CascadeType.REMOVE)
    private Set<Vozidlo> vozidla;

    public TypVozidla() {}

    public TypVozidla(String nazev, Schema schema) {
        super(schema);
        this.nazev = nazev;
        vozidla = new HashSet<>();
    }

    public String getNazev() {
        return nazev;
    }

}
