package cz.okozel.ristral.frontend.presenters.register;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.service.RegistratorService;
import cz.okozel.ristral.frontend.presenters.Presenter;
import cz.okozel.ristral.frontend.views.login.LoginView;
import cz.okozel.ristral.frontend.views.register.RegisterView;

@PageTitle("Registrace")
@Route("register")
@AnonymousAllowed
public class RegisterPresenter extends Presenter<RegisterView> {

    private final RegistratorService registratorService;

    public RegisterPresenter(RegistratorService registratorService) {
        this.registratorService = registratorService;
        getContent().addListener(RegisterView.RegisterEvent.UlozEvent.class, this::ulozUzivatele);
    }

    private void ulozUzivatele(RegisterView.RegisterEvent.UlozEvent event) {
        Uzivatel uzivatel = event.getUzivatel();
        if (registratorService.zaregistrujOsobniUcetAVytvorMuNoveSchema(uzivatel)) {
            getContent().ukazUspesneZaregistrovano();
            UI.getCurrent().navigate(LoginView.class);
        }
        else getContent().ukazUzivatelskeJmenoObsazeno(uzivatel.getUzivatelskeJmeno());
    }

}
