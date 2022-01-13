package cz.okozel.ristral.backend.entity.linky;

final class NamedRoute<V, E> implements NamedView<Route<V, E>> {

    private final Route<V, E> route;
    private final String name;
    private final boolean visible;

    public static <V, E> NamedRoute<V, E> build(Route<V, E> route, String name) {
        return new NamedRoute<>(route, name, false);
    }

    private NamedRoute(Route<V, E> route, String name, boolean visible) {
        this.route = route;
        this.name = name;
        this.visible = visible;
    }

    @Override
    public Route<V, E> data() {
        return route;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean visible() {
        return visible;
    }

    public NamedRoute<V, E> withData(Route<V, E> route) {
        return new NamedRoute<>(route, name, visible);
    }

    public NamedRoute<V, E> withName(String name) {
        return new NamedRoute<>(route, name, visible);
    }

    public NamedRoute<V, E> withVisibility(boolean visible) {
        return new NamedRoute<>(route, name, visible);
    }
}
