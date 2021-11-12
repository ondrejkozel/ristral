package cz.okozel.ristral.frontend.presenters.prehled;

import com.vaadin.flow.router.*;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.security.PrihlasenyUzivatel;
import cz.okozel.ristral.backend.service.VozidloService;
import cz.okozel.ristral.backend.service.ZastavkaService;
import cz.okozel.ristral.frontend.MainLayout;
import cz.okozel.ristral.frontend.presenters.Presenter;
import cz.okozel.ristral.frontend.views.prehled.PrehledView;

import javax.annotation.security.PermitAll;

@Route(value = "prehled", layout = MainLayout.class)
@PermitAll
public class PrehledPresenter extends Presenter<PrehledView> implements BeforeEnterObserver {

    private final Uzivatel prihlasenyUzivatel;
    private final ZastavkaService zastavkaService;
    private final VozidloService vozidloService;

    public PrehledPresenter(PrihlasenyUzivatel prihlasenyUzivatel, ZastavkaService zastavkaService, VozidloService vozidloService) {
        this.prihlasenyUzivatel = prihlasenyUzivatel.get().get();
        this.zastavkaService = zastavkaService;
        this.vozidloService = vozidloService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        getContent().setAdminJePrihlaseny(prihlasenyUzivatel.isAtLeastAdmin());
        getContent().setHighlightText("pocetZastavek", zastavkaService.count(prihlasenyUzivatel.getSchema()));
        getContent().setHighlightText("pocetVozidel", vozidloService.count(prihlasenyUzivatel.getSchema()));
    }
}
