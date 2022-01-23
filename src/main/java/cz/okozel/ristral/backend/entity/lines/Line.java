package cz.okozel.ristral.backend.entity.lines;

import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.vozidla.TypVozidla;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "lines_")
public class Line extends AbstractSchemaEntity {

    @NotBlank
    private String label;

    @Size(max = 250)
    @NotNull
    private String description;

    @ManyToOne
    @JoinColumn
    private TypVozidla prefVehicleType;

    public Line() {
    }

    public Line(String label, String description, TypVozidla prefVehicleType, Schema schema) {
        super(schema);
        this.label = label;
        this.description = description;
        this.prefVehicleType = prefVehicleType;
    }
}
