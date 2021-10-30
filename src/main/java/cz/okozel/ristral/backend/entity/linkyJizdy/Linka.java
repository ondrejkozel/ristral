package cz.okozel.ristral.backend.entity.linkyJizdy;

import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.vozidla.TypVozidla;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "linky")
public class Linka extends AbstractSchemaEntity {

    @Size(max = 10)
    @NotBlank
    String cislo;

    @Size(max = 250)
    @NotNull
    String popis;

    @ManyToOne
    @JoinColumn
    TypVozidla prefTypVozidla;



}
