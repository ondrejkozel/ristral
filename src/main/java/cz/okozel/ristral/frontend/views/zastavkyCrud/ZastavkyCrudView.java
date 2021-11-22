package cz.okozel.ristral.frontend.views.zastavkyCrud;

import cz.okozel.ristral.backend.entity.zastavky.Zastavka;
import cz.okozel.ristral.frontend.views.crud.GenericCrudView;

public class ZastavkyCrudView extends GenericCrudView<Zastavka> {

    public ZastavkyCrudView() {
        setCrudTexty("Nová zastávka", "Upravit zastávku", "Odstranit zastávku");
    }

    @Override
    public void poInicializaci() {
        odstranSloupceAzNa("nazev", "popis");
        prejmenujSloupec("nazev", "Název");
        setExpandRatioSloupce("popis", 2);
    }

}
