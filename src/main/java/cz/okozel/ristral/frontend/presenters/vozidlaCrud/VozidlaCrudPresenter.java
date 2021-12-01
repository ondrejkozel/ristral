package cz.okozel.ristral.frontend.presenters.vozidlaCrud;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.vozidla.TypVozidla;
import cz.okozel.ristral.backend.entity.vozidla.Vozidlo;
import cz.okozel.ristral.backend.security.PrihlasenyUzivatel;
import cz.okozel.ristral.backend.service.entity.TypVozidlaService;
import cz.okozel.ristral.backend.service.entity.VozidloService;
import cz.okozel.ristral.frontend.MainLayout;
import cz.okozel.ristral.frontend.presenters.crud.GenericCrudPresenter;
import cz.okozel.ristral.frontend.views.vozidlaCrud.VozidlaCrudView;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;

@PageTitle("Vozidla")
@Route(value = "vozidla", layout = MainLayout.class)
@PermitAll
public class VozidlaCrudPresenter extends GenericCrudPresenter<Vozidlo, VozidlaCrudView> {

    private final TypVozidlaService typVozidlaService;
    private final Schema aktSchema;

    public VozidlaCrudPresenter(VozidloService vozidloService, PrihlasenyUzivatel prihlasenyUzivatel, TypVozidlaService typVozidlaService) {
        //noinspection OptionalGetWithoutIsPresent
        super(Vozidlo.class, new VozidlaCrudDataProvider(vozidloService, Vozidlo.class, prihlasenyUzivatel.getPrihlasenyUzivatel().get().getSchema()));
        aktSchema = prihlasenyUzivatel.getPrihlasenyUzivatel().get().getSchema();
        this.typVozidlaService = typVozidlaService;
    }

    @SuppressWarnings("FieldCanBeLocal")
    private TextField nazev;
    @SuppressWarnings("FieldCanBeLocal")
    private TextArea popis;
    @SuppressWarnings("FieldCanBeLocal")
    private IntegerField obsaditelnost;
    private ComboBox<TypVozidla> typ;

    @Override
    protected CrudEditor<Vozidlo> vytvorEditor() {
        nazev = new TextField("Název");
        nazev.setRequired(true);
        //
        obsaditelnost = new IntegerField("Obsaditelnost");
        obsaditelnost.setPlaceholder("maximální počet cestujících");
        obsaditelnost.setTitle("Nepovinné, pro neomezený počet cestujících ponechte prázdné.");
        obsaditelnost.setMin(0);
        obsaditelnost.setClearButtonVisible(true);
        obsaditelnost.setStep(1);
        obsaditelnost.setHasControls(true);
        //
        typ = new ComboBox<>("Typ vozidla");
        typ.setRequired(true);
        //
        popis = new TextArea("Popis");
        //
        FormLayout form = new FormLayout(nazev, obsaditelnost, typ, popis);
        Binder<Vozidlo> binder = new BeanValidationBinder<>(Vozidlo.class);
        binder.bindInstanceFields(this);
        return new BinderCrudEditor<>(binder, form);
    }

    @PostConstruct
    private void naplnComboBox() {
        typ.setItems(typVozidlaService.findAll(aktSchema));
    }

}