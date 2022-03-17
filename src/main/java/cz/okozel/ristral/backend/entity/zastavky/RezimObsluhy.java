package cz.okozel.ristral.backend.entity.zastavky;

import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.schema.Schema;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "service_modes")
public class RezimObsluhy extends AbstractSchemaEntity {

    public static RezimObsluhy vytvorVychoziRezimBezZnameni(Schema schema) {
        RezimObsluhy rezimObsluhy = new RezimObsluhy("není na znamení", "Zastávka není na znamení po celou dobu.", schema);
        rezimObsluhy.upravitelny = false;
        return rezimObsluhy;
    }

    @Size(max = 50)
    @NotBlank
    private String nazev;

    @Size(max = 250)
    @NotNull
    private String popis;

    private boolean upravitelny = true;

    public RezimObsluhy() {}

    public RezimObsluhy(String nazev, String popis, Schema schema) {
        super(schema);
        this.nazev = nazev;
        this.popis = popis;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public boolean isUpravitelny() {
        return upravitelny;
    }

    @Override
    public String toString() {
        return nazev;
    }

    @Entity(name = "OnRequestPeriod")
    @Table(name = "on_request_periods")
    public static class PeriodaNaZnameni extends AbstractSchemaEntity {

        public static PeriodaNaZnameni vytvorVychoziPerioduNaZnameni(Schema schema, RezimObsluhy rezimObsluhy) {
            return new PeriodaNaZnameni(LocalTime.MIDNIGHT, LocalTime.NOON, new HashSet<>(), schema, rezimObsluhy);
        }

        @ManyToOne
        @JoinColumn
        private RezimObsluhy rezimObsluhy;

        private LocalTime naZnameniOd;

        private LocalTime naZnameniDo;

        @ElementCollection(fetch = FetchType.EAGER)
        @Enumerated(EnumType.STRING)
        @CollectionTable(name = "days_on_request",
                joinColumns = @JoinColumn(name = "on_request_period_id"))
        @Column(name = "day_on_request")
        private Set<DayOfWeek> dnyNaZnameni;

        public PeriodaNaZnameni() {}

        public PeriodaNaZnameni(LocalTime naZnameniOd, LocalTime naZnameniDo, Set<DayOfWeek> dnyNaZnameni, Schema schema, RezimObsluhy rezimObsluhy) {
            super(schema);
            this.naZnameniOd = naZnameniOd;
            this.naZnameniDo = naZnameniDo;
            this.dnyNaZnameni = dnyNaZnameni;
            this.rezimObsluhy = rezimObsluhy;
        }

        public RezimObsluhy getRezimObsluhy() {
            return rezimObsluhy;
        }

        public void setRezimObsluhy(RezimObsluhy rezimObsluhy) {
            this.rezimObsluhy = rezimObsluhy;
        }

        public LocalTime getNaZnameniOd() {
            return naZnameniOd;
        }

        public void setNaZnameniOd(LocalTime naZnameniOd) {
            this.naZnameniOd = naZnameniOd;
        }

        public LocalTime getNaZnameniDo() {
            return naZnameniDo;
        }

        public void setNaZnameniDo(LocalTime naZnameniDo) {
            this.naZnameniDo = naZnameniDo;
        }

        public Set<DayOfWeek> getDnyNaZnameni() {
            return dnyNaZnameni;
        }

        public void setDnyNaZnameni(Set<DayOfWeek> dnyNaZnameni) {
            this.dnyNaZnameni = dnyNaZnameni;
        }

        @Override
        public String toString() {
            return "PeriodaNaZnameni{" +
                    "rezimObsluhy=" + rezimObsluhy +
                    ", naZnameniOd=" + naZnameniOd +
                    ", naZnameniDo=" + naZnameniDo +
                    ", dnyNaZnameni=" + dnyNaZnameni +
                    '}';
        }
    }

}
