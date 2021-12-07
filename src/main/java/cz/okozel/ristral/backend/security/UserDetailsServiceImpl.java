package cz.okozel.ristral.backend.security;

import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.service.entity.UzivatelService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UzivatelService uzivatelService;

    public UserDetailsServiceImpl(UzivatelService uzivatelService) {
        this.uzivatelService = uzivatelService;
    }

    @Override
    public UserDetails loadUserByUsername(String uzivatelskeJmeno) throws UsernameNotFoundException {
        Uzivatel uzivatel = uzivatelService.findByUzivatelskeJmeno(uzivatelskeJmeno);
        if (uzivatel == null) {
            throw new UsernameNotFoundException("Žádný uživatel s tímto uživatelským jménem: " + uzivatelskeJmeno);
        } else {
            return new org.springframework.security.core.userdetails.User(uzivatel.getUzivatelskeJmeno(), uzivatel.getHeslo(),
                    getAuthorities(uzivatel));
        }
    }

    private static List<GrantedAuthority> getAuthorities(Uzivatel uzivatel) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + uzivatel.getRole().getKratkyNazev()));
    }

}
