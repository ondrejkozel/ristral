package cz.okozel.ristral.backend.schema;

import cz.okozel.ristral.backend.uzivatele.Uzivatel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "schemata")
public class Schema {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TypSchematu typSchematu;

    @Size(min = 1, max = 100)
    @NotBlank
    private String nazev;

    @OneToMany(mappedBy = "schema", cascade = CascadeType.ALL)
    private Set<Uzivatel> uzivatele;

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
