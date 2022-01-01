package cz.okozel.ristral.backend.entity.linky;

/**
 * Represents a direction between the origin and target.
 *
 * @param <V>
 *            the type of the origin and target representation
 */
public interface Direction<V> {

    /**
     * @return the origin
     */
    V from();

    /**
     * @return the target
     */
    V to();
}
