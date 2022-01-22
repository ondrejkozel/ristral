package cz.okozel.ristral.backend.entity.lines;

import cz.okozel.ristral.backend.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Duration;

@Entity
@Table(name = "line_route_link_data")
public class LineRouteLinkData extends AbstractEntity {

    /**
     * Duration to the next stop.
     */
    private final Duration duration;

    public LineRouteLinkData() {
        duration = null;
    }

    public LineRouteLinkData(Duration duration) {
        this.duration = duration;
    }

    public LineRouteLinkData(Duration duration, Long id) {
        super(id);
        this.duration = duration;
    }

    public Duration getDuration() {
        return duration;
    }

    public LineRouteLinkData withDuration(Duration duration) {
        return new LineRouteLinkData(duration, getId());
    }
}
