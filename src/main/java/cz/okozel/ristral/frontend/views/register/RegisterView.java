package cz.okozel.ristral.frontend.views.register;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import cz.okozel.ristral.backend.entity.uzivatele.OsobniUzivatel;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;

public class RegisterView extends VerticalLayout {

    private Uzivatel uzivatel;

    Binder<Uzivatel> binder;

    TextField uzivatelskeJmeno, jmeno;
    PasswordField heslo;
    EmailField email;

    public RegisterView() {
        uzivatel = new OsobniUzivatel();
        uzivatelskeJmeno = new TextField("Uživatelské jméno");
        heslo = new PasswordField("Heslo");
        jmeno = new TextField("Jméno");
        email = new EmailField("Email");
        //
        Button potvrdit = new Button("Zaregistrovat se");
        potvrdit.addClickListener(event -> validujAUloz());
        potvrdit.addClickShortcut(Key.ENTER);
        potvrdit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        //
        Button zapomenuteHeslo = new Button("Zapomenuté heslo");
        zapomenuteHeslo.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        zapomenuteHeslo.addClickListener(event -> Notification.show("Zatím nic neumím, ale už brzo to tak nebude! \uD83D\uDE0A"));
        //
        uzivatelskeJmeno.setRequired(true);
        heslo.setRequired(true);
        jmeno.setRequired(true);
        email.setRequiredIndicatorVisible(true);
        //
        FormLayout form = new FormLayout();
        form.add(
                uzivatelskeJmeno,
                heslo,
                jmeno,
                email
        );
        //
        Section section = new Section();
        section.add(
                new H2("Registrace"),
                form,
                new HorizontalLayout(
                        potvrdit,
                        zapomenuteHeslo)
        );
        add(section);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        //
        binder = new BeanValidationBinder<>(Uzivatel.class);
        binder.bindInstanceFields(this);
        binder.forField(heslo)
                .withValidator(s -> s.length() >= 8, "heslo musí mít alespoň 8 znaků")
                .bind(Uzivatel::getHeslo, Uzivatel::setHeslo);
    }

    private void validujAUloz() {
        try {
            binder.writeBean(uzivatel);
            fireEvent(new RegisterEvent.UlozEvent(this, uzivatel));
        } catch (ValidationException e) {
            Notification.show("Byly zadány neplatné údaje. ❌");
        }
    }

    public void ukazUzivatelskeJmenoObsazeno(String jmeno) {
        Notification.show(String.format("Uživatelské jméno %s je již obsazeno.", jmeno));
    }

    public void ukazUspesneZaregistrovano() {
        Notification.show("Registrace proběhla úspěšně, nyní se prosím přihlaste. \uD83D\uDC4B");
    }

    public static abstract class RegisterEvent extends ComponentEvent<RegisterView> {

        private final Uzivatel uzivatel;

        public RegisterEvent(RegisterView source, Uzivatel uzivatel) {
            super(source, false);
            this.uzivatel = uzivatel;
        }

        public Uzivatel getUzivatel() {
            return uzivatel;
        }

        public static class UlozEvent extends RegisterEvent {
            UlozEvent(RegisterView source, Uzivatel uzivatel) {
                super(source, uzivatel);
            }
        }

    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
