package cz.okozel.ristral.backend.entity.vozidla;

import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;

import javax.persistence.Entity;
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

}
