package cz.okozel.ristral.frontend.views.rezimyObsluhyCrud;

import cz.okozel.ristral.backend.entity.zastavky.RezimObsluhy;
import cz.okozel.ristral.frontend.views.crud.GenericCrudView;

public class RezimyObsluhyCrudView extends GenericCrudView<RezimObsluhy> {

    public RezimyObsluhyCrudView() {
        setCrudTexty("Nový režim obsluhy", "Upravit režim obsluhy", "Odstranit režim obsluhy");
    }

    @Override
    public void poInicializaci() {
        odstranSloupceAzNa("nazev", "popis");
        prejmenujSloupec("nazev", "Název");
        setExpandRatioSloupce("popis", 2);
    }

}
