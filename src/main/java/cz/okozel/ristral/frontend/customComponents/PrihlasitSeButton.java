package cz.okozel.ristral.frontend.customComponents;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.RouterLink;
import cz.okozel.ristral.frontend.views.login.LoginView;

public class PrihlasitSeButton extends Button {

    public static RouterLink getPrihlasitSeButtonRouterLink() {
        RouterLink routerLink = new RouterLink();
        routerLink.setRoute(LoginView.class);
        routerLink.add(new PrihlasitSeButton());
        return routerLink;
    }

    public PrihlasitSeButton() {
        super("Přihlásit se");
    }
    
}
