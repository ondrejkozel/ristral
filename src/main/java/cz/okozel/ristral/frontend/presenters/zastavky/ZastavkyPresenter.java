package cz.okozel.ristral.frontend.presenters.zastavky;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cz.okozel.ristral.backend.entity.zastavky.Zastavka;
import cz.okozel.ristral.backend.service.ZastavkaService;
import cz.okozel.ristral.frontend.MainLayout;
import cz.okozel.ristral.frontend.presenters.crud.GenericCrudPresenter;
import cz.okozel.ristral.frontend.views.zastavky.ZastavkyView;

import javax.annotation.security.PermitAll;

@PageTitle("Zastávky")
@Route(value = "zastavky", layout = MainLayout.class)
@PermitAll
public class ZastavkyPresenter extends GenericCrudPresenter<Zastavka, ZastavkyView> {

    public ZastavkyPresenter(ZastavkaService zastavkaService) {
        super(Zastavka.class, new ZastavkyDataProvider(zastavkaService));
    }

    // TODO: 17.11.2021 Proč se grid neplní zastávkami?

}
