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
@Table(name = "rezimy_obsluhy")
public class RezimObsluhy extends AbstractSchemaEntity {

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
    private Set<Znameni> znameni;

    public RezimObsluhy() {}

    public RezimObsluhy(String nazev, String popis, Schema schema) {
        super(schema);
        this.nazev = nazev;
        this.popis = popis;
        this.zastavky = new HashSet<>();
        this.znameni = new HashSet<>();
    }

    public String getNazev() {
        return nazev;
    }

    public String getPopis() {
        return popis;
    }

    public boolean addZnameni(Znameni znameni) {
        return this.znameni.add(znameni);
    }

    public boolean removeZnameni(Znameni znameni) {
        return this.znameni.remove(znameni);
    }

    public void clearZnameni() {
        znameni.clear();
    }

    @Entity
    @Table(name = "znameni")
    public static class Znameni extends AbstractSchemaEntity {

        @ManyToOne(cascade = CascadeType.ALL)
        @JoinColumn
        private RezimObsluhy rezimObsluhy;

        private LocalTime naZnameniOd;

        private LocalTime naZnameniDo;

        @ElementCollection(fetch = FetchType.EAGER)
        @Enumerated(EnumType.STRING)
        @CollectionTable(name = "dny_na_znameni",
                joinColumns = @JoinColumn(name = "znameni_id"))
        @Column(name = "dny_na_znameni")
        private Set<DayOfWeek> naZnameniDny;

        public Znameni() {}

        public Znameni(LocalTime naZnameniOd, LocalTime naZnameniDo, Set<DayOfWeek> naZnameniDny, Schema schema) {
            super(schema);
            this.naZnameniOd = naZnameniOd;
            this.naZnameniDo = naZnameniDo;
            this.naZnameniDny = naZnameniDny;
        }

        public LocalTime getNaZnameniOd() {
            return naZnameniOd;
        }

        public LocalTime getNaZnameniDo() {
            return naZnameniDo;
        }

        public Set<DayOfWeek> getNaZnameniDny() {
            return naZnameniDny;
        }

    }

}
