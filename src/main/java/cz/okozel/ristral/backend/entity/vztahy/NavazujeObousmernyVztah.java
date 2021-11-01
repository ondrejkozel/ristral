package cz.okozel.ristral.backend.entity.vztahy;

/**
 * Třída pro správu bidirectional jpa mapování.
 * @param <T> entita, se kterou je navazován vztah
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public interface NavazujeObousmernyVztah<T extends NavazujeObousmernyVztah> {

    /**
     * Ověří přítomnost spojení a případně ho naváže.
     * @param objekty objekt(y), které mají být přítomny ve spojení
     */
    default void vynutPritomnostSpojeni(T... objekty) {
        for (T objekt : objekty) {
            if (!overSpojeniS(objekt)) navazSpojeniS(objekt);
            if (!objekt.overSpojeniS(this)) objekt.navazSpojeniS(this);
        }
    }

    /**
     * Ověří nepřítomnost spojení a případně ho rozváže.
     * @param objekty objekt(y), které nemají být přítomny ve spojení
     */
    default void vynutNepritomnostSpojeni(T... objekty) {
        for (T objekt : objekty) {
            if (overSpojeniS(objekt)) rozvazSpojeniS(objekt);
            if (objekt.overSpojeniS(this)) objekt.rozvazSpojeniS(this);
        }
    }

    boolean overSpojeniS(T objekt);
    void navazSpojeniS(T objekt);
    void rozvazSpojeniS(T objekt);
}
