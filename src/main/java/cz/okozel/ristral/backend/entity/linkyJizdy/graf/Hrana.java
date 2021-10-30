package cz.okozel.ristral.backend.entity.linkyJizdy.graf;

public class Hrana<O, H> {

    private H hodnota;

    private Vrchol<O, H> cilovyVrchol;

    protected Hrana(H hodnota, Vrchol<O, H> cilovyVrchol) {
        this.hodnota = hodnota;
        this.cilovyVrchol = cilovyVrchol;
    }

    public H getHodnota() {
        return hodnota;
    }

    public void setHodnota(H hodnota) {
        this.hodnota = hodnota;
    }

    public Vrchol<O, H> getCilovyVrchol() {
        return cilovyVrchol;
    }

    public void setCilovyVrchol(Vrchol<O, H> cilovyVrchol) {
        this.cilovyVrchol = cilovyVrchol;
    }
}
