package cz.okozel.ristral.backend.entity.linky;

import cz.okozel.ristral.backend.entity.routes.Link;
import cz.okozel.ristral.backend.entity.routes.Route;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class RouteTest {

    @Test
    void empty_finish() {
        assertFalse(Route.empty().finishOptionally().isPresent());
    }

    @Test
    void empty_tip() {
        assertFalse(Route.empty().tip().isPresent());
    }

    @Test
    void single_link() {
        final var route = Route.extend(Link.build("A", "B", 1)).finish();

        assertEquals(1, route.length());
        assertEquals(2, route.stops());
        assertEquals(List.of(Link.build("A", "B", 1)), route.links());
        assertEquals("A", route.from());
        assertEquals("B", route.to());
        assertEquals(route.links().hashCode(), route.hashCode());

        final var copy = copyOf(route);
        assertEquals(copy.hashCode(), route.hashCode());
        assertEquals(copy, route);
    }

    @Test
    void multiple_links() {
        final var route = Route
            .start("A")
            .through(1)
            .to("B")
            .through(2)
            .to("C")
            .finish();

        assertEquals(2, route.length());
        assertEquals(3, route.stops());
        assertEquals(List.of(Link.build("A", "B", 1), Link.build("B", "C", 2)), route.links());
        assertEquals("A", route.from());
        assertEquals("C", route.to());
        assertEquals(route.links().hashCode(), route.hashCode());

        final var copy = copyOf(route);
        assertEquals(copy.hashCode(), route.hashCode());
        assertEquals(copy, route);
    }

    @Test
    void prefix() {
        final var prefix = Route.start("A").through(1).to("B").finish();
        final var suffix = Route.start("B").through(2).to("C").finish();
        final var route = Route.extend(prefix).join(suffix).finish();

        assertEquals(prefix, route.prefix(prefix.length()));
        assertEquals(route, route.prefix(route.length()));
    }

    @Test
    void suffix() {
        final var prefix = Route.start("A").through(1).to("B").finish();
        final var suffix = Route.start("B").through(2).to("C").finish();
        final var route = Route.extend(prefix).join(suffix).finish();

        assertEquals(suffix, route.suffix(suffix.length()));
        assertEquals(route, route.suffix(route.length()));
    }

    @Test
    void postfix() {
        final var prefix = Route.start("A").through(1).to("B").finish();
        final var suffix = Route.start("B").through(2).to("C").finish();
        final var route = Route.extend(prefix).join(suffix).finish();

        assertEquals(suffix, route.postfix(route.length() - suffix.length()));
        assertEquals(route, route.postfix(0));
    }

    @Test
    void slice() {
        final var prefix = Route.start("A").through(1).to("B").finish();
        final var suffix = Route.start("B").through(2).to("C").finish();
        final var route = Route.extend(prefix).join(suffix).finish();

        assertEquals(route, route.slice(0, route.length()));
        assertEquals(prefix, route.slice(0, prefix.length()));
        assertEquals(suffix, route.slice(prefix.length(), route.length()));
    }

    @Test
    void reverse() {
        final var original = Route
                .start("A")
                .through(1)
                .to("B")
                .through(2)
                .to("C")
                .through(3)
                .to("D")
                .finish();
        final var reversed = original.reverse();

        assertEquals(reversed.links(), List.of(Link.build("D", "C", 3), Link.build("C", "B", 2), Link.build("B", "A", 1)));
    }

    private static <V, E> Route<V, E> copyOf(Route<? extends V, ? extends E> route) {
        // How to compose a Route from a stream
        final var result = Route.<V, E> empty();
        route.links().forEach(result::join);
        return result.finishOptionally().orElseThrow();
    }
}
