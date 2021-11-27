package cz.okozel.ristral.frontend.presenters.rezimyObsluhyCrud;

import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cz.okozel.ristral.backend.entity.zastavky.RezimObsluhy;
import cz.okozel.ristral.backend.security.PrihlasenyUzivatel;
import cz.okozel.ristral.backend.service.entity.RezimObsluhyService;
import cz.okozel.ristral.backend.service.entity.ZastavkaService;
import cz.okozel.ristral.frontend.MainLayout;
import cz.okozel.ristral.frontend.presenters.crud.GenericCrudPresenter;
import cz.okozel.ristral.frontend.views.rezimyObsluhyCrud.RezimyObsluhyCrudView;

import javax.annotation.security.PermitAll;

@PageTitle("Režimy obsluhy")
@Route(value = "rezimy-obsluhy", layout = MainLayout.class)
@PermitAll
public class RezimyObsluhyCrudPresenter extends GenericCrudPresenter<RezimObsluhy, RezimyObsluhyCrudView> {

    public RezimyObsluhyCrudPresenter(RezimObsluhyService rezimObsluhyService, PrihlasenyUzivatel prihlasenyUzivatel, ZastavkaService zastavkaService) {
        //noinspection OptionalGetWithoutIsPresent
        super(RezimObsluhy.class, new RezimyObsluhyCrudDataProvider(rezimObsluhyService, prihlasenyUzivatel.getPrihlasenyUzivatel().get().getSchema(), zastavkaService));
        getContent().getCrud().addEditListener(event -> {
            boolean upravitelny = event.getItem().isUpravitelny();
            nazev.setReadOnly(!upravitelny);
            popis.setReadOnly(!upravitelny);
            getContent().getCrud().getDeleteButton().setEnabled(upravitelny);
        });
        getContent().getCrud().addNewListener(event -> {
            nazev.setReadOnly(false);
            popis.setReadOnly(false);
        });
    }

    TextField nazev;
    TextArea popis;

    @Override
    protected CrudEditor<RezimObsluhy> vytvorEditor() {
        nazev = new TextField("Název");
        nazev.setRequired(true);
        //
        popis = new TextArea("Popis");
        //
        FormLayout form = new FormLayout(nazev, popis);
        Binder<RezimObsluhy> binder = new BeanValidationBinder<>(RezimObsluhy.class);
        binder.bindInstanceFields(this);
        return new BinderCrudEditor<>(binder, form);
    }

}
