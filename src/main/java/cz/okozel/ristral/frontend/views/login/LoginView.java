package cz.okozel.ristral.frontend.views.login;

import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cz.okozel.ristral.backend.security.PrihlasenyUzivatel;
import cz.okozel.ristral.frontend.views.prehled.PrehledView;

@PageTitle("Přihlášení")
@Route(value = "login")
public class LoginView extends LoginOverlay implements BeforeEnterObserver {

    private PrihlasenyUzivatel prihlasenyUzivatel;

    public LoginView(PrihlasenyUzivatel prihlasenyUzivatel) {
        this.prihlasenyUzivatel = prihlasenyUzivatel;
        setAction("login");
        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(buildHeader());
        i18n.setForm(buildForm());
        i18n.setErrorMessage(buildErrorMessage());
        setI18n(i18n);
        setForgotPasswordButtonVisible(false);
        setOpened(true);
    }

    private LoginI18n.Header buildHeader() {
        LoginI18n.Header header = new LoginI18n.Header();
        header.setTitle("Ristral");
        header.setDescription("Přihlášení do informačního systému");
        return header;
    }

    private LoginI18n.Form buildForm() {
        LoginI18n.Form form = new LoginI18n.Form();
        form.setTitle("Přihlášení");
        form.setUsername("Uživatelské jméno");
        form.setPassword("Heslo");
        form.setSubmit("Přihlásit se");
        form.setForgotPassword("Zapomenuté heslo");
        return form;
    }

    private LoginI18n.ErrorMessage buildErrorMessage() {
        LoginI18n.ErrorMessage errorMessage = new LoginI18n.ErrorMessage();
        errorMessage.setTitle("Nesprávné přihlašovací údaje");
        errorMessage.setMessage("Ověřte, že bylo správně zadáno uživatelské jméno a heslo a zkuste to znovu.");
        return errorMessage;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (prihlasenyUzivatel.get().isPresent()) {
            beforeEnterEvent.forwardTo(PrehledView.class);
            setOpened(false);
        }
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) setError(true);
    }
}
