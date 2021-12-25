package cz.okozel.ristral.backend.entity.linky;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Route<V, E> {

    private final List<Link<V, E>> links;

    public Route(V first, E data, V second) {
        links = List.of(new Link<>(first, data, second));
    }

    private Route(List<Link<V, E>> links) {
        this.links = Collections.unmodifiableList(links);
    }

    public Route<V, E> addToEnd(V vertex, E data) {
        return add(vertexes(), vertex, data);
    }

    public Route<V, E> addToBeginning(V vertex, E data) {
        return add(0, vertex, data);
    }

    public Route<V, E> add(int index, V vertex, E data) {
        if (index < 0 || index > vertexes()) return this;
        List<Link<V, E>> newLinks = new ArrayList<>(links);
        if (index == 0) newLinks.add(0, new Link<>(vertex, data, getFirstVertex()));
        else if (index == vertexes()) newLinks.add(new Link<>(getLastVertex(), data, vertex));
        else  {
            Link<V, E> former = newLinks.get(index - 1);
            newLinks.set(index - 1, new Link<>(former.previous, former.data, vertex));
            newLinks.add(index, new Link<>(vertex, data, former.next));
        }
        return new Route<>(newLinks);
    }

    public Route<V, E> remove(int index) {
        if (index < 0 || index >= vertexes()) return this;
        List<Link<V, E>> newLinks = new ArrayList<>(links);
        if (index == 0) newLinks.remove(0);
        else if (index == links.size()) newLinks.remove(index - 1);
        else {
            Link<V, E> former = newLinks.get(index - 1);
            newLinks.set(index - 1, new Link<>(former.previous, former.data, newLinks.remove(index).next));
        }
        return new Route<>(newLinks);
    }

    public int vertexes() {
        return links.size() + 1;
    }

    public V getFirstVertex() {
        return links.get(0).previous;
    }

    public V getLastVertex() {
        return links.get(links.size() - 1).next;
    }

    public List<Link<V, E>> getLinks() {
        return links;
    }

    public static class Link<V, E> {

        private final V previous;
        private final E data;
        private final V next;

        public Link(V previous, E data, V next) {
            this.previous = previous;
            this.data = data;
            this.next = next;
        }

    }

}
