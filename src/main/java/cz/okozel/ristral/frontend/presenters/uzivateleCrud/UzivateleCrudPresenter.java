package cz.okozel.ristral.frontend.presenters.uzivateleCrud;

import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cz.okozel.ristral.backend.entity.uzivatele.Role;
import cz.okozel.ristral.backend.entity.uzivatele.SuperadminOrg;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.entity.uzivatele.UzivatelOrg;
import cz.okozel.ristral.backend.security.PrihlasenyUzivatel;
import cz.okozel.ristral.backend.service.RegistratorService;
import cz.okozel.ristral.backend.service.entity.UzivatelService;
import cz.okozel.ristral.frontend.MainLayout;
import cz.okozel.ristral.frontend.presenters.crud.GenericCrudPresenter;
import cz.okozel.ristral.frontend.views.uzivateleCrud.UzivateleCrudView;

import javax.annotation.security.RolesAllowed;
import java.util.stream.Stream;

/**
 * Vytvořit nový repositář a novou službu bylo nejjednodušší řešení toho, že crud nemůže mít abstraktní třídu jako
 * generický typ.
 */
@PageTitle("Uživatelé")
@Route(value = "uzivatele", layout = MainLayout.class)
@RolesAllowed("superadminOrg")
public class UzivateleCrudPresenter extends GenericCrudPresenter<UzivatelOrg, UzivateleCrudView> {

    private final SuperadminOrg prihlasenyUzivatel;

    public UzivateleCrudPresenter(UzivatelOrgService uzivatelOrgService, PrihlasenyUzivatel prihlasenyUzivatel, RegistratorService registratorService, UzivatelService uzivatelService) {
        super(UzivatelOrg.class, new UzivateleCrudDataProvider(uzivatelOrgService, UzivatelOrg.class, prihlasenyUzivatel, registratorService), prihlasenyUzivatel);
        //přihlášený uživatel je vždy superadministrátor
        //noinspection OptionalGetWithoutIsPresent
        this.prihlasenyUzivatel = (SuperadminOrg) prihlasenyUzivatel.getPrihlasenyUzivatel().get();
        //
        getContent().getCrud().addNewListener(event -> hesloField.setVisible(true));
        getContent().getCrud().addNewListener(event -> hesloBinding.setValidatorsDisabled(false));
        getContent().getCrud().addNewListener(event -> roleSelect.setVisible(false));
        getContent().getCrud().addNewListener(event -> roleSelect.setValue(Role.UZIVATEL_ORG));
        getContent().getCrud().addNewListener(event -> nastavNeupravitelnostFieldu(false));
        //
        getContent().getCrud().addEditListener(event -> hesloField.setVisible(false));
        getContent().getCrud().addEditListener(event -> hesloBinding.setValidatorsDisabled(true));
        getContent().getCrud().addEditListener(event -> roleSelect.setVisible(true));
        getContent().getCrud().addEditListener(event -> roleSelect.setValue(event.getItem().getRole()));
        getContent().getCrud().addEditListener(event -> nastavNeupravitelnostFieldu(event.getItem().getRole() == Role.SUPERADMIN_ORG));
        //
        getContent().getCrud().addSaveListener(event -> pokudPotrebaZmenRoli(uzivatelService, event.getItem()));
    }

    private void nastavNeupravitelnostFieldu(boolean neupravitelny) {
        Stream
                .of(jmeno, uzivatelskeJmeno, email)
                .forEach(field -> field.setReadOnly(neupravitelny));
        roleSelect.setReadOnly(neupravitelny);
        roleSelect.setHelperText(neupravitelny ? "Protože je tento uživatel superadministrátor, osobní údaje si spravuje sám." : "");
    }

    private void pokudPotrebaZmenRoli(UzivatelService uzivatelService, Uzivatel uzivatelKeZmene) {
        if (uzivatelKeZmene.getRole() != roleSelect.getValue())
            if (this.prihlasenyUzivatel.nastavRoliUctu(uzivatelKeZmene, roleSelect.getValue(), uzivatelService))
                Notification.show(String.format("Uživatel %s je nyní %s.", uzivatelKeZmene.getJmeno(), roleSelect.getValue().getNazev()));
    }

    private TextField jmeno;
    private TextField uzivatelskeJmeno;
    private TextField email;
    private PasswordField hesloField;

    private Select<Role> roleSelect;

    Binder.Binding<UzivatelOrg, String> hesloBinding;

    @Override
    protected CrudEditor<UzivatelOrg> createEditor() {
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
        roleSelect = new Select<>(Role.UZIVATEL_ORG, Role.ADMIN_ORG, Role.SUPERADMIN_ORG);
        roleSelect.setLabel("Role");
        //
        FormLayout form = new FormLayout(jmeno, uzivatelskeJmeno, email, hesloField, roleSelect);
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
