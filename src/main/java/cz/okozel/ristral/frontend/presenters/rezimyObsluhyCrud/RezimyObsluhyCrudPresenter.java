package cz.okozel.ristral.frontend.presenters.rezimyObsluhyCrud;

import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
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
import java.util.ArrayList;
import java.util.stream.Collectors;

@PageTitle("Režimy obsluhy")
@Route(value = "rezimy-obsluhy", layout = MainLayout.class)
@PermitAll
public class RezimyObsluhyCrudPresenter extends GenericCrudPresenter<RezimObsluhy, RezimyObsluhyCrudView> {

    public RezimyObsluhyCrudPresenter(RezimObsluhyService rezimObsluhyService, PrihlasenyUzivatel prihlasenyUzivatel, ZastavkaService zastavkaService, PeriodaNaZnameniService periodaNaZnameniService) {
        //noinspection OptionalGetWithoutIsPresent
        super(RezimObsluhy.class, new RezimyObsluhyCrudDataProvider(rezimObsluhyService, prihlasenyUzivatel.getPrihlasenyUzivatel().get().getSchema(), zastavkaService));
        getContent().getCrud().addEditListener(event -> {
            boolean upravitelny = event.getItem().isUpravitelny();
            nazev.setReadOnly(!upravitelny);
            popis.setReadOnly(!upravitelny);
            getContent().getCrud().getDeleteButton().setEnabled(upravitelny);
        });
        getContent().getCrud().addEditListener(event -> naplnGridPro(event.getItem(), periodaNaZnameniService));
        getContent().getCrud().addNewListener(event -> {
            nazev.setReadOnly(false);
            popis.setReadOnly(false);
        });
        getContent().getCrud().addNewListener(event -> naplnGridPro(null, null));
        getContent().getCrud().addSaveListener(event -> ulozGridPro(periodaNaZnameniService));
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
        GridPro<RezimObsluhy.PeriodaNaZnameni> periodyNaZnameni = buildPeriodyNaZnameniGrid();
        //
        FormLayout form = new FormLayout(nazev, popis, periodyNaZnameni);
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
                .custom(odTimePicker, RezimObsluhy.PeriodaNaZnameni::setNaZnameniOd)
                .setHeader("Od").setAutoWidth(true);
        //
        TimePicker doTimePicker = new TimePicker();
        doTimePicker.setSizeFull();
        periodaNaZnameniGridPro.addEditColumn(RezimObsluhy.PeriodaNaZnameni::getNaZnameniDo)
                .custom(doTimePicker, RezimObsluhy.PeriodaNaZnameni::setNaZnameniDo)
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
        return periodaNaZnameniGridPro;
    }

    private void naplnGridPro(RezimObsluhy aktRezimObsluhy, PeriodaNaZnameniService periodaNaZnameniService) {
        if (periodaNaZnameniService == null) {
            periodaNaZnameniGridPro.setItems(new ArrayList<>());
            return;
        }
        periodaNaZnameniGridPro.setItems(periodaNaZnameniService.findAllByRezimObsluhy(aktRezimObsluhy));
    }

    private void ulozGridPro(PeriodaNaZnameniService periodaNaZnameniService) {
        periodaNaZnameniService.saveAll(periodaNaZnameniGridPro.getDataProvider().fetch(new Query<>()).collect(Collectors.toList()));
    }

}
