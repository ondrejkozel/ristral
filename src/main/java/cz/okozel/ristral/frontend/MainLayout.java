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
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.security.PrihlasenyUzivatel;
import cz.okozel.ristral.frontend.views.login.LoginView;
import cz.okozel.ristral.frontend.views.prehled.PrehledView;
import cz.okozel.ristral.frontend.views.vitejte.VitejteView;

import java.util.*;

/**
 * Hlavní layout pro ostatní pohledy.
 */
@PageTitle("Main")
public class MainLayout extends AppLayout {

    public static class MenuItemInfo {

        private String text;
        private Ikona icon;
        private Class<? extends Component> view;

        public MenuItemInfo(String text, Ikona icon, Class<? extends Component> view) {
            this.text = text;
            this.icon = icon;
            this.view = view;
        }

        public String getText() {
            return text;
        }

        public Ikona getIcon() {
            return icon;
        }

        public Class<? extends Component> getView() {
            return view;
        }

    }

    private H1 titulekPohledu;

    private PrihlasenyUzivatel PrihlasenyUzivatel;
    private AccessAnnotationChecker accessChecker;

    public MainLayout(PrihlasenyUzivatel PrihlasenyUzivatel, AccessAnnotationChecker accessChecker) {
        this.PrihlasenyUzivatel = PrihlasenyUzivatel;
        this.accessChecker = accessChecker;

        setPrimarySection(Section.DRAWER);
        addToNavbar(true, createHeaderContent());
        addToDrawer(createDrawerContent());
    }

    private Component createHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassName("text-secondary");
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        titulekPohledu = new H1();
        titulekPohledu.addClassNames("m-0", "text-l");

        Header header = new Header(toggle, titulekPohledu);
        header.addClassNames("bg-base", "border-b", "border-contrast-10", "box-border", "flex", "h-xl", "items-center",
                "w-full");
        return header;
    }

    private Component createDrawerContent() {
        H2 appName = new H2("Ristral");
        appName.addClassNames("flex", "items-center", "h-xl", "m-0", "px-m", "text-m");

        com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section(appName,
                createNavigation(), createFooter());
        section.addClassNames("flex", "flex-col", "items-stretch", "max-h-full", "min-h-full");
        return section;
    }

    private Nav createNavigation() {
        Nav nav = new Nav();
        nav.addClassNames("border-b", "border-contrast-10", "flex-grow", "overflow-auto");
        nav.getElement().setAttribute("aria-labelledby", "views");

        H3 views = new H3("Views");
        views.addClassNames("flex", "h-m", "items-center", "mx-m", "my-0", "text-s", "text-tertiary");
        views.setId("views");

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames("list-none", "m-0", "p-0");
        nav.add(list);

        for (RouterLink link : createLinks()) {
            ListItem item = new ListItem(link);
            list.add(item);
        }
        return nav;
    }

    private List<RouterLink> createLinks() {
        MenuItemInfo[] menuItems = new MenuItemInfo[]{
                new MenuItemInfo("Vítejte", Ikona.VITEJTE, VitejtePresenter.class),
                new MenuItemInfo("Přehled", Ikona.PREHLED, PrehledPresenter.class)
        };
        //
        Set<Integer> ignorovaneIndexy = new HashSet<>();
        if (PrihlasenyUzivatel.get().isPresent()) ignorovaneIndexy.add(0);
        //
        List<RouterLink> links = new ArrayList<>();
        for (int i = 0; i < menuItems.length; i++) {
            MenuItemInfo menuItemInfo = menuItems[i];
            if (accessChecker.hasAccess(menuItemInfo.getView()) && !ignorovaneIndexy.contains(i)) {
                links.add(createLink(menuItemInfo));
            }
        }
        return links;
    }

    private static RouterLink createLink(MenuItemInfo menuItemInfo) {
        RouterLink link = new RouterLink();
        link.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");
        link.setRoute(menuItemInfo.getView());

        Span icon = menuItemInfo.getIcon().get();
        icon.addClassNames("me-s", "text-l");

        Span text = new Span(menuItemInfo.getText());
        text.addClassNames("font-medium", "text-s");

        link.add(icon, text);
        return link;
    }

    private Footer createFooter() {
        Footer footer = new Footer();
        footer.addClassNames("flex", "items-center", "my-s", "px-m", "py-xs");
        //
        Optional<Uzivatel> prihlasenyUzivatel = PrihlasenyUzivatel.get();
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
            uzivatelMenu.addItem("Odhlásit se", e -> PrihlasenyUzivatel.odhlasSe());
            //
            Span name = new Span(uzivatel.getJmeno());
            name.addClassNames("font-medium", "text-s", "text-secondary", "flex-auto");
            //
            footer.add(avatar, name, triTeckyButton);
        } else {
            Button prihlasitSeButton = new Button("Přihlásit se");
            RouterLink routerLink = new RouterLink();
            routerLink.setRoute(LoginView.class);
            routerLink.add(prihlasitSeButton);
            footer.add(routerLink);
        }

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
}
