package cz.okozel.ristral.frontend.views.zastavkyCrud;

import com.vaadin.flow.component.UI;
import cz.okozel.ristral.backend.entity.zastavky.Zastavka;
import cz.okozel.ristral.frontend.presenters.rezimyObsluhyCrud.RezimyObsluhyCrudPresenter;
import cz.okozel.ristral.frontend.views.crud.GenericCrudView;

public class ZastavkyCrudView extends GenericCrudView<Zastavka> {

    public ZastavkyCrudView() {
        setCrudTexts("Nová zastávka", "Upravit zastávku", "Odstranit zastávku");
        //
        pridejHrPodSoubor();
        pridejMenuItemPodSoubor("Spravovat režimy obsluhy", event -> UI.getCurrent().navigate(RezimyObsluhyCrudPresenter.class), false);
    }

    @Override
    public void postInicialization() {
        deleteAllColumnsExcept("nazev", "popis");
        renameColumn("nazev", "Název");
        setColumnExpandRatio("popis", 2);
    }

}
