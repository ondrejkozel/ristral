package cz.okozel.ristral.backend.entity.vozidla;

import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.vztahy.NavazujeObousmernyVztah;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "typy_vozidel")
public class TypVozidla extends AbstractSchemaEntity implements NavazujeObousmernyVztah<Vozidlo> {

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

    @Override
    public boolean overSpojeniS(Vozidlo objekt) {
        return vozidla.contains(objekt);
    }

    @Override
    public void navazSpojeniS(Vozidlo objekt) {
        vozidla.add(objekt);
    }

    @Override
    public void rozvazSpojeniS(Vozidlo objekt) {
        vozidla.remove(objekt);
    }
}
