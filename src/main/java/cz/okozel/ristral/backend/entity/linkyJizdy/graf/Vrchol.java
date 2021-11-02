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

    public boolean jeKoncovy() {
        return hranaKDalsimu == null;
    }

    public Vrchol<O, H> getDalsi() {
        return jeKoncovy() ? null : hranaKDalsimu.getCilovyVrchol();
    }

    protected void setDalsi(H hodnotaHrany, Vrchol<O, H> pridavanyVrchol) {
        if (jeKoncovy()) this.hranaKDalsimu = new Hrana<>(hodnotaHrany, pridavanyVrchol);
        else {
            hranaKDalsimu.setHodnota(hodnotaHrany);
            hranaKDalsimu.setCilovyVrchol(pridavanyVrchol);
        }
    }

    protected void setDalsi(Vrchol<O, H> pridavanyVrchol) {
        if (pridavanyVrchol == null) odstranVztahKDalsimu();
        else if (jeKoncovy()) this.hranaKDalsimu = new Hrana<>(null, pridavanyVrchol);
        else {
            hranaKDalsimu.setCilovyVrchol(pridavanyVrchol);
        }
    }

    public Hrana<O, H> getHranaKDalsimu() {
        return hranaKDalsimu;
    }

    protected void odstranVztahKDalsimu() {
        if (!jeKoncovy()) {
            hranaKDalsimu.setCilovyVrchol(null);
            hranaKDalsimu = null;
        }
    }

    public Vrchol<O, H> getKoncovy() {
        return jeKoncovy() ? this : hranaKDalsimu.getCilovyVrchol().getKoncovy();
    }

    public static class Hrana<O, H> {

        private H hodnota;

        private Vrchol<O, H> cilovyVrchol;

        private Hrana(H hodnota, Vrchol<O, H> cilovyVrchol) {
            this.hodnota = hodnota;
            this.cilovyVrchol = cilovyVrchol;
        }

        public H getHodnota() {
            return hodnota;
        }

        public void setHodnota(H hodnota) {
            this.hodnota = hodnota;
        }

        private Vrchol<O, H> getCilovyVrchol() {
            return cilovyVrchol;
        }

        private void setCilovyVrchol(Vrchol<O, H> cilovyVrchol) {
            this.cilovyVrchol = cilovyVrchol;
        }

    }
}
