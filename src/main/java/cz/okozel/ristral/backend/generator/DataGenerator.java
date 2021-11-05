package cz.okozel.ristral.backend.generator;

import com.vaadin.flow.spring.annotation.SpringComponent;
import cz.okozel.ristral.backend.entity.aktivity.Aktivita;
import cz.okozel.ristral.backend.entity.aktivity.TypAktivity;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.schema.TypSchematu;
import cz.okozel.ristral.backend.entity.uzivatele.AdminOrg;
import cz.okozel.ristral.backend.entity.uzivatele.OsobniUzivatel;
import cz.okozel.ristral.backend.entity.uzivatele.SuperadminOrg;
import cz.okozel.ristral.backend.entity.uzivatele.UzivatelOrg;
import cz.okozel.ristral.backend.entity.vozidla.TypVozidla;
import cz.okozel.ristral.backend.entity.vozidla.Vozidlo;
import cz.okozel.ristral.backend.entity.zastavky.RezimObsluhy;
import cz.okozel.ristral.backend.entity.zastavky.Zastavka;
import cz.okozel.ristral.backend.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner generateDemonstrativeData(PasswordEncoder passwordEncoder, SchemaRepository schemaRepository, UzivatelRepository uzivatelService, AktivitaRepository aktivitaRepository, VozidloRepository vozidloRepository, ZastavkaRepository zastavkaRepository) {
        return args -> {
            Schema organizace = new Schema(TypSchematu.ORGANIZACE, "Dopravní podnik městské části Žebětín");
            Schema osobni = new Schema(TypSchematu.OSOBNI, "Pepa Novák");
            if (schemaRepository.count() == 0) schemaRepository.saveAll(List.of(organizace, osobni));
            final UzivatelOrg uzivatel1 = new UzivatelOrg("ondrejkozel", "Ondřej Kozel", "ondrakozel@outlook.com", passwordEncoder.encode("1234"), organizace);
            if (uzivatelService.count() == 0) {
                uzivatelService.saveAll(List.of(
                        uzivatel1,
                        new AdminOrg("admin", "administrátor", "admin@organizace.com", "admin", organizace),
                        new OsobniUzivatel("osobak", "Os. uživatel", "osobak@email.com", "já tady vůbec nemám co dělat", osobni),
                        new SuperadminOrg("superadmin", "superadmin", "super@organizace.com", "nereknu", organizace)
                ));
            }
            if (aktivitaRepository.count() == 0) {
                aktivitaRepository.saveAll(List.of(
                        new Aktivita(TypAktivity.VYTVORENI, "Vytvoření nového uživatele", "Byl vytvořen nový uživatel Bla bla.", LocalDateTime.now().minusHours(1), uzivatel1),
                        new Aktivita(TypAktivity.JINE, "Odeslání zprávy", "Byla odeslána zpráva administrátorovi.", LocalDateTime.now(), uzivatel1)
                ));
            }
            if (vozidloRepository.count() == 0) {
                TypVozidla tramvaj = new TypVozidla("tramvaj", organizace);
                TypVozidla autobus = new TypVozidla("autobus", organizace);
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
                                "Rychlost: 80 [km/hod]", 165, autobus, organizace),
                        new Vozidlo("Škoda 13T", "Délka: 31060 [mm]\n" +
                                "Šířka: 2460 [mm]\n" +
                                "Hmotnost: 41200 [kg]\n" +
                                "Výkon: 6x90 [kW]\n" +
                                "Rychlost: 70 [km/hod]", 204, tramvaj, organizace)
                ));
            }
            if (zastavkaRepository.count() == 0) {
                final RezimObsluhy naZnameniOVikendu = new RezimObsluhy("na znamení o víkendu", "", organizace);
                Set<DayOfWeek> dny = new HashSet<>();
                dny.add(DayOfWeek.SATURDAY);
                dny.add(DayOfWeek.SUNDAY);
                naZnameniOVikendu.addZnameni(new RezimObsluhy.PeriodaNaZnameni(LocalTime.of(18, 0), LocalTime.of(6,0), dny, organizace));
                zastavkaRepository.saveAll(List.of(
                        new Zastavka("Ríšova", "", naZnameniOVikendu, organizace)
                ));
            }
        };
    }

}