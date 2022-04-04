package cz.okozel.ristral.backend.entity.lines;

import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.vozidla.TypVozidla;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "lines_") //lines is a reserved word, so this table must be named lines_
public class Line extends AbstractSchemaEntity {

    @NotBlank
    @Size(max = 5)
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

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

    public TypVozidla getPrefVehicleType() {
        return prefVehicleType;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrefVehicleType(TypVozidla prefVehicleType) {
        this.prefVehicleType = prefVehicleType;
    }
}
