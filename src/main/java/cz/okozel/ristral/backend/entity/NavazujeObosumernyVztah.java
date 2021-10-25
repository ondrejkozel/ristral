package cz.okozel.ristral.backend.entity;

/**
 * Třída pro správu bidirectional jpa mapování.
 * @param <T> entita, se kterou je navazován vztah
 */
public interface NavazujeObosumernyVztah<T extends AbstractEntity> {
    // TODO: 22.10.2021 implementovat všem třídám s obousměrným vztahem toto rozhraní jako u Uzivatel a Aktivita
    /**
     * Ověří přítomnost spojení a případně ho naváže.
     * @param objekt objekt, který má být přítomný ve spojení
     * @return pokud entita měla navázané spojení s objektem true, jinak naváže spojení a vrátí false
     */
    default boolean vynutPritomnostSpojeni(T objekt) {
        if (overSpojeni(objekt)) return true;
        navazSpojeni(objekt);
        return false;
    }
    /**
     * Ověří nepřítomnost spojení a případně ho rozváže.
     * @param objekt objekt, který nemá být přítomný ve spojení
     * @return pokud entita neměla navázané spojení s objektem true, jinak rozváže spojení a vrátí false
     */
    default boolean vynutNepritomnostSpojeni(T objekt) {
        if (!overSpojeni(objekt)) return true;
        rozvazSpojeni(objekt);
        return false;
    }
    boolean overSpojeni(T objekt);
    void navazSpojeni(T objekt);
    void rozvazSpojeni(T objekt);
}
