package cz.okozel.ristral.backend.entity.routes;

import java.util.List;
import java.util.Optional;

/**
 * Represents a route in a graph.
 *
 * <p>
 * A route consists of a list of one or more {@link Link} instances that
 * represent hops of the route.
 *
 * <p>
 * Implementations of this interface must be immutable.
 *
 * @param <V>
 *            the type of vertices
 * @param <E>
 *            the type of edges
 */
public interface Route<V, E> extends Direction<V> {

    /**
     * Compares this instance for equality.
     *
     * <p>
     * The contract requires implementations to be equal only and only if
     * {@link #links()} is equal.
     *
     * @see Object#equals(Object)
     */
    @Override
    boolean equals(Object obj);

    /**
     * Computes the hash of this instance.
     *
     * <p>
     * The result must be the hash of {@link #links()}.
     *
     * @see Object#hashCode()
     */
    @Override
    int hashCode();

    /**
     * @see Direction#from()
     */
    @Override
    default V from() {
        return links().get(0).from();
    }

    /**
     * @see Direction#to()
     */
    @Override
    default V to() {
        return links().get(length() - 1).to();
    }

    /**
     * Returns a portion of this route between the specified indices.
     *
     * @param fromIndex
     *            the index of the link to start the slice with. It must be
     *            non-negative and less than {@link #length()}.
     * @param toIndex
     *            the index of the first link not to be included in the slice.
     *            It must be greater than {@code from} and less or equal to
     *            {@link #length()}.
     *
     * @return the slice of this route
     */
    Route<V, E> slice(int fromIndex, int toIndex);

    /**
     * Returns a prefix of this route of the specified length.
     *
     * <p>
     * This method is a shortcut to {@code slice(0, length)}, which is the
     * default implementation.
     *
     * @param length
     *            the length of the prefix. It must be greater than zero and
     *            less or equal to {@link #length()}.
     *
     * @return the prefix of this route
     */
    default Route<V, E> prefix(int length) {
        return slice(0, length);
    }

    /**
     * Returns a suffix of this route of the specified length.
     *
     * <p>
     * This method is a shortcut to {@code slice(0, length)}, which is the
     * default implementation.
     *
     * @param length
     *            the length of the suffix. It must be greater than zero and
     *            less or equal to {@link #length()}.
     *
     * @return the suffix of this route
     */
    default Route<V, E> suffix(int length) {
        final var fromIndex = length() - length;

        if ((fromIndex < 0) || (length <= 0)) {
            throw new IllegalArgumentException("Illegal length: " + length);
        }

        return postfix(fromIndex);
    }

    /**
     * Returns a suffix of this route starting at the specified index.
     *
     * <p>
     * This method is a shortcut to {@code slice(fromIndex, length())}, which is
     * the default implementation.
     *
     * @param fromIndex
     *            the index of the link to start the slice with. It must be
     *            non-negative and less than {@link #length()}.
     *
     * @return the suffix of this route
     */
    default Route<V, E> postfix(int fromIndex) {
        return slice(fromIndex, length());
    }

    /**
     * Returns a new route with vertices in the opposite direction.
     *
     * @return the reverse of this route
     */
    Route<V, E> reverse();

    /**
     * Returns a list of links that form this route. The list contains at least
     * one element, because a route must consist of one or more links and it is
     * immutable.
     *
     * @return a list of links that form this route
     */
    List<Link<V, E>> links();

    /**
     * Returns the number of links that form this route.
     *
     * <p>
     * This method is a shortcut for {@code links().size()}, which is the
     * default implementation. It returns always a positive value.
     *
     * @return the number of links that form this route
     */
    default int length() {
        return links().size();
    }

    /**
     * Returns the number of stops that this route contains.
     *
     * <p>
     * This method is a shortcut for {@code length() + 1}, which is the
     * default implementation. It returns always a positive value.
     *
     * @return the number of stops this route contains
     */
    default int stops() {
        return length() + 1;
    }

    /**
     * Creates a new empty builder.
     *
     * @param <V>
     *            the type of vertices
     * @param <E>
     *            the type of edges
     *
     * @return the builder
     */
    static <V, E> Joiner<V, E> empty() {
        return DefaultRoute.empty();
    }

    /**
     * Creates a new builder containing the specified link.
     *
     * @param <V>
     *            the type of vertices
     * @param <E>
     *            the type of edges
     * @param link
     *            the source link. It must not be {@code null}.
     *
     * @return the builder
     */
    static <V, E> Builder<V, E> extend(Link<? extends V, ? extends E> link) {
        return DefaultRoute.extend(link);
    }

