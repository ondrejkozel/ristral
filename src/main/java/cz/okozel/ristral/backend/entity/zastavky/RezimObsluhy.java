package cz.okozel.ristral.backend.entity.zastavky;

import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.vztahy.NavazujeObousmernyVztah;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "rezimy_obsluhy")
public class RezimObsluhy extends AbstractSchemaEntity implements NavazujeObousmernyVztah<RezimObsluhy.PeriodaNaZnameni> {

    public static RezimObsluhy vytvorVychoziRezimBezZnameni(Schema schema) {
        RezimObsluhy rezimObsluhy = new RezimObsluhy("není na znamení", "Zastávka není na znamení všechny dny v týdnu.", schema);
        rezimObsluhy.smazatelny = false;
        return rezimObsluhy;
    }

    @Size(max = 50)
    @NotBlank
    private String nazev;

    @Size(max = 250)
    @NotNull
    private String popis;

    @OneToMany(mappedBy = "rezimObsluhy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PeriodaNaZnameni> periodyNaZnameni;

    private boolean smazatelny = true;

    public RezimObsluhy() {}

    public RezimObsluhy(String nazev, String popis, Schema schema) {
        super(schema);
        this.nazev = nazev;
        this.popis = popis;
        this.periodyNaZnameni = new HashSet<>();
    }

    public String getNazev() {
        return nazev;
    }

    public String getPopis() {
        return popis;
    }

    public void addZnameni(PeriodaNaZnameni periodaNaZnameni) {
        vynutPritomnostSpojeni(periodaNaZnameni);
    }

    /**
     * funkce vytvoří garbage
     */
    public void odstranPerioduNaZnameni(PeriodaNaZnameni periodaNaZnameni) {
        vynutNepritomnostSpojeni(periodaNaZnameni);
    }

    public void clearPeriodyNaZnameni() {
        periodyNaZnameni.clear();
    }

    public boolean isSmazatelny() {
        return smazatelny;
    }

    @Override
    public boolean overSpojeniS(PeriodaNaZnameni objekt) {
        return periodyNaZnameni.contains(objekt);
    }

    @Override
    public void navazSpojeniS(PeriodaNaZnameni objekt) {
        periodyNaZnameni.add(objekt);
    }

    @Override
    public void rozvazSpojeniS(PeriodaNaZnameni objekt) {
        periodyNaZnameni.remove(objekt);
    }

    @Override
    public String toString() {
        return nazev;
    }

    @Entity
    @Table(name = "periody_na_znameni")
    public static class PeriodaNaZnameni extends AbstractSchemaEntity implements NavazujeObousmernyVztah<RezimObsluhy> {

        @ManyToOne
        @JoinColumn
        private RezimObsluhy rezimObsluhy;

        private LocalTime naZnameniOd;

        private LocalTime naZnameniDo;

        @ElementCollection(fetch = FetchType.EAGER)
        @Enumerated(EnumType.STRING)
        @CollectionTable(name = "dny_na_znameni",
                joinColumns = @JoinColumn(name = "perioda_na_znameni_id"))
        @Column(name = "dny_na_znameni")
        private Set<DayOfWeek> dnyNaZnameni;

        public PeriodaNaZnameni() {}

        public PeriodaNaZnameni(LocalTime naZnameniOd, LocalTime naZnameniDo, Set<DayOfWeek> dnyNaZnameni, Schema schema) {
            super(schema);
            this.naZnameniOd = naZnameniOd;
            this.naZnameniDo = naZnameniDo;
            this.dnyNaZnameni = dnyNaZnameni;
        }

        public LocalTime getNaZnameniOd() {
            return naZnameniOd;
        }

        public LocalTime getNaZnameniDo() {
            return naZnameniDo;
        }

        public Set<DayOfWeek> getDnyNaZnameni() {
            return dnyNaZnameni;
        }

        @Override
        public boolean overSpojeniS(RezimObsluhy objekt) {
            return rezimObsluhy != null && rezimObsluhy.equals(objekt);
        }

        @Override
        public void navazSpojeniS(RezimObsluhy objekt) {
            rezimObsluhy = objekt;
        }

        @Override
        public void rozvazSpojeniS(RezimObsluhy objekt) {
            rezimObsluhy = null;
        }

    }

}
