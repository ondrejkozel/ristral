package cz.okozel.ristral.frontend.presenters.rezimyObsluhyCrud;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.gridpro.ItemUpdater;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.zastavky.RezimObsluhy;
import cz.okozel.ristral.backend.security.PrihlasenyUzivatel;
import cz.okozel.ristral.backend.service.entity.PeriodaNaZnameniService;
import cz.okozel.ristral.backend.service.entity.RezimObsluhyService;
import cz.okozel.ristral.backend.service.entity.ZastavkaService;
import cz.okozel.ristral.frontend.MainLayout;
import cz.okozel.ristral.frontend.presenters.crud.GenericCrudPresenter;
import cz.okozel.ristral.frontend.views.rezimyObsluhyCrud.RezimyObsluhyCrudView;

import javax.annotation.security.PermitAll;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Režimy obsluhy")
@Route(value = "rezimy-obsluhy", layout = MainLayout.class)
@PermitAll
public class RezimyObsluhyCrudPresenter extends GenericCrudPresenter<RezimObsluhy, RezimyObsluhyCrudView> {

    private final List<RezimObsluhy.PeriodaNaZnameni> keSmazani = new ArrayList<>();
    private final Schema aktSchema;

    public RezimyObsluhyCrudPresenter(RezimObsluhyService rezimObsluhyService, PrihlasenyUzivatel prihlasenyUzivatel, ZastavkaService zastavkaService, PeriodaNaZnameniService periodaNaZnameniService) {
        //noinspection OptionalGetWithoutIsPresent
        super(RezimObsluhy.class, new RezimyObsluhyCrudDataProvider(rezimObsluhyService, prihlasenyUzivatel.getPrihlasenyUzivatel().get().getSchema(), zastavkaService, periodaNaZnameniService));
        //
        aktSchema = prihlasenyUzivatel.getPrihlasenyUzivatel().get().getSchema();
        getContent().getCrud().addNewListener(event -> {
            nazev.setReadOnly(false);
            popis.setReadOnly(false);
            pridatPerioduNaZnameniButton.setEnabled(true);
        });
        getContent().getCrud().addNewListener(event -> {
            naplnGridPro(null, null);
            vyprazdniKeSmazani();
        });
        //
        getContent().getCrud().addEditListener(event -> {
            boolean upravitelny = event.getItem().isUpravitelny();
            nazev.setReadOnly(!upravitelny);
            popis.setReadOnly(!upravitelny);
            getContent().getCrud().getDeleteButton().setEnabled(upravitelny);
            pridatPerioduNaZnameniButton.setEnabled(upravitelny);
        });
        getContent().getCrud().addEditListener(event -> {
            naplnGridPro(event.getItem(), periodaNaZnameniService);
            vyprazdniKeSmazani();
        });
        //
        getContent().getCrud().addSaveListener(event -> {
            ulozGridPro(periodaNaZnameniService);
            vymazKeSmazani(periodaNaZnameniService);
        });
    }

    private TextField nazev;
    private TextArea popis;

    private Button pridatPerioduNaZnameniButton;

