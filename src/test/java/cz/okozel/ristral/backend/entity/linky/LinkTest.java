package cz.okozel.ristral.backend.entity.linky;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LinkTest {

    @Test
    void construction() {
        final var link = Link.build("A", "B", 1);

        assertEquals("A", link.from());
        assertEquals("B", link.to());
        assertEquals(1, link.edge());
    }

    @Test
    void equality() {
        final var a = Link.build("A", "B", 1024);
        final var b = Link.build("A", "B", 1024);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void hashing() {
        assertEquals(Objects.hash("A", "B", 1), Link.build("A", "B", 1).hashCode());
    }
}
