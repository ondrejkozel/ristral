package cz.okozel.ristral.backend.entity.zastavky;

import cz.okozel.ristral.backend.entity.AbstractSchemaEntity;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.vztahy.EntitaNeobsahujeTentoVztahException;
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
@SuppressWarnings("rawtypes")
public class RezimObsluhy extends AbstractSchemaEntity implements NavazujeObousmernyVztah {

    public static RezimObsluhy vytvorRezimBezZnameni(Schema schema) {
        return new RezimObsluhy("není na znamení", "Zastávka není na znamení všechny dny v týdnu.", schema);
    }

    @Size(max = 50)
    @NotBlank
    private String nazev;

    @Size(max = 250)
    @NotNull
    private String popis;

    @OneToMany(mappedBy = "rezimObsluhy")
    private Set<Zastavka> zastavky;

    @OneToMany(mappedBy = "rezimObsluhy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<PeriodaNaZnameni> periodyNaZnameni;

    public RezimObsluhy() {}

    public RezimObsluhy(String nazev, String popis, Schema schema) {
        super(schema);
        this.nazev = nazev;
        this.popis = popis;
        this.zastavky = new HashSet<>();
        this.periodyNaZnameni = new HashSet<>();
    }

    public String getNazev() {
        return nazev;
    }

    public String getPopis() {
        return popis;
    }

    @SuppressWarnings("unchecked")
    public void addZnameni(PeriodaNaZnameni periodaNaZnameni) {
        vynutPritomnostSpojeni(periodaNaZnameni);
    }

    /**
     * funkce vytvoří garbage
     */
    @SuppressWarnings("unchecked")
    public void removeZnameni(PeriodaNaZnameni periodaNaZnameni) {
        vynutNepritomnostSpojeni(periodaNaZnameni);
    }

    public void clearZnameni() {
        periodyNaZnameni.clear();
    }

    @Override
    public boolean overSpojeniS(NavazujeObousmernyVztah objekt) {
        if (objekt instanceof Zastavka) return zastavky.contains(objekt);
        if (objekt instanceof PeriodaNaZnameni) return periodyNaZnameni.contains(objekt);
        throw new EntitaNeobsahujeTentoVztahException(objekt + " není Zastavka nebo PeriodaNaZnameni");
    }

    @Override
    public void navazSpojeniS(NavazujeObousmernyVztah objekt) {
        if (objekt instanceof Zastavka) zastavky.add((Zastavka) objekt);
        else if (objekt instanceof PeriodaNaZnameni) periodyNaZnameni.add((PeriodaNaZnameni) objekt);
        else throw new EntitaNeobsahujeTentoVztahException(objekt + " není Zastavka nebo PeriodaNaZnameni");
    }

    @Override
    public void rozvazSpojeniS(NavazujeObousmernyVztah objekt) {
        if (objekt instanceof Zastavka) zastavky.remove(objekt);
        else if (objekt instanceof PeriodaNaZnameni) periodyNaZnameni.remove(objekt);
        else throw new EntitaNeobsahujeTentoVztahException(objekt + " není Zastavka nebo PeriodaNaZnameni");
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
