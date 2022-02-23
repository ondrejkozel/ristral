package cz.okozel.ristral.frontend.presenters.linkyCrud;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;
import cz.okozel.ristral.backend.entity.lines.Line;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.vozidla.TypVozidla;
import cz.okozel.ristral.backend.security.PrihlasenyUzivatel;
import cz.okozel.ristral.backend.service.entity.LineService;
import cz.okozel.ristral.backend.service.entity.TypVozidlaService;
import cz.okozel.ristral.frontend.MainLayout;
import cz.okozel.ristral.frontend.presenters.crud.GenericCrudPresenter;
import cz.okozel.ristral.frontend.presenters.lineEdit.LineEditPresenter;
import cz.okozel.ristral.frontend.views.linkyCrud.LinkyCrudView;

import javax.annotation.security.PermitAll;

@PageTitle("Linky")
@Route(value = "lines", layout = MainLayout.class)
@PermitAll
public class LinkyCrudPresenter extends GenericCrudPresenter<Line, LinkyCrudView> {

    private final TypVozidlaService typVozidlaService;
    private final Schema aktSchema;

    public LinkyCrudPresenter(LineService lineService, PrihlasenyUzivatel prihlasenyUzivatel, TypVozidlaService typVozidlaService) {
        super(Line.class, new LinkyCrudDataProvider(lineService, Line.class, prihlasenyUzivatel.getPrihlasenyUzivatel().orElseThrow().getSchema()), prihlasenyUzivatel);
        aktSchema = prihlasenyUzivatel.getPrihlasenyUzivatel().get().getSchema();
        this.typVozidlaService = typVozidlaService;
        //
        getContent().getCrud().addNewListener(event -> editRoutesButtonEnabled(false));
        //
        getContent().getCrud().addEditListener(event -> editRoutesButtonEnabled(true));
        getContent().getCrud().addEditListener(this::updateRoutesButtonOnClick);
        //
        fillComboBox();
    }

    @SuppressWarnings("FieldCanBeLocal")
    private TextField label;
    @SuppressWarnings("FieldCanBeLocal")
    private TextArea description;
    private ComboBox<TypVozidla> prefVehicleType;

    private Button editRoutesButton;
    private Registration editRoutesButtonActionRegistration;

    @Override
    protected CrudEditor<Line> createEditor() {
        label = new TextField("Číslo");
        label.setRequired(true);
        //
        prefVehicleType = new ComboBox<>("Preferovaný typ vozidla");
        //
        description = new TextArea("Popis");
        //
        HorizontalLayout buttonLayout = new HorizontalLayout(buildEditRoutesButton());
        //
        FormLayout formLayout = new FormLayout(label, prefVehicleType, description);
        formLayout.add(buttonLayout);
        formLayout.setColspan(description, 2);
        formLayout.setColspan(buttonLayout, 2);
        Binder<Line> binder = new BeanValidationBinder<>(Line.class);
        binder.bindInstanceFields(this);
        return new BinderCrudEditor<>(binder, formLayout);
    }

    private void editRoutesButtonEnabled(boolean enabled) {
        editRoutesButton.setEnabled(enabled);
    }

    private void updateRoutesButtonOnClick(Crud.EditEvent<Line> event) {
        if (editRoutesButtonActionRegistration != null) editRoutesButtonActionRegistration.remove();
        editRoutesButtonActionRegistration = editRoutesButton.addClickListener(clickEvent -> UI.getCurrent().navigate(LineEditPresenter.class, event.getItem().getId().toString()));
    }

    private Button buildEditRoutesButton() {
        editRoutesButton = new Button("Správa tras", VaadinIcon.ROAD.create());
        return editRoutesButton;
    }

    private void fillComboBox() {
        prefVehicleType.setItems(typVozidlaService.findAll(aktSchema));
    }
}
