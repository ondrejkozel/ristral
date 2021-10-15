package cz.okozel.ristral.backend.service;

import cz.okozel.ristral.backend.aktivity.Aktivita;
import cz.okozel.ristral.backend.aktivity.TypAktivity;
import cz.okozel.ristral.backend.repository.AktivitaRepository;
import cz.okozel.ristral.backend.repository.SchemaRepository;
import cz.okozel.ristral.backend.repository.UzivatelRepository;
import cz.okozel.ristral.backend.schema.Schema;
import cz.okozel.ristral.backend.schema.TypSchematu;
import cz.okozel.ristral.backend.uzivatele.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UzivatelService extends GenerickaService<Uzivatel, UzivatelRepository> {
    private SchemaRepository schemaRepository;
    private AktivitaRepository aktivitaRepository;

    public UzivatelService(UzivatelRepository uzivatelRepository, SchemaRepository schemaRepository, AktivitaRepository aktivitaRepository) {
        super(uzivatelRepository);
        this.schemaRepository = schemaRepository;
        this.aktivitaRepository = aktivitaRepository;
    }

    @PostConstruct
    public void populateTestData() {
        Schema organizace = new Schema(TypSchematu.ORGANIZACE, "Dopravní podnik městské části Žebětín");
        Schema osobni = new Schema(TypSchematu.OSOBNI, "Pepa Novák");
        if (schemaRepository.count() == 0) {
            schemaRepository.saveAll(List.of(organizace, osobni));
        }
        if (count() == 0) {
            saveAll(List.of(
                    new UzivatelOrg("Ondřej Kozel", "ondrakozel@outlook.com", "hovnokleslo", organizace),
                    new AdminOrg("administrátor", "admin@organizace.com", "admin", organizace),
                    new OsobniUzivatel("Os. uživatel", "osobak@email.com", "já tady vůbec nemám co dělat", osobni),
                    new SuperadminOrg("superadmin", "super@organizace.com", "nereknu", organizace)
            ));
        }
        if (aktivitaRepository.count() == 0) {
            aktivitaRepository.saveAll(List.of(
                    new Aktivita(TypAktivity.VYTVORENI, "Vytvoření nového uživatele", "Byl vytvořen nový uživatel Bla bla.", LocalDateTime.now().minusHours(1), getAll().get(0)),
                    new Aktivita(TypAktivity.JINE, "Odeslání zprávy", "Byla odeslána zpráva administrátorovi.", LocalDateTime.now(), getAll().get(0))
            ));
        }
    }
}
