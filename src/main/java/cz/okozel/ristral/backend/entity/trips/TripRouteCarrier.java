package cz.okozel.ristral.backend.entity.trips;

import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.routes.Route;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "trip_routes")
public class TripRouteCarrier extends AbstractSchemaEntity {

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="trip_route_id")
    @OrderColumn(name="order_")
    @Cascade(CascadeType.ALL)
    @NotNull
    private List<TripRouteLinkCarrier> linkCarriers;

    @OneToOne
    @JoinColumn
    @NotNull
    private Trip associatedTrip;

    @NotNull
    private LocalDateTime timeOfDeparture;

    @NotNull
    private LocalDateTime timeOfArrival;

    public TripRouteCarrier() {
    }

    public TripRouteCarrier(Route<String, TripRouteLinkData> route, Trip associatedTrip) {
        super(associatedTrip.getSchema());
        linkCarriers = route.links().stream().map(TripRouteLinkCarrier::new).collect(Collectors.toList());
        this.associatedTrip = associatedTrip;
        timeOfDeparture = linkCarriers.get(0).getLink().edge().getTimeOfDeparture();
        timeOfArrival = linkCarriers.get(linkCarriers.size() - 1).getLink().edge().getTimeOfArrival();
    }

    public Route<String, TripRouteLinkData> getTripRoute() {
        Route.Joiner<String, TripRouteLinkData> joiner = Route.empty();
        linkCarriers.forEach(linkCarrier -> joiner.join(linkCarrier.getLink()));
        return joiner.finishOptionally().orElseThrow();
    }

    public Trip getAssociatedTrip() {
        return associatedTrip;
    }

    public LocalDateTime getTimeOfDeparture() {
        return timeOfDeparture;
    }

    public LocalDateTime getTimeOfArrival() {
        return timeOfArrival;
    }
}
