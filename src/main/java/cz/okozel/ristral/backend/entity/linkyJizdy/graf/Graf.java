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

    /**
     * neefektivní
     */
    public Vrchol<O, H> getPosledni() {
        Vrchol<O, H> vrchol = vychozi;
        for (Vrchol<O, H> aktVrchol : this) {
            vrchol = aktVrchol;
        }
        return vrchol;
    }

    public void vlozNaZacatek(Vrchol<O, H> vkladanyVrchol) {
        vlozNaZacatek(vychoziHodnotaHrany, vkladanyVrchol);
    }

    public void vlozNaZacatek(H hodnotaHrany, Vrchol<O, H> vkladanyVrchol) {
        vloz(hodnotaHrany, vkladanyVrchol, null);
    }

    public void vlozNaKonec(Vrchol<O, H> vkladanyVrchol) {
        vlozNaKonec(vychoziHodnotaHrany, vkladanyVrchol);
    }

    public void vlozNaKonec(H hodnotaHrany, Vrchol<O, H> vkladanyVrchol) {
        vloz(hodnotaHrany, vkladanyVrchol, jePrazdny() ? null : vychozi.getKoncovy());
    }

    public void vloz(Vrchol<O, H> vkladanyVrchol, Vrchol<O, H> zaKtery) {
        vloz(vychoziHodnotaHrany, vkladanyVrchol, zaKtery);
    }

    public void vloz(H hodnotaHrany, Vrchol<O, H> vkladanyVrchol, Vrchol<O, H> zaKtery) {
        if (vkladanyVrchol == null || hodnotaHrany == null) return;
        if (zaKtery == null) {
            if (!jePrazdny()) vkladanyVrchol.getKoncovy().setDalsi(hodnotaHrany, vychozi);
            vychozi = vkladanyVrchol;
        }
        else if (zaKtery.jeKoncovy()) zaKtery.setDalsi(hodnotaHrany, vkladanyVrchol);
        else {
            Vrchol<O, H> puvodniCil = zaKtery.getDalsi();
            zaKtery.setDalsi(vkladanyVrchol);
            vkladanyVrchol.getKoncovy().setDalsi(hodnotaHrany, puvodniCil);
        }
    }

    public boolean smaz(Vrchol<O, H> vrcholKeSmazani) {
        if (vrcholKeSmazani == null) return false;
        Vrchol<O, H> predchozi = null;
        for (Vrchol<O, H> aktualni : this) {
            if (aktualni.get().equals(vrcholKeSmazani.get())) {
                smazTento(predchozi, aktualni);
                return true;
            }
            predchozi = aktualni;
        }
        return false;
    }

    private void smazTento(Vrchol<O, H> predchozi, Vrchol<O, H> keSmazani) {
        if (predchozi == null) vychozi = vychozi.jeKoncovy() ? null : vychozi.getDalsi();
        else predchozi.setDalsi(keSmazani.getDalsi());
        keSmazani.setDalsi(null);
    }

    public boolean jePrazdny() {
        return vychozi == null;
    }

    public void vyprazdni() {
        for (Vrchol<O, H> vrchol : this) {
            vrchol.odstranVztahKDalsimu();
        }
        vychozi = null;
    }

    public int pocetVrcholu() {
        int pocet = 0;
        for (Vrchol<O, H> ignored : this) pocet++;
        return pocet;
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
                aktVrchol = aktVrchol.jeKoncovy() ? null : aktVrchol.getDalsi();
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
