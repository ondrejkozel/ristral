package cz.okozel.ristral.backend.entity.trips;

import cz.okozel.ristral.backend.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "trip_route_link_data")
public class TripRouteLinkData extends AbstractEntity {

    @NotNull
    private final LocalDateTime timeOfDeparture;

    @NotNull
    private final LocalDateTime timeOfArrival;

    private final LocalDateTime actualTimeOfDeparture;

    private final LocalDateTime actualTimeOfArrival;

    private final Integer passengers;

    public TripRouteLinkData(Long id, LocalDateTime timeOfDeparture, LocalDateTime timeOfArrival, LocalDateTime actualTimeOfDeparture, LocalDateTime actualTimeOfArrival, Integer passengers) {
        super(id);
        this.timeOfDeparture = timeOfDeparture;
        this.timeOfArrival = timeOfArrival;
        this.actualTimeOfDeparture = actualTimeOfDeparture;
        this.actualTimeOfArrival = actualTimeOfArrival;
        this.passengers = passengers;
    }

    public TripRouteLinkData(LocalDateTime timeOfDeparture, LocalDateTime timeOfArrival) {
        this.timeOfDeparture = timeOfDeparture;
        this.timeOfArrival = timeOfArrival;
        actualTimeOfDeparture = null;
        actualTimeOfArrival = null;
        passengers = 0;
    }

    public TripRouteLinkData() {
        timeOfDeparture = null;
        timeOfArrival = null;
        actualTimeOfDeparture = null;
        actualTimeOfArrival = null;
        passengers = null;
    }

    public LocalDateTime getTimeOfDeparture() {
        return timeOfDeparture;
    }

    public LocalDateTime getTimeOfArrival() {
        return timeOfArrival;
    }

    public Optional<LocalDateTime> getActualTimeOfDeparture() {
        return Optional.ofNullable(actualTimeOfDeparture);
    }

    public Optional<LocalDateTime> getActualTimeOfArrival() {
        return Optional.ofNullable(actualTimeOfArrival);
    }

    public Optional<Integer> getPassengers() {
        return Optional.ofNullable(passengers);
    }

    public TripRouteLinkData withActualTimeOfDeparture(LocalDateTime newActualTimeOfDeparture) {
        return new TripRouteLinkData(getId(), timeOfDeparture, timeOfArrival, newActualTimeOfDeparture, actualTimeOfArrival, passengers);
    }

    public TripRouteLinkData withActualTimeOfArrival(LocalDateTime newActualTimeOfArrival) {
        return new TripRouteLinkData(getId(), timeOfDeparture, timeOfArrival, actualTimeOfDeparture, newActualTimeOfArrival, passengers);
    }

    public TripRouteLinkData withPassengers(int newPassengers) {
        return new TripRouteLinkData(getId(), timeOfDeparture, timeOfArrival, actualTimeOfDeparture, actualTimeOfArrival, newPassengers);
    }
}
