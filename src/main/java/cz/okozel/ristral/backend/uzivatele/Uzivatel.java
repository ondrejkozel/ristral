package cz.okozel.ristral.backend.uzivatele;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Uzivatel {
    @Id
    @GeneratedValue()
    private Long id;
    private String jmeno;

    public Uzivatel() {}

    public Uzivatel(String jmeno) {
        this.jmeno = jmeno;
    }

    public Long getId() {
        return id;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }
}
