package cz.okozel.ristral.frontend.views.linkyCrud;

import cz.okozel.ristral.backend.entity.lines.Line;
import cz.okozel.ristral.frontend.views.crud.GenericCrudView;

public class LinkyCrudView extends GenericCrudView<Line> {

    public LinkyCrudView() {
        setCrudTexts("Nová linka", "Upravit linku", "Odstranit linku");
    }

    @Override
    public void postInicialization() {
//        deleteAllColumnsExcept("nazev", "popis");
//        renameColumn("nazev", "Název");
//        setColumnExpandRatio("popis", 2);
    }

}
