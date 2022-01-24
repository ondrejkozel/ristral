package cz.okozel.ristral.backend.entity.trips;

import cz.okozel.ristral.backend.entity.AbstractEntity;
import cz.okozel.ristral.backend.entity.routes.Link;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * from and to are reserved words
 */
@Entity
@Table(name = "trip_route_links")
public class TripRouteLinkCarrier extends AbstractEntity {

    @NotBlank
    private final String from_;

    @NotBlank
    private final String to_;

    @OneToOne
    @JoinColumn
    @NotNull
    @Cascade(CascadeType.ALL)
    private final TripRouteLinkData edge;

    public TripRouteLinkCarrier() {
        from_ = null;
        to_ = null;
        edge = null;
    }

    public TripRouteLinkCarrier(Link<String, TripRouteLinkData> link) {
        from_ = link.from();
        to_ = link.to();
        edge = link.edge();
    }

    public Link<String, TripRouteLinkData> getLink() {
        return Link.build(from_, to_, edge);
    }
}
