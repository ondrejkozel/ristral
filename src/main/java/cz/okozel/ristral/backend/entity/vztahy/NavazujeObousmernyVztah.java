package cz.okozel.ristral.backend.entity.vztahy;

/**
 * Třída pro správu bidirectional jpa mapování.
 * @param <T> entita, se kterou je navazován vztah
 */
public interface NavazujeObousmernyVztah<T extends NavazujeObousmernyVztah> {

    // TODO: 22.10.2021 implementovat všem třídám s obousměrným vztahem toto rozhraní jako u Uzivatel a Aktivita
    /**
     * Ověří přítomnost spojení a případně ho naváže.
     * @param objekt objekt, který má být přítomný ve spojení
     */
    default void vynutPritomnostSpojeni(T objekt) {
        if (!overSpojeniS(objekt)) navazSpojeniS(objekt);
        if (!objekt.overSpojeniS(this)) objekt.navazSpojeniS(this);
    }

    /**
     * Ověří nepřítomnost spojení a případně ho rozváže.
     * @param objekt objekt, který nemá být přítomný ve spojení
     */
    default void vynutNepritomnostSpojeni(T objekt) {
        if (overSpojeniS(objekt)) rozvazSpojeniS(objekt);
        if (objekt.overSpojeniS(this)) objekt.rozvazSpojeniS(this);
    }

    boolean overSpojeniS(T objekt);
    void navazSpojeniS(T objekt);
    void rozvazSpojeniS(T objekt);
}
