package cz.okozel.ristral.backend.entity.routes;

import java.util.Objects;

/**
 * The default implementation of {@link Link}.
 *
 * @param <V>
 *            the type of the vertex
 * @param <E>
 *
 */
final class DefaultLink<V, E> implements Link<V, E> {

    private final V from;
    private final V to;
    private final E edge;

    DefaultLink(V from, V to, E edge) {
        this.from = from;
        this.to = to;
        this.edge = edge;
    }

    /**
     * @see Link#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(from, to, edge);
    }

    @Override
    public E invertEdge(E edge) {
        return edge;
    }

    public V from() {
        return from;
    }

    public V to() {
        return to;
    }

    public E edge() {
        return edge;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        @SuppressWarnings("rawtypes") var that = (DefaultLink) obj;
        return Objects.equals(this.from, that.from) &&
                Objects.equals(this.to, that.to) &&
                Objects.equals(this.edge, that.edge);
    }

    @Override
    public String toString() {
        return "DefaultLink[" +
                "from=" + from + ", " +
                "to=" + to + ", " +
                "edge=" + edge + ']';
    }

}
