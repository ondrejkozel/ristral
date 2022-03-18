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
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "line_routes")
public class LineRouteCarrier extends AbstractSchemaEntity {

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="line_route_id")
    @OrderColumn(name="order_")
    @Cascade(CascadeType.ALL)
    private List<LineRouteLinkCarrier> linkCarriers;

    @Size(max = 50)
    @NotBlank
    private String name;

    @Size(max = 250)
    @NotNull
    private String description;

    @NotNull
    private boolean visible;

    @ManyToOne
    @JoinColumn
    @NotNull
    private Line associatedLine;

    public LineRouteCarrier() {
        description = "";
        visible = false;
        linkCarriers = new ArrayList<>();
    }

    public LineRouteCarrier(Line associatedLine) {
        super(associatedLine.getSchema());
        this.associatedLine = associatedLine;
    }

    public LineRouteCarrier(NamedView<Route<Zastavka, LineRouteLinkData>> routeView, Line associatedLine) {
        super(associatedLine.getSchema());
        linkCarriers = routeView.getData().links().stream().map(LineRouteLinkCarrier::new).collect(Collectors.toList());
        name = routeView.getName();
        visible = routeView.isVisible();
        this.associatedLine = associatedLine;
        description = "";
    }

    public NamedView<Route<Zastavka, LineRouteLinkData>> buildLineRoute() throws EmptyRouteException {
        return new NamedView<>(buildRoute(), name, visible);
    }

    private Route<Zastavka, LineRouteLinkData> buildRoute() throws EmptyRouteException {
        Route.Joiner<Zastavka, LineRouteLinkData> joiner = Route.empty();
        linkCarriers.forEach(linkCarrier -> joiner.join(linkCarrier.getLink()));
        var finished = joiner.finishOptionally();
        if (finished.isEmpty()) throw new EmptyRouteException("Route has no links.");
        return finished.get();
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
