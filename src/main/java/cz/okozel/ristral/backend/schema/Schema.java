package cz.okozel.ristral.backend.schema;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "schemata")
public class Schema {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private TypSchematu typSchematu;

    @Size(min = 1, max = 100)
    @NotBlank
    private String nazev;

    public Schema() {
    }

    public Schema(TypSchematu typSchematu, String nazev) {
        this.typSchematu = typSchematu;
        this.nazev = nazev;
    }

    public Long getId() {
        return Id;
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
}
