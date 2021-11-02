package cz.okozel.ristral.backend.entity.linkyJizdy.graf;

public class Vrchol<O, H> {

    private final O objekt;

    private Hrana<O, H> hranaKDalsimu;

    public Vrchol(O objekt) {
        this.objekt = objekt;
    }

    public O get() {
        return objekt;
    }

    public Hrana<O, H> getHranaKDalsimu() {
        return hranaKDalsimu;
    }

    public boolean jeKoncovy() {
        return hranaKDalsimu == null;
    }

    protected void setDalsi(H hodnotaHranyKNovemuVrcholu, Vrchol<O, H> pridavanyVrchol) {
        if (jeKoncovy()) this.hranaKDalsimu = new Hrana<>(hodnotaHranyKNovemuVrcholu, pridavanyVrchol);
        else {
            hranaKDalsimu.setHodnota(hodnotaHranyKNovemuVrcholu);
            hranaKDalsimu.setCilovyVrchol(pridavanyVrchol);
        }
    }

    protected void odstranDalsi() {
        if (!jeKoncovy()) {
            hranaKDalsimu.setCilovyVrchol(null);
            hranaKDalsimu = null;
        }
    }

    public Vrchol<O, H> getKoncovy() {
        return jeKoncovy() ? this : hranaKDalsimu.getCilovyVrchol().getKoncovy();
    }
}
