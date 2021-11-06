package cz.okozel.ristral.frontend.views.vitejte;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import cz.okozel.ristral.backend.security.PrihlasenyUzivatel;
import cz.okozel.ristral.frontend.MainLayout;
import cz.okozel.ristral.frontend.views.prehled.PrehledView;

@PageTitle("Vítejte")
@Route(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class VitejteView extends VerticalLayout implements BeforeEnterObserver {

    private PrihlasenyUzivatel prihlasenyUzivatel;

    public VitejteView(PrihlasenyUzivatel prihlasenyUzivatel) {
        this.prihlasenyUzivatel = prihlasenyUzivatel;
        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        add(new H2("This place intentionally left empty"));
        add(new Paragraph("It’s a place where you can grow your own UI 🤗"));

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (prihlasenyUzivatel.get().isPresent()) event.forwardTo(PrehledView.class);
    }
}
