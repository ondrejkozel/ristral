package cz.okozel.ristral.frontend.presenters.zastavkyCrud;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.zastavky.RezimObsluhy;
import cz.okozel.ristral.backend.entity.zastavky.Zastavka;
import cz.okozel.ristral.backend.security.PrihlasenyUzivatel;
import cz.okozel.ristral.backend.service.entity.RezimObsluhyService;
import cz.okozel.ristral.backend.service.entity.ZastavkaService;
import cz.okozel.ristral.frontend.MainLayout;
import cz.okozel.ristral.frontend.presenters.crud.GenericCrudPresenter;
import cz.okozel.ristral.frontend.views.zastavkyCrud.ZastavkyCrudView;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;

@PageTitle("Zastávky")
@Route(value = "zastavky", layout = MainLayout.class)
@PermitAll
public class ZastavkyCrudPresenter extends GenericCrudPresenter<Zastavka, ZastavkyCrudView> {

    TextField nazev;
    ComboBox<RezimObsluhy> rezimObsluhy;
    TextArea popis;

    private final Schema schema;
    private final RezimObsluhyService rezimObsluhyService;

    public ZastavkyCrudPresenter(ZastavkaService zastavkaService, PrihlasenyUzivatel prihlasenyUzivatel, RezimObsluhyService rezimObsluhyService) {
        //noinspection OptionalGetWithoutIsPresent
        super(Zastavka.class, new ZastavkyCrudDataProvider(zastavkaService, prihlasenyUzivatel.getPrihlasenyUzivatel().get().getSchema()));
        schema = prihlasenyUzivatel.getPrihlasenyUzivatel().get().getSchema();
        this.rezimObsluhyService = rezimObsluhyService;
    }

    @Override
    protected CrudEditor<Zastavka> vytvorEditor() {
        nazev = new TextField("Název");
        nazev.setRequired(true);
        //
        rezimObsluhy = new ComboBox<>("Režim obsluhy");
        rezimObsluhy.setRequired(true);
        //
        popis = new TextArea("Popis");
        //
        FormLayout form = new FormLayout(nazev, rezimObsluhy, popis);
        Binder<Zastavka> binder = new BeanValidationBinder<>(Zastavka.class);
        binder.bindInstanceFields(this);
        return new BinderCrudEditor<>(binder, form);
    }

    @PostConstruct
    public void naplnComboBox() {
        rezimObsluhy.setItems(rezimObsluhyService.findAll(schema));
    }

}
