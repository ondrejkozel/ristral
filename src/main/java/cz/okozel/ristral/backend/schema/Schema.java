package cz.okozel.ristral.backend.schema;

import cz.okozel.ristral.backend.AbstractEntity;
import cz.okozel.ristral.backend.uzivatele.Uzivatel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "schemata")
public class Schema extends AbstractEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    private TypSchematu typSchematu;

    @Size(max = 100)
    @NotNull
    private String nazev;

    @OneToMany(mappedBy = "schema", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Uzivatel> uzivatele;

    public Schema() {
    }

    public Schema(TypSchematu typSchematu, String nazev) {
        this.typSchematu = typSchematu;
        this.nazev = nazev;
        this.uzivatele = new HashSet<>();
    }

    public TypSchematu getTypSchematu() {
        return typSchematu;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public Set<Uzivatel> getUzivatele() {
        return Collections.unmodifiableSet(uzivatele);
    }

    @Override
    public String toString() {
        return nazev;
    }
}
