package cz.okozel.ristral.backend.entity.linky;

interface NamedView<T> {
    T data();
    String name();
    boolean visible();
}
