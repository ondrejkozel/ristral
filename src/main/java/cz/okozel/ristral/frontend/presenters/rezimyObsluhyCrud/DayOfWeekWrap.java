package cz.okozel.ristral.frontend.presenters.rezimyObsluhyCrud;

import java.time.DayOfWeek;
import java.util.HashMap;

/**
 * český překlad {@link java.time.DayOfWeek}
 */
public enum DayOfWeekWrap {
    PONDELI(DayOfWeek.MONDAY, "pondělí", "po"),
    UTERY(DayOfWeek.TUESDAY, "úterý", "út"),
    STREDA(DayOfWeek.WEDNESDAY, "středa", "st"),
    CTVRTEK(DayOfWeek.THURSDAY, "čtvrtek", "čt"),
    PATEK(DayOfWeek.FRIDAY, "pátek", "pá"),
    SOBOTA(DayOfWeek.SATURDAY, "sobota", "so"),
    NEDELE(DayOfWeek.SUNDAY, "neděle", "ne");

    private final DayOfWeek dayOfWeek;
    private final String longText;
    private final String shortText;

    DayOfWeekWrap(DayOfWeek dayOfWeek, String longText, String shortText) {
        this.dayOfWeek = dayOfWeek;
        this.longText = longText;
        this.shortText = shortText;
    }

    private static final HashMap<DayOfWeek, DayOfWeekWrap> DAYS_OF_WEEK = new HashMap<>();

    static {
        for (DayOfWeekWrap day : values()) {
            DAYS_OF_WEEK.put(day.dayOfWeek, day);
        }
    }

    public static String getFullTranslation(DayOfWeek day) {
        return DAYS_OF_WEEK.get(day).longText;
    }

    public static String getShortTranslation(DayOfWeek day) {
        return DAYS_OF_WEEK.get(day).shortText;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    @Override
    public String toString() {
        return longText;
    }

}
