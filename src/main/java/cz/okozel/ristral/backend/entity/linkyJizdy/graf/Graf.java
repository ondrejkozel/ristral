package cz.okozel.ristral.backend.entity.linkyJizdy.graf;


public class Graf<O, H> {

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
}
