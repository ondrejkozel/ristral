package cz.okozel.ristral.frontend.presenters.vitejte;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import cz.okozel.ristral.backend.security.PrihlasenyUzivatel;
import cz.okozel.ristral.frontend.MainLayout;
import cz.okozel.ristral.frontend.presenters.Presenter;
import cz.okozel.ristral.frontend.presenters.prehled.PrehledPresenter;
import cz.okozel.ristral.frontend.views.vitejte.VitejteView;

@Route(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class VitejtePresenter extends Presenter<VitejteView> implements BeforeEnterObserver {

    private PrihlasenyUzivatel prihlasenyUzivatel;

    public VitejtePresenter(PrihlasenyUzivatel prihlasenyUzivatel) {
        this.prihlasenyUzivatel = prihlasenyUzivatel;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (prihlasenyUzivatel.jePrihlaseny()) event.forwardTo(PrehledPresenter.class);
    }

}
