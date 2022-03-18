package cz.okozel.ristral.frontend.views.linkyCrud;

import com.vaadin.flow.component.grid.Grid;
import cz.okozel.ristral.backend.entity.lines.Line;
import cz.okozel.ristral.frontend.views.crud.GenericCrudView;

import java.util.ArrayList;
import java.util.List;

public class LinkyCrudView extends GenericCrudView<Line> {

    public LinkyCrudView() {
        setCrudTexts("Nová linka", "Upravit linku", "Odstranit linku");
    }

    @Override
    public void postInicialization() {
        deleteAllColumnsExcept("label", "prefVehicleType", "description");
        renameColumn("label", "Číslo");
        renameColumn("prefVehicleType", "Preferovaný typ vozidla");
        renameColumn("description", "Popis");
        setColumnExpandRatio("description", 2);
        List<Grid.Column<Line>> columns = new ArrayList<>(getColumns());
        columns.add(2, columns.remove(0));
        setColumnOrder(columns);
    }

}
