package cz.okozel.ristral.frontend.presenters.zastavkyCrud;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
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
import cz.okozel.ristral.frontend.presenters.rezimyObsluhyCrud.RezimyObsluhyCrudPresenter;
import cz.okozel.ristral.frontend.views.zastavkyCrud.ZastavkyCrudView;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;

@PageTitle("Zastávky")
@Route(value = "stops", layout = MainLayout.class)
@PermitAll
public class ZastavkyCrudPresenter extends GenericCrudPresenter<Zastavka, ZastavkyCrudView> {

    private final Schema schema;
    private final RezimObsluhyService rezimObsluhyService;

    public ZastavkyCrudPresenter(ZastavkaService zastavkaService, PrihlasenyUzivatel prihlasenyUzivatel, RezimObsluhyService rezimObsluhyService) {
        //noinspection OptionalGetWithoutIsPresent
        super(Zastavka.class, new ZastavkyCrudDataProvider(zastavkaService, prihlasenyUzivatel.getPrihlasenyUzivatel().get().getSchema()), prihlasenyUzivatel);
        schema = prihlasenyUzivatel.getPrihlasenyUzivatel().get().getSchema();
        this.rezimObsluhyService = rezimObsluhyService;
    }

    @SuppressWarnings("FieldCanBeLocal")
    private TextField nazev;
    private Select<RezimObsluhy> rezimObsluhy;
    @SuppressWarnings("FieldCanBeLocal")
    private TextArea popis;

    @Override
    protected CrudEditor<Zastavka> createEditor() {
        nazev = new TextField("Název");
        nazev.setRequired(true);
        //
        rezimObsluhy = new Select<>();
        rezimObsluhy.setLabel("Režim obsluhy");
        //
        Button spravovatRezimyObsluhyButton = new Button(VaadinIcon.PENCIL.create());
        spravovatRezimyObsluhyButton.addClickListener(event -> UI.getCurrent().navigate(RezimyObsluhyCrudPresenter.class));
        //
        HorizontalLayout rezimyObsluhyLayout = new HorizontalLayout();
        rezimyObsluhyLayout.setAlignItems(FlexComponent.Alignment.END);
        rezimyObsluhyLayout.addAndExpand(rezimObsluhy);
        rezimyObsluhyLayout.add(spravovatRezimyObsluhyButton);
        //
        popis = new TextArea("Popis");
        //
        FormLayout form = new FormLayout(nazev, rezimyObsluhyLayout, popis);
        Binder<Zastavka> binder = new BeanValidationBinder<>(Zastavka.class);
        binder.bindInstanceFields(this);
        return new BinderCrudEditor<>(binder, form);
    }

    @PostConstruct
    private void naplnSelect() {
        rezimObsluhy.setItems(rezimObsluhyService.findAll(schema));
    }

}
