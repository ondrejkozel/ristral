package cz.okozel.ristral.frontend.presenters.register;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.schema.TypSchematu;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.service.SchemaService;
import cz.okozel.ristral.backend.service.UzivatelService;
import cz.okozel.ristral.frontend.presenters.Presenter;
import cz.okozel.ristral.frontend.presenters.vitejte.VitejtePresenter;
import cz.okozel.ristral.frontend.views.login.LoginView;
import cz.okozel.ristral.frontend.views.register.RegisterView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

@PageTitle("Registrace")
@Route("register")
@AnonymousAllowed
public class RegisterPresenter extends Presenter<RegisterView> {

    Logger logger = LoggerFactory.getLogger(RegisterPresenter.class);

    private UzivatelService uzivatelService;
    private SchemaService schemaService;
    private PasswordEncoder passwordEncoder;

    public RegisterPresenter(UzivatelService uzivatelService, SchemaService schemaService, PasswordEncoder passwordEncoder) {
        this.uzivatelService = uzivatelService;
        this.schemaService = schemaService;
        this.passwordEncoder = passwordEncoder;
        getContent().addListener(RegisterView.RegisterEvent.UlozEvent.class, this::ulozUzivatele);
    }

    private void ulozUzivatele(RegisterView.RegisterEvent.UlozEvent event) {
        Uzivatel uzivatel = event.getUzivatel();
        if (uzivatelService.jeTotoUzivateskeJmenoObsazene(uzivatel.getUzivatelskeJmeno())) getContent().ukazUzivatelskeJmenoObsazeno(uzivatel.getUzivatelskeJmeno());
        else {
            Schema schema = new Schema(TypSchematu.OSOBNI, uzivatel.getUzivatelskeJmeno());
            uzivatel.setSchema(schema);
            uzivatel.setHeslo(passwordEncoder.encode(uzivatel.getHeslo()));
            schemaService.save(schema);
            uzivatelService.save(uzivatel);
            logger.info("Byl vytvořen nový uživatel " + uzivatel.getUzivatelskeJmeno());
            getContent().ukazUspesneZaregistrovano();
            UI.getCurrent().navigate(LoginView.class);
        }
    }

}
