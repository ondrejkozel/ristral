package cz.okozel.ristral.backend.entity.lines;

import cz.okozel.ristral.backend.entity.AbstractEntity;
import cz.okozel.ristral.backend.entity.routes.Link;
import cz.okozel.ristral.backend.entity.zastavky.Zastavka;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "line_route_links")
public class LineRouteLinkCarrier extends AbstractEntity {

    @ManyToOne
    @JoinColumn
    @NotNull
    private final Zastavka from;

    @ManyToOne
    @JoinColumn
    @NotNull
    private final Zastavka to;

    @OneToOne
    @JoinColumn
    @NotNull
    @Cascade(CascadeType.ALL)
    private final LineRouteLinkData edge;

    public LineRouteLinkCarrier() {
        from = null;
        to = null;
        edge = null;
    }

    public LineRouteLinkCarrier(Link<Zastavka, LineRouteLinkData> link) {
        from = link.from();
        to = link.to();
        edge = link.edge();
    }

    public Link<Zastavka, LineRouteLinkData> getLink() {
        return Link.build(from, to, edge);
    }

}
