package cz.okozel.ristral.backend.entity.linky;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class RouteTest {

    private static Route<String, Integer> generateShortStringIntegerRoute() {
        return new Route<>("one", 0, "two");
    }

    private static Route<String, Integer> generateLongStringIntegerRoute() {
        return new Route<>("one", 0, "two").addToEnd("three", 0).addToEnd("four", 0).addToEnd("five", 0);
    }

    private static <V, E> String linksToString(List<Route.Link<V, E>> links) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Iterator<Route.Link<V, E>> iterator = links.iterator(); iterator.hasNext(); ) {
            Route.Link<V, E> link = iterator.next();
            stringBuilder.append("[").append(link.previous).append("] - ").append(link.data).append(" - ");
            if (!iterator.hasNext()) stringBuilder.append("[").append(link.next).append("]");
        }
        return stringBuilder.toString();
    }

    @Test
    void routeCreationTest() {
        assertThat(linksToString(generateShortStringIntegerRoute().getLinks()), equalTo("[one] - 0 - [two]"));
    }

    @Test
    void addToEndTest() {
        Route<String, Integer> route = generateShortStringIntegerRoute();
        route = route.addToEnd("three", 1);
        assertThat(linksToString(route.getLinks()), equalTo("[one] - 0 - [two] - 1 - [three]"));
    }

    @Test
    void addToBegginingTest() {
        Route<String, Integer> route = generateShortStringIntegerRoute();
        route = route.addToBeginning("zero", -1);
        assertThat(linksToString(route.getLinks()), equalTo("[zero] - -1 - [one] - 0 - [two]"));
    }

    @Test
    void addMultipleTest() {
        Route<String, Integer> route = generateShortStringIntegerRoute();
        route = route.addToEnd("three", 1);
        route = route.add(2, "two and half", 2);
        assertThat(linksToString(route.getLinks()), equalTo("[one] - 0 - [two] - 1 - [two and half] - 2 - [three]"));
    }

    @Test
    void removeFirstTest() {
        Route<String, Integer> route = generateLongStringIntegerRoute();
        route = route.remove(0);
        assertThat(linksToString(route.getLinks()), equalTo("[two] - 0 - [three] - 0 - [four] - 0 - [five]"));
    }

    @Test
    void removeLastTest() {
        Route<String, Integer> route = generateLongStringIntegerRoute();
        route = route.remove(route.getLinks().size());
        assertThat(linksToString(route.getLinks()), equalTo("[one] - 0 - [two] - 0 - [three] - 0 - [four]"));
    }

    @Test
    void removeTest() {
        Route<String, Integer> route = generateLongStringIntegerRoute();
        route = route.remove(2);
        assertThat(linksToString(route.getLinks()), equalTo("[one] - 0 - [two] - 0 - [four] - 0 - [five]"));
    }

}