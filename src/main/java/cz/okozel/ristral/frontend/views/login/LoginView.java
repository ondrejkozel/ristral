package cz.okozel.ristral.frontend.views.login;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cz.okozel.ristral.backend.security.PrihlasenyUzivatel;
import cz.okozel.ristral.frontend.presenters.vitejte.VitejtePresenter;

@PageTitle("Přihlášení")
@Route(value = "login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final PrihlasenyUzivatel prihlasenyUzivatel;
    private LoginForm loginForm;

    public LoginView(PrihlasenyUzivatel prihlasenyUzivatel) {
        this.prihlasenyUzivatel = prihlasenyUzivatel;
        loginForm = new LoginForm();
        loginForm.setAction("login");
        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setForm(buildForm());
        i18n.setErrorMessage(buildErrorMessage());
        loginForm.setI18n(i18n);
        loginForm.setForgotPasswordButtonVisible(false);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(loginForm);
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
        if (prihlasenyUzivatel.jePrihlaseny()) {
            beforeEnterEvent.forwardTo(VitejtePresenter.class);
        }
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) loginForm.setError(true);
    }

}