    @Override
    protected CrudEditor<RezimObsluhy> vytvorEditor() {
        nazev = new TextField("Název");
        nazev.setRequired(true);
        //
        popis = new TextArea("Popis");
        //
        buildPridatPerioduButton();
        VerticalLayout periodyLayout = new VerticalLayout(buildPeriodyNaZnameniGrid(), pridatPerioduNaZnameniButton);
        periodyLayout.setPadding(false);
        //
        FormLayout form = new FormLayout(nazev, popis);
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP));
        form.addFormItem(periodyLayout, "Periody na znamení");
        //
        Binder<RezimObsluhy> binder = new BeanValidationBinder<>(RezimObsluhy.class);
        binder.bindInstanceFields(this);
        return new BinderCrudEditor<>(binder, form);
    }

    GridPro<RezimObsluhy.PeriodaNaZnameni> periodaNaZnameniGridPro;

    private GridPro<RezimObsluhy.PeriodaNaZnameni> buildPeriodyNaZnameniGrid() {
        periodaNaZnameniGridPro = new GridPro<>();
        periodaNaZnameniGridPro.setMaxHeight("335px");
        periodaNaZnameniGridPro.setMinWidth("200px");
        //
        TimePicker odTimePicker = new TimePicker();
        odTimePicker.setSizeFull();
        periodaNaZnameniGridPro.addEditColumn(RezimObsluhy.PeriodaNaZnameni::getNaZnameniOd)
                .custom(odTimePicker, pokudJeValidniTakProved(RezimObsluhy.PeriodaNaZnameni::setNaZnameniOd))
                .setHeader("Od").setAutoWidth(true);
        //
        TimePicker doTimePicker = new TimePicker();
        doTimePicker.setSizeFull();
        periodaNaZnameniGridPro.addEditColumn(RezimObsluhy.PeriodaNaZnameni::getNaZnameniDo)
                .custom(doTimePicker, pokudJeValidniTakProved(RezimObsluhy.PeriodaNaZnameni::setNaZnameniDo))
                .setHeader("Do").setAutoWidth(true);
        //
        Renderer<RezimObsluhy.PeriodaNaZnameni> dnyNaZnameniRenderer = new TextRenderer<>(item -> item.getDnyNaZnameni().toString());
        MultiSelectListBox<DayOfWeek> dnyVTydnuListBox = new MultiSelectListBox<>();
        dnyVTydnuListBox.setItems(DayOfWeek.values());
        dnyVTydnuListBox.addSelectionListener(event -> getContent().getCrud().setDirty(true));
        periodaNaZnameniGridPro.addEditColumn(RezimObsluhy.PeriodaNaZnameni::getDnyNaZnameni, dnyNaZnameniRenderer)
                .custom(dnyVTydnuListBox, RezimObsluhy.PeriodaNaZnameni::setDnyNaZnameni)
                .setHeader("Dny").setFlexGrow(4);
        //
        periodaNaZnameniGridPro.addColumn(new ComponentRenderer<>((SerializableFunction<RezimObsluhy.PeriodaNaZnameni, Button>) this::vytvorOdstranitPerioduNaZnameniButton))
                .setAutoWidth(true)
                .setFlexGrow(0);
        //
        return periodaNaZnameniGridPro;
    }

    private Button vytvorOdstranitPerioduNaZnameniButton(RezimObsluhy.PeriodaNaZnameni periodaNaZnameni) {
        Icon icon = VaadinIcon.CLOSE_BIG.create();
        icon.setColor("red");
        Button button = new Button(icon);
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        //
        button.addClickListener(event -> odstranPerioduNaZnameniZGridPro(periodaNaZnameni));
        return button;
    }

    private void odstranPerioduNaZnameniZGridPro(RezimObsluhy.PeriodaNaZnameni periodaNaZnameni) {
        List<RezimObsluhy.PeriodaNaZnameni> periodyNaZnameni = getPolozkyGridPro();
        periodyNaZnameni.remove(periodaNaZnameni);
        keSmazani.add(periodaNaZnameni);
        periodaNaZnameniGridPro.setItems(periodyNaZnameni);
        getContent().getCrud().setDirty(true);
    }

    private void vymazKeSmazani(PeriodaNaZnameniService periodaNaZnameniService) {
        keSmazani.forEach(objekt -> {
            if (!objekt.isPersisted()) keSmazani.remove(objekt);
        });
        periodaNaZnameniService.deleteAll(keSmazani);
        vyprazdniKeSmazani();
    }

    private void vyprazdniKeSmazani() {
        keSmazani.clear();
    }

    private void buildPridatPerioduButton() {
        pridatPerioduNaZnameniButton = new Button("Přidat periodu na znamení", VaadinIcon.PLUS.create());
        pridatPerioduNaZnameniButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        pridatPerioduNaZnameniButton.addClickListener(event -> {
            List<RezimObsluhy.PeriodaNaZnameni> polozkyGridPro = getPolozkyGridPro();
            polozkyGridPro.add(RezimObsluhy.PeriodaNaZnameni.vytvorVychoziPerioduNaZnameni(aktSchema, getContent().getCrud().getEditor().getItem()));
            periodaNaZnameniGridPro.setItems(polozkyGridPro);
            getContent().getCrud().setDirty(true);
        });
    }

    private ItemUpdater<RezimObsluhy.PeriodaNaZnameni, LocalTime> pokudJeValidniTakProved(ItemUpdater<RezimObsluhy.PeriodaNaZnameni, LocalTime> neco) {
        return (item, newValue) -> {
            if (newValue != null) neco.accept(item, newValue);
            else Notification.show("Byla zadána neplatná hodnota.");
        };
    }

    private void naplnGridPro(RezimObsluhy aktRezimObsluhy, PeriodaNaZnameniService periodaNaZnameniService) {
        if (periodaNaZnameniService == null) {
            periodaNaZnameniGridPro.setItems(new ArrayList<>());
            return;
        }
        periodaNaZnameniGridPro.setItems(periodaNaZnameniService.findAllByRezimObsluhy(aktRezimObsluhy));
    }

    private void ulozGridPro(PeriodaNaZnameniService periodaNaZnameniService) {
        periodaNaZnameniService.saveAll(getPolozkyGridPro());
    }

    private List<RezimObsluhy.PeriodaNaZnameni> getPolozkyGridPro() {
        return periodaNaZnameniGridPro.getDataProvider().fetch(new Query<>()).collect(Collectors.toList());
    }

}
