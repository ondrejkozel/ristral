package cz.okozel.ristral.backend.entity.linkyJizdy.graf;


import java.util.Iterator;

public class Graf<O, H> implements Iterable<Vrchol<O, H>> {

    private Vrchol<O, H> vychozi;

    private H vychoziHodnotaHrany;

    public Graf() {
    }

    public Graf(H vychoziHodnotaHrany) {
        this.vychoziHodnotaHrany = vychoziHodnotaHrany;
    }

    public Vrchol<O, H> getPrvni() {
        return vychozi;
    }

    public void vlozNaZacatek(Vrchol<O, H> vkladanyVrchol) {
        vlozNaZacatek(vychoziHodnotaHrany, vkladanyVrchol);
    }

    public void vlozNaZacatek(H hodnotaHrany, Vrchol<O, H> vkladanyVrchol) {
        vloz(hodnotaHrany, vkladanyVrchol, null);
    }

    public void vlozNaKonec(Vrchol<O, H> vkladanyVrchol) {
        vlozNaKonec(vychoziHodnotaHrany,vkladanyVrchol);
    }

    public void vlozNaKonec(H hodnotaHrany, Vrchol<O, H> vkladanyVrchol) {
        vloz(hodnotaHrany, vkladanyVrchol, jePrazdny() ? null : vychozi.getKoncovy());
    }

    public void vloz(Vrchol<O, H> vkladanyVrchol, Vrchol<O, H> zaKtery) {
        vloz(vychoziHodnotaHrany, vkladanyVrchol, zaKtery);
    }

    public void vloz(H hodnotaHrany, Vrchol<O, H> vkladanyVrchol, Vrchol<O, H> zaKtery) {
        if (zaKtery == null) {
            if (!jePrazdny()) vkladanyVrchol.getKoncovy().setDalsi(hodnotaHrany, vychozi);
            vychozi = vkladanyVrchol;
        }
        else if (zaKtery.jeKoncovy()) zaKtery.setDalsi(hodnotaHrany, vkladanyVrchol);
        else {
            Hrana<O, H> hrana = zaKtery.getHranaKDalsimu();
            Vrchol<O, H> puvodniCil = hrana.getCilovyVrchol();
            hrana.setCilovyVrchol(vkladanyVrchol);
            vkladanyVrchol.getKoncovy().setDalsi(hodnotaHrany, puvodniCil);
        }
    }

    public boolean smaz(Vrchol<O, H> vrcholKeSmazani) {
        Vrchol<O, H> predchozi = null;
        for (Vrchol<O, H> aktualni : this) {
            if (vrcholKeSmazani.equals(aktualni)) {
                smazTento(predchozi, aktualni);
                return true;
            }
            predchozi = aktualni;
        }
        return false;
    }

    /**
     * bude vůbec potřeba metoda pro smazání podle indexu?
     */
    public boolean smaz(int index) {
        Vrchol<O, H> predchozi = null;
        for (Vrchol<O, H> aktualni : this) {
            if (index-- == 0) {
                smazTento(predchozi, aktualni);
                return true;
            }
            predchozi = aktualni;
        }
        return false;
    }

    private void smazTento(Vrchol<O, H> predchozi, Vrchol<O, H> keSmazani) {
        if (predchozi == null) vychozi = vychozi.jeKoncovy() ? null : vychozi.getHranaKDalsimu().getCilovyVrchol();
        else predchozi.getHranaKDalsimu().setCilovyVrchol(keSmazani.getHranaKDalsimu().getCilovyVrchol());
        keSmazani.getHranaKDalsimu().setCilovyVrchol(null);
    }

    public boolean jePrazdny() {
        return vychozi == null;
    }

    @Override
    public Iterator<Vrchol<O, H>> iterator() {
        return new Iterator<>() {

            private Vrchol<O, H> aktVrchol = vychozi;

            @Override
            public boolean hasNext() {
                return aktVrchol != null;
            }

            @Override
            public Vrchol<O, H> next() {
                Vrchol<O, H> predeslyVrchol = aktVrchol;
                aktVrchol = aktVrchol.jeKoncovy() ? null : aktVrchol.getHranaKDalsimu().getCilovyVrchol();
                return predeslyVrchol;
            }

        };
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Vrchol<O, H> vrchol : this) {
            if (stringBuilder.length() != 0) stringBuilder.append(" - ");
            stringBuilder.append(vrchol.get());
            if (!vrchol.jeKoncovy()) stringBuilder.append(" - ").append(vrchol.getHranaKDalsimu().getHodnota());
        }
        return stringBuilder.toString();
    }
}
