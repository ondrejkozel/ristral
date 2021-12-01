package cz.okozel.ristral.frontend.presenters.prehled;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.security.PrihlasenyUzivatel;
import cz.okozel.ristral.backend.service.entity.VozidloService;
import cz.okozel.ristral.backend.service.entity.ZastavkaService;
import cz.okozel.ristral.frontend.MainLayout;
import cz.okozel.ristral.frontend.presenters.Presenter;
import cz.okozel.ristral.frontend.views.prehled.PrehledView;

import javax.annotation.security.PermitAll;

@PageTitle("Přehled")
@Route(value = "prehled", layout = MainLayout.class)
@PermitAll
public class PrehledPresenter extends Presenter<PrehledView> implements BeforeEnterObserver {

    private final Uzivatel prihlasenyUzivatel;
    private final ZastavkaService zastavkaService;
    private final VozidloService vozidloService;

    public PrehledPresenter(PrihlasenyUzivatel prihlasenyUzivatel, ZastavkaService zastavkaService, VozidloService vozidloService) {
        //noinspection OptionalGetWithoutIsPresent
        this.prihlasenyUzivatel = prihlasenyUzivatel.getPrihlasenyUzivatel().get();
        this.zastavkaService = zastavkaService;
        this.vozidloService = vozidloService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        getContent().setAdminPrihlaseny(prihlasenyUzivatel.isAtLeastAdmin());
        getContent().setHighlightText("pocetZastavek", zastavkaService.count(prihlasenyUzivatel.getSchema()));
        getContent().setHighlightText("pocetVozidel", vozidloService.count(prihlasenyUzivatel.getSchema()));
    }

}
