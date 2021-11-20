package cz.okozel.ristral.frontend.views.zastavky;

import cz.okozel.ristral.backend.entity.zastavky.Zastavka;
import cz.okozel.ristral.frontend.views.crud.GenericCrudView;

import java.util.List;

public class ZastavkyView extends GenericCrudView<Zastavka> {

    public ZastavkyView() {
    }

    @Override
    public void poInicializaci() {
        odstranSloupceAzNa(List.of("nazev"));
        prejmenujSloupec("nazev", "Název");
    }

}
