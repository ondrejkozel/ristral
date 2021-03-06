package cz.okozel.ristral.frontend.presenters.vozidlaCrud;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.GeneratedVaadinComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
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
import cz.okozel.ristral.backend.service.entity.LineService;
import cz.okozel.ristral.backend.service.entity.TypVozidlaService;
import cz.okozel.ristral.backend.service.entity.VozidloService;
import cz.okozel.ristral.frontend.MainLayout;
import cz.okozel.ristral.frontend.presenters.crud.GenericCrudPresenter;
import cz.okozel.ristral.frontend.views.vozidlaCrud.VozidlaCrudView;

import javax.annotation.security.PermitAll;
import java.util.List;

@PageTitle("Vozidla")
@Route(value = "vozidla", layout = MainLayout.class)
@PermitAll
public class VozidlaCrudPresenter extends GenericCrudPresenter<Vozidlo, VozidlaCrudView> {

    private final TypVozidlaService typVozidlaService;
    private final Schema aktSchema;

    public VozidlaCrudPresenter(VozidloService vozidloService, PrihlasenyUzivatel prihlasenyUzivatel, TypVozidlaService typVozidlaService, LineService lineService) {
        //noinspection OptionalGetWithoutIsPresent
        super(Vozidlo.class, new VozidlaCrudDataProvider(vozidloService, Vozidlo.class, prihlasenyUzivatel.getPrihlasenyUzivatel().get().getSchema()), prihlasenyUzivatel);
        aktSchema = prihlasenyUzivatel.getPrihlasenyUzivatel().get().getSchema();
        this.typVozidlaService = typVozidlaService;
        //
        getContent().getCrud().addSaveListener(event -> smazNepouzivaneTypyVozidel());
        getContent().getCrud().addSaveListener(event -> vynulujNovyTypVozidla());
        //
        getContent().getCrud().addDeleteListener(event -> smazNepouzivaneTypyVozidel());
        getContent().getCrud().addDeleteListener(event -> vynulujNovyTypVozidla());
        //
        getContent().getCrud().addCancelListener(event -> smazNepouzivaneTypyVozidel());
        getContent().getCrud().addCancelListener(event -> vynulujNovyTypVozidla());
        //
        naplnComboBox();
    }

    private void smazNepouzivaneTypyVozidel() {
        List<TypVozidla> unusedVehicleTypes = typVozidlaService.deleteUnusedVehicleTypes(aktSchema);
        if (!unusedVehicleTypes.isEmpty()) {
            StringBuilder zprava = new StringBuilder();
            zprava.append("Typ vozidla ").append(unusedVehicleTypes.get(0).getNazev());
            if (unusedVehicleTypes.size() > 1) zprava.append(" a ").append(unusedVehicleTypes.size() - 1).append(" dal????ch");
            zprava.append(" byl vymaz??n, proto??e nebyl nastaven ????dn??mu vozidlu.");
            //
            naplnComboBox();
            //
            Notification.show(zprava.toString());
        }
    }

    @SuppressWarnings("FieldCanBeLocal")
    private TextField nazev;
    @SuppressWarnings("FieldCanBeLocal")
    private TextArea popis;
    @SuppressWarnings("FieldCanBeLocal")
    private IntegerField obsaditelnost;
    private ComboBox<TypVozidla> typ;

    private TypVozidla novyTypVozidla;

    @Override
    protected CrudEditor<Vozidlo> createEditor() {
        nazev = new TextField("N??zev");
        nazev.setRequired(true);
        //
        obsaditelnost = new IntegerField("Obsaditelnost");
        obsaditelnost.setPlaceholder("maxim??ln?? po??et cestuj??c??ch");
        obsaditelnost.setTitle("Nepovinn??, pro neomezen?? po??et cestuj??c??ch ponechte pr??zdn??.");
        obsaditelnost.setMin(0);
        obsaditelnost.setClearButtonVisible(true);
        obsaditelnost.setStep(1);
        obsaditelnost.setHasControls(true);
        //
        typ = new ComboBox<>("Typ vozidla");
        typ.setRequired(true);
        typ.setAllowCustomValue(true);
        typ.addCustomValueSetListener(this::typComboBoxMaNovouCustomHodnotu);
        typ.addValueChangeListener(event -> {
            if (event.getValue() != null) overValidituTypComboBoxu(event.getValue().getNazev());
        });
        typ.setHelperText("Pro vytvo??en?? nov??ho typu vozidla napi??te jeho n??zev.");
        //
        popis = new TextArea("Popis");
        //
        FormLayout form = new FormLayout(nazev, obsaditelnost, typ, popis);
        Binder<Vozidlo> binder = new BeanValidationBinder<>(Vozidlo.class);
        binder.bindInstanceFields(this);
        return new BinderCrudEditor<>(binder, form);
    }

    private void typComboBoxMaNovouCustomHodnotu(GeneratedVaadinComboBox.CustomValueSetEvent<ComboBox<TypVozidla>> event) {
        if (novyTypVozidla == null) novyTypVozidla = new TypVozidla("", aktSchema);
        if (!overValidituTypComboBoxu(event.getDetail())) return;
        novyTypVozidla.setNazev(event.getDetail());
        typVozidlaService.save(novyTypVozidla);
        naplnComboBox();
        typ.setValue(novyTypVozidla);
    }

    private boolean overValidituTypComboBoxu(String novaHondota) {
        boolean invalid = novaHondota.length() > 50;
        typ.setInvalid(invalid);
        getContent().getCrud().getSaveButton().setEnabled(!invalid);
        if (invalid) {
            typ.setErrorMessage("n??zev typu vozidla nesm?? b??t del???? ne?? 50 znak??");
            return false;
        }
        return true;
    }

    private void vynulujNovyTypVozidla() {
        novyTypVozidla = null;
    }

    private void naplnComboBox() {
        typ.setItems(typVozidlaService.findAll(aktSchema));
    }

}
