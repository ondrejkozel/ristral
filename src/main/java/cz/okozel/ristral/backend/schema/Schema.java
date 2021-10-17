package cz.okozel.ristral.backend.schema;

import cz.okozel.ristral.backend.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "schemata")
public class Schema extends AbstractEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    private TypSchematu typSchematu;

    @Size(max = 100)
    @NotNull
    private String nazev;

    public Schema() {
    }

    public Schema(TypSchematu typSchematu, String nazev) {
        this.typSchematu = typSchematu;
        this.nazev = nazev;
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

    @Override
    public String toString() {
        return nazev;
    }
}
