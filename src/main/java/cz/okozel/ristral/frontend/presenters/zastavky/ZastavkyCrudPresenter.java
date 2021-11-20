package cz.okozel.ristral.frontend.presenters.zastavky;

import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cz.okozel.ristral.backend.entity.zastavky.Zastavka;
import cz.okozel.ristral.backend.security.PrihlasenyUzivatel;
import cz.okozel.ristral.backend.service.ZastavkaService;
import cz.okozel.ristral.frontend.MainLayout;
import cz.okozel.ristral.frontend.presenters.crud.GenericCrudPresenter;
import cz.okozel.ristral.frontend.views.zastavky.ZastavkyCrudView;

import javax.annotation.security.PermitAll;

@PageTitle("Zastávky")
@Route(value = "zastavky", layout = MainLayout.class)
@PermitAll
public class ZastavkyCrudPresenter extends GenericCrudPresenter<Zastavka, ZastavkyCrudView> {

    TextField nazev;
    TextArea popis;

    public ZastavkyCrudPresenter(ZastavkaService zastavkaService, PrihlasenyUzivatel prihlasenyUzivatel) {
        //noinspection OptionalGetWithoutIsPresent
        super(Zastavka.class, new ZastavkyCrudDataProvider(zastavkaService, prihlasenyUzivatel.getPrihlasenyUzivatel().get().getSchema()));
    }

    @Override
    protected CrudEditor<Zastavka> vytvorEditor() {
        nazev = new TextField("Název");
        popis = new TextArea("Popis");
        nazev.setRequired(true);
        FormLayout form = new FormLayout(nazev, popis);
        Binder<Zastavka> binder = new BeanValidationBinder<>(Zastavka.class);
        binder.bindInstanceFields(this);
        return new BinderCrudEditor<>(binder, form);
    }
}
