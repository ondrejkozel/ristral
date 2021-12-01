package cz.okozel.ristral.frontend.customComponents;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.RouterLink;
import cz.okozel.ristral.frontend.presenters.register.RegisterPresenter;

public class ZaregistrovatSeButton extends Button {

    public static RouterLink getZaregistrovatSeButtonRouterLink() {
        RouterLink routerLink = new RouterLink();
        routerLink.setRoute(RegisterPresenter.class);
        routerLink.add(new ZaregistrovatSeButton());
        return routerLink;
    }

    public ZaregistrovatSeButton() {
        super("Zaregistrovat se");
    }
    
}
