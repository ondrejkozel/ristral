package cz.okozel.ristral.backend.entity.lines;

import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.routes.Link;
import cz.okozel.ristral.backend.entity.routes.NamedView;
import cz.okozel.ristral.backend.entity.routes.Route;
import cz.okozel.ristral.backend.entity.trips.TripRouteLinkData;
import cz.okozel.ristral.backend.entity.zastavky.Zastavka;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "line_routes")
public class LineRouteCarrier extends AbstractSchemaEntity {

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="line_route_id")
    @OrderColumn(name="order_")
    @Cascade(CascadeType.ALL)
    @NotNull
    private List<LineRouteLinkCarrier> linkCarriers;

    @NotBlank
    private String name;

    @NotNull
    private boolean isVisible;

    @ManyToOne
    @JoinColumn
    @NotNull
    private Line associatedLine;

    public LineRouteCarrier() {}

    public LineRouteCarrier(NamedView<Route<Zastavka, LineRouteLinkData>> routeView, Line associatedLine) {
        super(associatedLine.getSchema());
        linkCarriers = routeView.getData().links().stream().map(LineRouteLinkCarrier::new).collect(Collectors.toList());
        name = routeView.getName();
        isVisible = routeView.isVisible();
        this.associatedLine = associatedLine;
    }

    public NamedView<Route<Zastavka, LineRouteLinkData>> getLineRoute() {
        return new NamedView<>(buildRoute(), name, isVisible);
    }

    private Route<Zastavka, LineRouteLinkData> buildRoute() {
        Route.Joiner<Zastavka, LineRouteLinkData> joiner = Route.empty();
        linkCarriers.forEach(linkCarrier -> joiner.join(linkCarrier.getLink()));
        return joiner.finishOptionally().orElseThrow();
    }

    public static Route<String, TripRouteLinkData> buildTripRoute(Route<Zastavka, LineRouteLinkData> lineRoute, LocalDateTime timeOfDeparture) {
        Route.Joiner<String, TripRouteLinkData> joiner = Route.empty();
        for (Link<Zastavka, LineRouteLinkData> link : lineRoute.links()) {
            LocalDateTime arrivalToNext = timeOfDeparture.plus(link.edge().getDuration());
            joiner.join(Link.build(link.from().getNazev(), link.to().getNazev(), new TripRouteLinkData(timeOfDeparture, arrivalToNext)));
            timeOfDeparture = arrivalToNext;
        }
        return joiner.finishOptionally().orElseThrow();
    }
}
