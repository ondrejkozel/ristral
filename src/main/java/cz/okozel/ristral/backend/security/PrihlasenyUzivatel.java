package cz.okozel.ristral.backend.security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.service.entity.UzivatelService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PrihlasenyUzivatel {

    private final UzivatelService uzivatelService;

    public PrihlasenyUzivatel(UzivatelService uzivatelService) {
        this.uzivatelService = uzivatelService;
    }

    private UserDetails getPrihlasenyUzivatell() {
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) return (UserDetails) context.getAuthentication().getPrincipal();
        return null;
    }

    public Optional<Uzivatel> getPrihlasenyUzivatel() {
        UserDetails details = getPrihlasenyUzivatell();
        if (details == null) return Optional.empty();
        return Optional.ofNullable(uzivatelService.findByUzivatelskeJmeno(details.getUsername()));
    }

    public void odhlasSe() {
        UI.getCurrent().getPage().setLocation(SecurityConfiguration.LOGOUT_URL);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
    }

    public boolean jePrihlaseny() {
        return getPrihlasenyUzivatel().isPresent();
    }

}
