package cz.okozel.ristral.backend.security;

import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.service.UzivatelService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PrihlasenyUzivatel {

    private final UzivatelService uzivatelService;

    public PrihlasenyUzivatel(UzivatelService uzivatelService) {
        this.uzivatelService = uzivatelService;
    }

    private UserDetails getPrihlasenyUzivatel() {
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) return (UserDetails) context.getAuthentication().getPrincipal();
        return null;
    }

    public Optional<Uzivatel> get() {
        UserDetails details = getPrihlasenyUzivatel();
        if (details == null) return Optional.empty();
        return Optional.of(uzivatelService.findByUzivatelskeJmeno(details.getUsername()));
    }

}
