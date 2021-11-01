package cz.okozel.ristral.backend.entity.linkyJizdy.graf;


import java.util.Iterator;

public class Graf<O, H> implements Iterable<Vrchol<O, H>> {

    private Vrchol<O, H> vychozi;

    // TODO: 30.10.2021 metody vkládání do grafu

    public Vrchol<O, H> getPrvni() {
        return vychozi;
    }

    public Vrchol<O, H> getPosledni() {
        if (jePrazdny()) return null;
        else return vychozi.getKoncovy();
    }

    public void smaz(Vrchol<O, H> vrcholKeSmazani) {
        Vrchol<O, H> predchozi = null, aktualni = vychozi;
        boolean pokracuj = true;
        while (pokracuj) {
            if (aktualni.equals(vrcholKeSmazani)) {
                if (predchozi == null) vychozi = vychozi.jeKoncovy() ? null : vychozi.getHranaKDalsimu().getCilovyVrchol();
                else predchozi.getHranaKDalsimu().setCilovyVrchol(aktualni.getHranaKDalsimu().getCilovyVrchol());
                aktualni.getHranaKDalsimu().setCilovyVrchol(null);
            }
            if (aktualni.jeKoncovy()) pokracuj = false;
            else {
                predchozi = aktualni;
                aktualni = aktualni.getHranaKDalsimu().getCilovyVrchol();
            }
        }
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
