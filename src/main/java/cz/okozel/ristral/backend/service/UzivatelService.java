package cz.okozel.ristral.backend.service;

import cz.okozel.ristral.backend.entity.aktivity.Aktivita;
import cz.okozel.ristral.backend.entity.aktivity.TypAktivity;
import cz.okozel.ristral.backend.entity.vozidla.TypVozidla;
import cz.okozel.ristral.backend.entity.vozidla.Vozidlo;
import cz.okozel.ristral.backend.repository.*;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.schema.TypSchematu;
import cz.okozel.ristral.backend.service.generic.GenerickaSchemaService;
import cz.okozel.ristral.backend.entity.uzivatele.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UzivatelService extends GenerickaSchemaService<Uzivatel, UzivatelRepository> {

    @Autowired
    private SchemaRepository schemaRepository;
    @Autowired
    private AktivitaRepository aktivitaRepository;
    @Autowired
    private VozidloRepository vozidloRepository;
    @Autowired
    private TypVozidlaRepository typVozidlaRepository;

    public UzivatelService(UzivatelRepository uzivatelRepository) {
        super(uzivatelRepository);
    }

    @PostConstruct
    public void populateTestData() {
        Schema organizace = new Schema(TypSchematu.ORGANIZACE, "Dopravní podnik městské části Žebětín");
        Schema osobni = new Schema(TypSchematu.OSOBNI, "Pepa Novák");
        if (schemaRepository.count() == 0) schemaRepository.saveAll(List.of(organizace, osobni));
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
                    new Aktivita(TypAktivity.VYTVORENI, "Vytvoření nového uživatele", "Byl vytvořen nový uživatel Bla bla.", LocalDateTime.now().minusHours(1), findAll().get(0)),
                    new Aktivita(TypAktivity.JINE, "Odeslání zprávy", "Byla odeslána zpráva administrátorovi.", LocalDateTime.now(), findAll().get(0))
            ));
        }
        if (vozidloRepository.count() == 0) {
            TypVozidla tramvaj = new TypVozidla("tramvaj", organizace);
            TypVozidla autobus = new TypVozidla("autobus", organizace);
            typVozidlaRepository.saveAll(List.of(tramvaj, autobus));
            vozidloRepository.saveAll(List.of(
                    new Vozidlo("Tatra T3", "Délka: 15104 [mm]\n" +
                            "Šířka: 2500 [mm]\n" +
                            "Hmotnost: 16000 [kg]\n" +
                            "Výkon: 4x40 [kW]\n" +
                            "Rychlost: 65 [km/hod]", 110, tramvaj, organizace),
                    new Vozidlo("Solaris Urbino 18", "Délka: 18000 [mm]\n" +
                            "Šířka: 2550 [mm]\n" +
                            "Hmotnost: 16900 [kg]\n" +
                            "Výkon: 231 [kW]\n" +
                            "Rychlost: 80 [km/hod]", 165, autobus, organizace)
            ));
        }
    }
}
