package cz.okozel.ristral.backend.service;

import cz.okozel.ristral.backend.repository.UzivatelOrgRepository;
import cz.okozel.ristral.backend.uzivatele.UzivatelOrg;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UzivatelOrgService {
    private UzivatelOrgRepository uzivatelOrgRepository;

    public UzivatelOrgService(UzivatelOrgRepository uzivatelRepository) {
        this.uzivatelOrgRepository = uzivatelRepository;
    }

    public List<UzivatelOrg> getAll() {
        return uzivatelOrgRepository.findAll();
    }

    public void save(UzivatelOrg uzivatelOrg) {
        uzivatelOrgRepository.save(uzivatelOrg);
    }
}
