package cz.okozel.ristral.frontend.presenters.linkyCrud;

import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cz.okozel.ristral.backend.entity.lines.Line;
import cz.okozel.ristral.backend.security.PrihlasenyUzivatel;
import cz.okozel.ristral.backend.service.entity.LineService;
import cz.okozel.ristral.frontend.MainLayout;
import cz.okozel.ristral.frontend.presenters.crud.GenericCrudPresenter;
import cz.okozel.ristral.frontend.views.linkyCrud.LinkyCrudView;

import javax.annotation.security.PermitAll;

@PageTitle("Linky")
@Route(value = "lines", layout = MainLayout.class)
@PermitAll
public class LinkyCrudPresenter extends GenericCrudPresenter<Line, LinkyCrudView> {

    public LinkyCrudPresenter(LineService lineService, PrihlasenyUzivatel prihlasenyUzivatel) {
        super(Line.class, new LinkyCrudDataProvider(lineService, Line.class, prihlasenyUzivatel.getPrihlasenyUzivatel().orElseThrow().getSchema()), prihlasenyUzivatel);
    }

    @Override
    protected CrudEditor<Line> createEditor() {
        Binder<Line> binder = new BeanValidationBinder<>(Line.class);
        return new BinderCrudEditor<>(binder);
    }
}
