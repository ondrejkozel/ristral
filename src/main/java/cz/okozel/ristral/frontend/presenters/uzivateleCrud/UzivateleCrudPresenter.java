package cz.okozel.ristral.frontend.presenters.uzivateleCrud;

import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cz.okozel.ristral.backend.entity.uzivatele.UzivatelOrg;
import cz.okozel.ristral.backend.security.PrihlasenyUzivatel;
import cz.okozel.ristral.backend.service.RegistratorService;
import cz.okozel.ristral.backend.service.entity.UzivatelOrgService;
import cz.okozel.ristral.frontend.MainLayout;
import cz.okozel.ristral.frontend.presenters.crud.GenericCrudPresenter;
import cz.okozel.ristral.frontend.views.uzivateleCrud.UzivateleCrudView;

import javax.annotation.security.RolesAllowed;

@PageTitle("Uživatelé")
@Route(value = "uzivatele", layout = MainLayout.class)
@RolesAllowed("superadminOrg")
public class UzivateleCrudPresenter extends GenericCrudPresenter<UzivatelOrg, UzivateleCrudView> {

    public UzivateleCrudPresenter(UzivatelOrgService uzivatelService, PrihlasenyUzivatel prihlasenyUzivatel, RegistratorService registratorService) {
        //noinspection OptionalGetWithoutIsPresent
        super(UzivatelOrg.class, new UzivateleCrudDataProvider(uzivatelService, UzivatelOrg.class, prihlasenyUzivatel.getPrihlasenyUzivatel().get().getSchema(), prihlasenyUzivatel.getPrihlasenyUzivatel().get(), registratorService));
        //
        getContent().getCrud().addNewListener(event -> hesloField.setVisible(true));
        getContent().getCrud().addNewListener(event -> hesloBinding.setValidatorsDisabled(false));
        //
        getContent().getCrud().addEditListener(event -> hesloField.setVisible(false));
        getContent().getCrud().addEditListener(event -> hesloBinding.setValidatorsDisabled(true));
    }

    @SuppressWarnings("FieldCanBeLocal")
    private TextField jmeno;
    @SuppressWarnings("FieldCanBeLocal")
    private TextField uzivatelskeJmeno;
    @SuppressWarnings("FieldCanBeLocal")
    private TextField email;
    @SuppressWarnings("FieldCanBeLocal")
    private PasswordField hesloField;

    Binder.Binding<UzivatelOrg, String> hesloBinding;
    @Override
    protected CrudEditor<UzivatelOrg> vytvorEditor() {
        jmeno = new TextField("Jméno");
        jmeno.setRequired(true);
        //
        uzivatelskeJmeno = new TextField("Uživatelské jméno");
        uzivatelskeJmeno.setRequired(true);
        //
        email = new TextField("Email");
        email.setRequired(true);
        //
        hesloField = new PasswordField("Heslo");
        hesloField.setRequired(true);
        //
        FormLayout form = new FormLayout(jmeno, uzivatelskeJmeno, email, hesloField);
        Binder<UzivatelOrg> binder = new BeanValidationBinder<>(UzivatelOrg.class);
        binder.bindInstanceFields(this);
        vytvorHesloBinding(binder);
        return new BinderCrudEditor<>(binder, form);
    }

    private void vytvorHesloBinding(Binder<UzivatelOrg> binder) {
        hesloBinding = binder.forField(hesloField)
                .withValidator(s -> s.length() >= 8, "heslo musí mít alespoň 8 znaků")
                .bind(uzivatel -> "", (uzivatel, s) -> {
                    if (!uzivatel.isPersisted()) uzivatel.setHeslo(s);
                });
    }

}
