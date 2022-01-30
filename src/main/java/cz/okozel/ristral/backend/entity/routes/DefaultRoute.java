package cz.okozel.ristral.backend.entity.routes;

import java.util.*;

/**
 * The default implementation of {@link Route}.
 *
 * @param <V>
 *            the type of vertices
 * @param <E>
 *            the type of edges
 */
final class DefaultRoute<V, E> implements Route<V, E> {

    private final List<Link<V, E>> links;

    /**
     * Creates a new instance.
     *
     * @param links
     *            the list of links. It must be a consistent list of links.
     */
    @SuppressWarnings("unchecked") // The cast is valid due to strict covariance
    private DefaultRoute(List<? extends Link<? extends V, ? extends E>> links) {
        this.links = List.copyOf((List<Link<V, E>>) links);
    }

    /**
     * Creates a new instance.
     *
     * @param builder
     *            the builder. It must not be {@code null}.
     */
    private DefaultRoute(ValidBuilder<? extends V, ? extends E> builder) {
        this(builder.links);
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
    public static <V, E> Joiner<V, E> empty() {
        return new EmptyBuilder<>();
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
    public static <V, E> Builder<V, E> extend(Link<? extends V, ? extends E> link) {
        return new ValidBuilder<>(link);
    }

    /**
     * Creates a builder filled with all links of the specified route.
     *
     * @param <V>
     *            the type of vertices
     * @param <E>
     *            the type of edges
     * @param route
     *            the route to fill the builder with. It must not be
     *            {@code null}.
     *
     * @return the builder
     */
    public static <V, E> Builder<V, E> extend(Route<? extends V, ? extends E> route) {
        return new ValidBuilder<>(route);
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
    public static <V> Origin<V> start(V origin) {
        return new OriginBuilder<>(origin);
    }

    /**
     * @see Route#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Route<?, ?>) {
            final var other = (Route<?, ?>) obj;
            return links.equals(other.links());
        }
        return false;
    }

    /**
     * @see Route#hashCode()
     */
    @Override
    public int hashCode() {
        return links.hashCode();
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "DefaultRoute[links=" + links + ']';
    }

    /**
     * @see Route#slice(int, int)
     */
    @Override
    public Route<V, E> slice(int fromIndex, int toIndex) {
        if (fromIndex == toIndex) {
            throw new IllegalArgumentException("Empty slice: [" + fromIndex + ", " + toIndex + ")");
        }

        if ((fromIndex == 0) && (toIndex == length())) {
            return this;
        }

        return new DefaultRoute<>(links.subList(fromIndex, toIndex));
    }

    /**
     * @see Route#reverse()
     */
    @Override
    public Route<V, E> reverse() {
        final var length = links.size();
        final List<Link<V, E>> reversed = new ArrayList<>(length);
        for (var iterator = links.listIterator(length); iterator.hasPrevious();) {
            reversed.add(iterator.previous().reverse());
        }
        assert (length == reversed.size());
        return new DefaultRoute<>(reversed);
    }

    /**
     * @see Route#links()
     */
    @Override
    public List<Link<V, E>> links() {
        return links;
    }

    private static final class EmptyBuilder<V, E> implements Joiner<V, E> {

        private Builder<V, E> builder;

        /**
         * Creates a new instance.
         */
        public EmptyBuilder() {
            // Default constructor
        }

        /**
         * @see Object#toString()
         */
        @Override
        public String toString() {
            return (builder == null) ? "DefaultRoute.Builder[links=[]]" : builder.toString();
        }

        /**
         * @see Route.Joiner#tip()
         */
        @Override
        public Optional<V> tip() {
            return (builder == null) ? Optional.empty() : builder.tip();
        }

        /**
         * @see Route.Joiner#joinable(Direction)
         */
        @Override
        public boolean joinable(Direction<? extends V> next) {
            return ((builder == null) || builder.joinable(next));
        }

        /**
         * @see Route.Joiner#join(Link)
         */
        public Builder<V, E> join(Link<? extends V, ? extends E> link) {
            if (builder == null) {
                builder = new ValidBuilder<>(link);
            } else {
                builder.join(link);
            }

            return builder;
        }

        /**
         * @see Route.Joiner#join(Route)
         */
        public Builder<V, E> join(Route<? extends V, ? extends E> route) {
            if (builder == null) {
                builder = new ValidBuilder<>(route);
            } else {
                builder.join(route);
            }

            return builder;
        }

        /**
         * @see Route.Joiner#finishOptionally()
         */
        public Optional<Route<V, E>> finishOptionally() {
            return (builder == null) ? Optional.empty() : builder.finishOptionally();
        }
    }

    private static final class ValidBuilder<V, E> implements Builder<V, E>, Direction<V> {

        private final List<Link<? extends V, ? extends E>> links;

        /**
         * Creates a new instance.
         *
         * @param route
         *            the route to extend. It must not be {@code null}.
         */
        public ValidBuilder(Route<? extends V, ? extends E> route) {
            this.links = new ArrayList<>(route.links());
        }

        /**
         * Creates a new instance.
         *
         * @param link
         *            the link to extend. It must not be {@code null}.
         */
        public ValidBuilder(Link<? extends V, ? extends E> link) {
            Objects.requireNonNull(link);
            this.links = new ArrayList<>();
            this.links.add(link);
        }

        /**
         * @see Object#toString()
         */
        @Override
        public String toString() {
            return "DefaultRoute.Builder[links=" + links + ']';
        }

        /**
         * @see Direction#from()
         */
        @Override
        public V from() {
            return links.get(0).from();
        }

        /**
         * @see Direction#to()
         */
        @Override
        public V to() {
            return links.get(links.size() - 1).to();
        }

        /**
         * @see Route.Joiner#tip()
         */
        public Optional<V> tip() {
            return Optional.of(to());
        }

        /**
         * @see Route.Joiner#joinable(Direction)
         */
        @Override
        public boolean joinable(Direction<? extends V> next) {
            return to().equals(next.from());
        }

        /**
         * @see Route.Joiner#join(Link)
         */
        @Override
        public Builder<V, E> join(Link<? extends V, ? extends E> link) {
            if (joinable(link)) {
                links.add(link);
                return this;
            }

            throw new IllegalArgumentException("Could not join the link.");
        }

        /**
         * @see Route.Joiner#join(Route)
         */
        @Override
        public Builder<V, E> join(Route<? extends V, ? extends E> route) {
            if (joinable(route)) {
                links.addAll(route.links());
                return this;
            }

            throw new IllegalArgumentException("Could not join the route.");
        }

        /**
         * @see Route.Builder#through(Object)
         */
        @Override
        public Linker<V, E> through(E edge) {
            return new BuilderLinker<>(this, edge);
        }

        /**
         * @see Route.Builder#link(Object,
         *      Object)
         */
        @Override
        public Builder<V, E> link(E edge, V next) {
            links.add(Link.build(to(), Objects.requireNonNull(next), Objects.requireNonNull(edge)));
            return this;
        }

        /**
         * @see Route.Builder#finish()
         */
        @Override
        public Route<V, E> finish() {
            return new DefaultRoute<>(this);
        }
    }

    private static final class BuilderLinker<V, E> implements Linker<V, E> {

        private final ValidBuilder<V, E> builder;
        private final E edge;

        /**
         * Creates a new instance.
         *
         * @param builder
         *            the builder. It must not be {@code null}.
         * @param edge
         *            the linking edge. It must not be {@code null}.
         */
        public BuilderLinker(ValidBuilder<V, E> builder, E edge) {
            this.builder = Objects.requireNonNull(builder);
            this.edge = Objects.requireNonNull(edge);
        }

        /**
         * @see Object#toString()
         */
        @Override
        public String toString() {
            return "DefaultRoute.Linker[builder=" + builder + ", edge=" + edge + ']';
        }

        /**
         * @see Route.Linker#from()
         */
        @Override
        public V from() {
            return builder.to();
        }

        /**
         * @see Route.Linker#edge()
         */
        @Override
        public E edge() {
            return edge;
        }

        /**
         * @see Route.Linker#to(Object)
         */
        @Override
        public Builder<V, E> to(V next) {
            return builder.link(edge, next);
        }

        /**
         * @see Route.Linker#append(Link)
         */
        @Override
        public Builder<V, E> append(Link<? extends V, ? extends E> link) {
            return builder.link(edge, link.from()).link(link.edge(), link.to());
        }

        /**
         * @see Route.Linker#append(Route)
         */
        @Override
        public Builder<V, E> append(Route<? extends V, ? extends E> route) {
            return builder.link(edge, route.from()).join(route);
        }
    }

    private static final class OriginLinker<V, E> implements Linker<V, E> {

        private final V from;
        private final E edge;

        /**
         * Creates a new instance.
         *
         * @param from
         *            the origin. It must not be {@code null}.
         * @param edge
         *            the edge. It must not be {@code null}.
         */
        public OriginLinker(V from, E edge) {
            this.from = Objects.requireNonNull(from);
            this.edge = Objects.requireNonNull(edge);
        }

        /**
         * @see Object#toString()
         */
        @Override
        public String toString() {
            return "DefaultRoute.Linker[from=" + from + ", edge=" + edge + ']';
        }

        /**
         * @see Route.Linker#from()
         */
        @Override
        public V from() {
            return from;
        }

        /**
         * @see Route.Linker#edge()
         */
        @Override
        public E edge() {
            return edge;
        }

        /**
         * @see Route.Linker#to(Object)
         */
        @Override
        public Builder<V, E> to(V next) {
            return extend(Link.build(from, Objects.requireNonNull(next), edge));
        }

        /**
         * @see Route.Linker#append(Link)
         */
        @Override
        public Builder<V, E> append(Link<? extends V, ? extends E> link) {
            return to(link.from()).join(link);
        }

        /**
         * @see Route.Linker#append(Route)
         */
        @Override
        public Builder<V, E> append(Route<? extends V, ? extends E> route) {
            return to(route.from()).join(route);
        }
    }

    private static final class OriginBuilder<V> implements Origin<V> {

        private final V from;

        /**
         * Creates a new instance.
         *
         * @param from
         *            the origin. It must not be {@code null}.
         */
        public OriginBuilder(V from) {
            this.from = Objects.requireNonNull(from);
        }

        /**
         * @see Object#toString()
         */
        @Override
        public String toString() {
            return "DefaultRoute.Origin[from=" + from + ']';
        }

        /**
         * @see Route.Origin#from()
         */
        @Override
        public V from() {
            return from;
        }

        /**
         * @see Route.Origin#through(Object)
         */
        @Override
        public <E> Linker<V, E> through(E edge) {
            return new OriginLinker<>(from, edge);
        }
    }
}