    /**
     * Creates a new builder containing the specified route.
     *
     * @param <V>
     *            the type of vertices
     * @param <E>
     *            the type of edges
     * @param route
     *            the source route. It must not be {@code null}.
     *
     * @return the builder
     */
    static <V, E> Builder<V, E> extend(Route<? extends V, ? extends E> route) {
        return DefaultRoute.extend(route);
    }

    /**
     * Creates a new origin for building a route.
     *
     * @param <V>
     *            the type of vertices
     * @param origin
     *            the origin vertex. It must not be {@code null}.
     *
     * @return the origin
     */
    static <V> Origin<V> start(V origin) {
        return DefaultRoute.start(origin);
    }

    /**
     * Represents the origin of a route to be built.
     *
     * @param <V>
     *            the type of vertices
     */
    interface Origin<V> {

        /**
         * Returns the next building stage that can link the origin to the
         * continuation of the route to be built.
         *
         * @param <E>
         *            the type of the edge
         * @param edge
         *            the linking edge. It must not be {@code null}.
         *
         * @return the next building stage
         */
        <E> Linker<V, E> through(E edge);

        /**
         * @return the originating vertex
         */
        V from();
    }

    /**
     * Defines a building stage that can use a linking edge to append the next
     * part of a route to a builder.
     *
     * @param <V>
     *            the type of vertices
     * @param <E>
     *            the type of edges
     */
    interface Linker<V, E> {

        /**
         * @return the originating vertex
         */
        V from();

        /**
         * @return the linking edge
         */
        E edge();

        /**
         * Binds the given vertex through the linking edge.
         *
         * @param next
         *            the vertex to bind. It must not be {@code null}.
         *
         * @return the related builder
         */
        Builder<V, E> to(V next);

        /**
         * Appends the specified link through the linking edge.
         *
         * @param link
         *            the link to incorporate. It must not be {@code null}.
         *
         * @return the related builder
         */
        Builder<V, E> append(Link<? extends V, ? extends E> link);

        /**
         * Appends the specified route through the linking edge.
         *
         * @param route
         *            the route to incorporate. It must not be {@code null}.
         *
         * @return the related builder
         */
        Builder<V, E> append(Route<? extends V, ? extends E> route);
    }

    /**
     * Defines a builder capable of joining links and routes.
     *
     * @param <V>
     *            the type of vertices
     * @param <E>
     *            the type of edges
     */
    interface Joiner<V, E> {

        /**
         * @return the last vertex of the route being built if any present
         */
        Optional<V> tip();

        /**
         * Checks if the given segment can be possibly joined.
         *
         * @param next
         *            the desired next segment. It must not be {@code null}.
         *
         * @return {@code true} if the given segment might be joined
         */
        boolean joinable(Direction<? extends V> next);

        /**
         * Joins the specified link.
         *
         * @param link
         *            the link to join. It must not be {@code null} and if any
         *            {@link #tip()} is present, it must be equal to the
         *            {@link Direction#from()} vertex of the link.
         *
         * @return the next building stage
         */
        Builder<V, E> join(Link<? extends V, ? extends E> link);

        /**
         * Joins the specified route.
         *
         * @param route
         *            the route to join. It must not be {@code null} and if any
         *            {@link #tip()} is present, it must be equal to the
         *            {@link Direction#from()} vertex of the route.
         *
         * @return the next building stage
         */
        Builder<V, E> join(Route<? extends V, ? extends E> route);

        /**
         * @return the finished route if there were any links provided
         */
        Optional<Route<V, E>> finishOptionally();
    }

    /**
     * Defines a non-empty builder.
     *
     * @param <V>
     *            the type of vertices
     * @param <E>
     *            the type of edges
     */
    interface Builder<V, E> extends Joiner<V, E> {

        /**
         * Returns the next building stage holding a linking edge.
         *
         * @param edge
         *            the linking edge. It must not be {@code null}.
         *
         * @return the next building stage using this builder
         */
        Linker<V, E> through(E edge);

        /**
         * Adds a link to the next vertex through the given edge.
         *
         * @param edge
         *            the edge to add. It must not be {@code null}.
         * @param next
         *            the next vertex. It must not be {@code null}.
         *
         * @return this builder
         */
        Builder<V, E> link(E edge, V next);

        /**
         * @return the finished route
         */
        Route<V, E> finish();

        /**
         * Returns the finished route.
         *
         * <p>
         * For this interface, this method never fails and therefore works as
         * {@code Optional.of(finish())}, which is the default implementation.
         *
         * @see Joiner#finishOptionally()
         */
        @Override
        default Optional<Route<V, E>> finishOptionally() {
            return Optional.of(finish());
        }
    }
}
