package cz.okozel.ristral.frontend.presenters.uzivateleCrud;

import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.security.PrihlasenyUzivatel;
import cz.okozel.ristral.backend.service.entity.UzivatelService;
import cz.okozel.ristral.frontend.MainLayout;
import cz.okozel.ristral.frontend.presenters.crud.GenericCrudPresenter;
import cz.okozel.ristral.frontend.views.uzivateleCrud.UzivateleCrudView;

import javax.annotation.security.RolesAllowed;

@PageTitle("Uživatelé")
@Route(value = "uzivatele", layout = MainLayout.class)
@RolesAllowed("superadminOrg")
public class UzivateleCrudPresenter extends GenericCrudPresenter<Uzivatel, UzivateleCrudView> {

    public UzivateleCrudPresenter(UzivatelService uzivatelService, PrihlasenyUzivatel prihlasenyUzivatel) {
        //noinspection OptionalGetWithoutIsPresent
        super(Uzivatel.class, new UzivateleCrudDataProvider(uzivatelService, Uzivatel.class, prihlasenyUzivatel.getPrihlasenyUzivatel().get().getSchema()));
    }

    @Override
    protected CrudEditor<Uzivatel> vytvorEditor() {
        TextField nazev = new TextField("Název");
        nazev.setRequired(true);
        //
        FormLayout form = new FormLayout(nazev);
        Binder<Uzivatel> binder = new BeanValidationBinder<>(Uzivatel.class);
//        binder.bindInstanceFields(this);
        return new BinderCrudEditor<>(binder, form);
    }

}
