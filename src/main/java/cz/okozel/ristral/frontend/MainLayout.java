package cz.okozel.ristral.frontend;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import cz.okozel.ristral.backend.entity.schema.TypSchematu;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.security.PrihlasenyUzivatel;
import cz.okozel.ristral.frontend.customComponents.PrihlasitSeButton;
import cz.okozel.ristral.frontend.presenters.prehled.PrehledPresenter;
import cz.okozel.ristral.frontend.presenters.vitejte.VitejtePresenter;
import cz.okozel.ristral.frontend.presenters.vozidlaCrud.VozidlaCrudPresenter;
import cz.okozel.ristral.frontend.presenters.zastavkyCrud.ZastavkyCrudPresenter;
import cz.okozel.ristral.frontend.views.oRistralu.ORistraluView;

import java.util.*;

/**
 * Hlavní layout pro ostatní pohledy.
 */
@PageTitle("Main")
public class MainLayout extends AppLayout {

    private H1 titulekPohledu;

    private final PrihlasenyUzivatel prihlasenyUzivatel;
    private final AccessAnnotationChecker accessChecker;

    public MainLayout(PrihlasenyUzivatel PrihlasenyUzivatel, AccessAnnotationChecker accessChecker) {
        this.prihlasenyUzivatel = PrihlasenyUzivatel;
        this.accessChecker = accessChecker;
        //
        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        addToDrawer(createDrawerContent());
    }

    private Component createHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassName("text-secondary");
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toggle.getElement().setAttribute("aria-label", "Menu toggle");
        //
        titulekPohledu = new H1();
        titulekPohledu.addClassNames("m-0", "text-l");
        //
        Header header = new Header(toggle, titulekPohledu);
        header.addClassNames("bg-base", "border-b", "border-contrast-10", "box-border", "flex", "h-xl", "items-center", "w-full");
        return header;
    }

    private Component createDrawerContent() {
        H2 nazevAplikace = new H2("Ristral");
        nazevAplikace.addClassNames("flex", "items-center", "h-xl", "m-0", "px-m", "text-m");
        //
        //noinspection OptionalGetWithoutIsPresent
        if (prihlasenyUzivatel.jePrihlaseny() && prihlasenyUzivatel.getPrihlasenyUzivatel().get().getSchema().getTypSchematu() == TypSchematu.ORGANIZACE) {
            nazevAplikace.setText(prihlasenyUzivatel.getPrihlasenyUzivatel().get().getSchema().getNazev());
        }
        //
        com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section(nazevAplikace, createNavigation(), createFooter());
        section.addClassNames("flex", "flex-col", "items-stretch", "max-h-full", "min-h-full");
        return section;
    }

    private Nav createNavigation() {
        Nav nav = new Nav();
        nav.addClassNames("border-b", "border-contrast-10", "flex-grow", "overflow-auto");
        nav.getElement().setAttribute("aria-labelledby", "views");

        //toto pak jde vkládat mezi jednotlivé položky menu
//        H3 views = new H3("Pohledy");
//        views.addClassNames("flex", "h-m", "items-center", "mx-m", "my-0", "text-s", "text-tertiary");
//        views.setId("views");

        // pro lepší přístupnost
        UnorderedList list = new UnorderedList();
        list.addClassNames("list-none", "m-0", "p-0");
        nav.add(list);
        //
        for (RouterLink link : createLinks()) {
            ListItem item = new ListItem(link);
            list.add(item);
        }
        return nav;
    }

    private List<RouterLink> createLinks() {
        PolozkaMenu[] menuItems = new PolozkaMenu[]{
                new PolozkaMenu("Vítejte", Ikona.BUS, VitejtePresenter.class),
                new PolozkaMenu("Přehled", Ikona.GRAF, PrehledPresenter.class),
                new PolozkaMenu("Zastávky", Ikona.ZASTAVKA, ZastavkyCrudPresenter.class),
                new PolozkaMenu("Vozidla", Ikona.BUS, VozidlaCrudPresenter.class),
                new PolozkaMenu("O Ristralu", Ikona.INFO, ORistraluView.class)
        };
        //
        Set<Integer> ignorovaneIndexy = new HashSet<>();
        if (prihlasenyUzivatel.jePrihlaseny()) ignorovaneIndexy.add(0);
        //
        List<RouterLink> links = new ArrayList<>();
        for (int i = 0; i < menuItems.length; i++) {
            PolozkaMenu polozkaMenu = menuItems[i];
            if (accessChecker.hasAccess(polozkaMenu.getView()) && !ignorovaneIndexy.contains(i)) {
                links.add(createLink(polozkaMenu));
            }
        }
        return links;
    }

    private static RouterLink createLink(PolozkaMenu polozkaMenu) {
        RouterLink link = new RouterLink();
        link.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");
        link.setRoute(polozkaMenu.getView());
        //
        Span ikona = polozkaMenu.getIkona().getSpan();
        ikona.addClassNames("me-s", "text-l");
        //
        Span text = new Span(polozkaMenu.getText());
        text.addClassNames("font-medium", "text-s");
        //
        link.add(ikona, text);
        return link;
    }

    private Footer createFooter() {
        Footer footer = new Footer();
        footer.addClassNames("flex", "items-center", "my-s", "px-m", "py-xs");
        //
        Optional<Uzivatel> prihlasenyUzivatel = this.prihlasenyUzivatel.getPrihlasenyUzivatel();
        if (prihlasenyUzivatel.isPresent()) {
            Uzivatel uzivatel = prihlasenyUzivatel.get();
            //
            Avatar avatar = new Avatar(uzivatel.getJmeno());
            avatar.addClassNames("me-xs");
            //
            Button triTeckyButton = new Button(VaadinIcon.ELLIPSIS_DOTS_V.create());
            triTeckyButton.addClassNames("px-xs");
            //
            ContextMenu uzivatelMenu = new ContextMenu(triTeckyButton);
            uzivatelMenu.setOpenOnClick(true);
            uzivatelMenu.addItem("Odhlásit se", e -> this.prihlasenyUzivatel.odhlasSe());
            //
            Span jmeno = new Span(uzivatel.getJmeno());
            jmeno.addClassNames("font-medium", "text-s", "text-secondary", "flex-auto");
            //
            footer.add(avatar, jmeno, triTeckyButton);
        }
        else footer.add(PrihlasitSeButton.getPrihlasitSeButtonRouterLink());
        return footer;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        titulekPohledu.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

    public static class PolozkaMenu {

        private final String text;
        private final Ikona ikona;
        private final Class<? extends Component> view;

        public PolozkaMenu(String text, Ikona ikona, Class<? extends Component> view) {
            this.text = text;
            this.ikona = ikona;
            this.view = view;
        }

        public String getText() {
            return text;
        }

        public Ikona getIkona() {
            return ikona;
        }

        public Class<? extends Component> getView() {
            return view;
        }

    }

}
