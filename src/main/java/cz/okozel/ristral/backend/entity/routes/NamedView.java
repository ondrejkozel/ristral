package cz.okozel.ristral.backend.entity.routes;

public class NamedView<T> {

    private final T data;
    private final String name;
    private final boolean visible;

    public NamedView(T data, String name, boolean visible) {
        this.data = data;
        this.name = name;
        this.visible = visible;
    }

    public T getData() {
        return data;
    }

    public String getName() {
        return name;
    }

    public boolean isVisible() {
        return visible;
    }

    public NamedView<T> withData(T data) {
        return new NamedView<>(data, name, visible);
    }

    public NamedView<T> withName(String name) {
        return new NamedView<>(data, name, visible);
    }

    public NamedView<T> withVisible(boolean visible) {
        return new NamedView<>(data, name, visible);
    }

    @Override
    public String toString() {
        return "NamedView{" +
                "data=" + data +
                ", name='" + name + '\'' +
                ", visible=" + visible +
                '}';
    }
}
