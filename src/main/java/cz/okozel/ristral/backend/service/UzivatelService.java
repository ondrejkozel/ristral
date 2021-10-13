package cz.okozel.ristral.backend.service;

import cz.okozel.ristral.backend.repository.UzivatelRepository;
import cz.okozel.ristral.backend.uzivatele.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
public class UzivatelService {
    private UzivatelRepository uzivatelRepository;

    public UzivatelService(UzivatelRepository uzivatelRepository) {
        this.uzivatelRepository = uzivatelRepository;
    }

    public List<Uzivatel> getAll() {
        return uzivatelRepository.findAll();
    }

    public void add(Uzivatel uzivatel) {
        uzivatelRepository.save(uzivatel);
    }

    @PostConstruct
    public void populateTestData() {
        if (uzivatelRepository.count() == 0) {
            Uzivatel[] uzivatele = new Uzivatel[4];
            uzivatele[0] = new UzivatelOrg("Ondřej Kozel", "ondrakozel@outlook.com", "hovnokleslo");
            uzivatele[1] = new AdminOrg("administrátor", "admin@organizace.com", "admin");
            uzivatele[2] = new OsobniUzivatel("Os. uživatel", "osobak@email.com", "já tady vůbec nemám co dělat");
            uzivatele[3] = new SuperadminOrg("superadmin", "super@organizace.com", "nereknu");
            uzivatelRepository.saveAll(Arrays.asList(uzivatele));
        }
    }
}
