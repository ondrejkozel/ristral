package cz.okozel.ristral.backend.entity.trips;

import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.routes.Route;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "trip_routes")
public class TripRouteCarrier extends AbstractSchemaEntity {

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="trip_route_id")
    @OrderColumn(name="order_")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @NotNull
    private List<TripRouteLinkCarrier> linkCarriers;

    @OneToOne
    @JoinColumn
    @NotNull
    private Trip associatedTrip;

    public TripRouteCarrier() {
    }

    public TripRouteCarrier(Route<String, TripRouteLinkData> route, Trip associatedTrip) {
        super(associatedTrip.getSchema());
        linkCarriers = route.links().stream().map(TripRouteLinkCarrier::new).collect(Collectors.toList());
        this.associatedTrip = associatedTrip;
    }

    public Route<String, TripRouteLinkData> getTripRoute() {
        Route.Joiner<String, TripRouteLinkData> joiner = Route.empty();
        linkCarriers.forEach(linkCarrier -> joiner.join(linkCarrier.getLink()));
        return joiner.finishOptionally().orElseThrow();
    }
}
