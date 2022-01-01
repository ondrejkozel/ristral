package cz.okozel.ristral.backend.entity.linky;

/**
 * Represents an elementary link which connects two graph vertices through an
 * edge directly without any other intermediate vertices and edges.
 *
 * @param <V>
 *            the type of the vertices
 * @param <E>
 *            the type of the edge
 */
public interface Link<V, E> extends Direction<V> {

    /**
     * @return the edge representation, never {@code null}
     */
    E edge();

    /**
     * Compares this instance for equality.
     *
     * <p>
     * The contract requires implementations to be equal only and only if all of
     * {@link #from()}, {@link #to()} and {@link #edge()} provide equal results.
     *
     * @see Object#equals(Object)
     */
    @Override
    boolean equals(Object obj);

    /**
     * Computes the hash of this instance.
     *
     * <p>
     * The contract requires the implementation to be equal to
     * {@code Objects.hash(from(), to(), edge())}.
     *
     * @see Object#hashCode()
     */
    @Override
    int hashCode();

    /**
     * Creates an instance.
     *
     * @param <V>
     *            the type of the vertex
     * @param <E>
     *            the type of the edge
     * @param from
     *            the origin vertex. It must not be {@code null}.
     * @param to
     *            the target vertex. It must not be {@code null}.
     * @param edge
     *            the edge representation. It must not be {@code null}.
     *
     * @return the new instance
     */
    static <V, E> Link<V, E> build(V from, V to, E edge) {
        return new DefaultLink<>(from, to, edge);
    }
}
