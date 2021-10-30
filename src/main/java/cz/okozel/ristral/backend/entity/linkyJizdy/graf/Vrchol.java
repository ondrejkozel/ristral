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

    public void setDalsi(H hodnotaHranyKNovemuVrcholu, O objektNovehoVrcholu) {
        setDalsi(hodnotaHranyKNovemuVrcholu, new Vrchol<>(objektNovehoVrcholu));
    }

    public void setDalsi(H hodnotaHranyKNovemuVrcholu, Vrchol<O, H> pridavanyVrchol) {
        this.hranaKDalsimu = new Hrana<>(hodnotaHranyKNovemuVrcholu, pridavanyVrchol);
    }

    public Vrchol<O, H> getKoncovy() {
        return jeKoncovy() ? this : hranaKDalsimu.getCilovyVrchol().getKoncovy();
    }
}
