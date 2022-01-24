package cz.okozel.ristral.backend.entity.trips;

import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.lines.Line;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.entity.vozidla.Vozidlo;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

@Entity
@Table(name = "trips")
public class Trip extends AbstractSchemaEntity {

    @ManyToOne
    @JoinColumn
    private Line line;

    private String deletedLineLabel;

    @ManyToOne
    @JoinColumn
    private Vozidlo vehicle;

    private String deletedVehicleName;

    @ManyToOne
    @JoinColumn
    private Uzivatel user;

    private String deletedUserName;

    @Size(max = 250)
    @NotNull
    private String description;

    public Optional<Line> getLine() {
        return Optional.ofNullable(line);
    }

    public Optional<Vozidlo> getVehicle() {
        return Optional.ofNullable(vehicle);
    }

    public Optional<Uzivatel> getUser() {
        return Optional.ofNullable(user);
    }

    public String getLineLabel() {
        return getLine().isPresent() ? line.getLabel() : deletedLineLabel;
    }

    public String getVehicleName() {
        return getVehicle().isPresent() ? vehicle.getNazev() : deletedVehicleName;
    }

    public String getUserName() {
        return getUser().isPresent() ? user.getJmeno() : deletedUserName;
    }

    public String getDescription() {
        return description;
    }
}
