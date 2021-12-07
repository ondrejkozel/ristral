package cz.okozel.ristral.frontend.views.rezimyObsluhyCrud;

import cz.okozel.ristral.backend.entity.zastavky.RezimObsluhy;
import cz.okozel.ristral.frontend.views.crud.GenericCrudView;

public class RezimyObsluhyCrudView extends GenericCrudView<RezimObsluhy> {

    public RezimyObsluhyCrudView() {
        setCrudTexts("Nový režim obsluhy", "Upravit režim obsluhy", "Odstranit režim obsluhy");
    }

    @Override
    public void postInicialization() {
        deleteAllColumnsExcept("nazev", "popis");
        renameColumn("nazev", "Název");
        setColumnExpandRatio("popis", 2);
    }

}
