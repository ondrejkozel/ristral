package cz.okozel.ristral.backend.entity.linkyJizdy.graf;


import java.util.Iterator;

public class Graf<O, H> implements Iterable<Vrchol<O, H>> {

    private Vrchol<O, H> vychozi;

    // TODO: 30.10.2021 metody vkládání do grafu

    public Vrchol<O, H> getPrvni() {
        return vychozi;
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
                return aktVrchol != null && !aktVrchol.jeKoncovy();
            }

            @Override
            public Vrchol<O, H> next() {
                Vrchol<O, H> predeslyVrchol = aktVrchol;
                aktVrchol = aktVrchol.getHranaKDalsimu().getCilovyVrchol();
                return predeslyVrchol;
            }

        };
    }
}
