package cz.okozel.ristral.backend.generator;

import com.vaadin.flow.spring.annotation.SpringComponent;
import cz.okozel.ristral.backend.entity.aktivity.Aktivita;
import cz.okozel.ristral.backend.entity.aktivity.TypAktivity;
import cz.okozel.ristral.backend.entity.uzivatele.UzivatelOrg;
import cz.okozel.ristral.backend.entity.vozidla.TypVozidla;
import cz.okozel.ristral.backend.entity.vozidla.Vozidlo;
import cz.okozel.ristral.backend.entity.zastavky.RezimObsluhy;
import cz.okozel.ristral.backend.entity.zastavky.Zastavka;
import cz.okozel.ristral.backend.repository.*;
import cz.okozel.ristral.backend.service.RegistratorService;
import cz.okozel.ristral.backend.service.entity.RezimObsluhyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner generateDemonstrativeData(TypVozidlaRepository typVozidlaRepository, VozidloRepository vozidloRepository, RegistratorService registratorService, AktivitaRepository aktivitaRepository, ZastavkaRepository zastavkaRepository, PeriodaNaZnameniRepository periodaNaZnameniRepository, RezimObsluhyService rezimObsluhyService) {
        return args -> {
            final UzivatelOrg uzivatel1 = new UzivatelOrg("ondrejkozel", "Ondřej Kozel", "ondrakozel@outlook.com", "11111111", null);
            registratorService.zaregistruj(uzivatel1);
            if (aktivitaRepository.count() == 0) {
                aktivitaRepository.saveAll(List.of(
                        new Aktivita(TypAktivity.VYTVORENI, "Vytvoření nového uživatele", "Byl vytvořen nový uživatel Bla bla.", LocalDateTime.now().minusHours(1), uzivatel1),
                        new Aktivita(TypAktivity.JINE, "Odeslání zprávy", "Byla odeslána zpráva administrátorovi.", LocalDateTime.now(), uzivatel1)
                ));
            }
            if (vozidloRepository.count() == 0) {
                TypVozidla tramvaj = new TypVozidla("tramvaj", uzivatel1.getSchema());
                TypVozidla autobus = new TypVozidla("autobus", uzivatel1.getSchema());
                typVozidlaRepository.saveAll(List.of(tramvaj, autobus));
                vozidloRepository.saveAll(List.of(
                        new Vozidlo("Tatra T3", "Délka: 15104 [mm]\n" +
                                "Šířka: 2500 [mm]\n" +
                                "Hmotnost: 16000 [kg]\n" +
                                "Výkon: 4x40 [kW]\n" +
                                "Rychlost: 65 [km/hod]", 110, tramvaj, uzivatel1.getSchema()),
                        new Vozidlo("Solaris Urbino 18", "Délka: 18000 [mm]\n" +
                                "Šířka: 2550 [mm]\n" +
                                "Hmotnost: 16900 [kg]\n" +
                                "Výkon: 231 [kW]\n" +
                                "Rychlost: 80 [km/hod]", 165, autobus, uzivatel1.getSchema()),
                        new Vozidlo("Škoda 13T", "Délka: 31060 [mm]\n" +
                                "Šířka: 2460 [mm]\n" +
                                "Hmotnost: 41200 [kg]\n" +
                                "Výkon: 6x90 [kW]\n" +
                                "Rychlost: 70 [km/hod]", 204, tramvaj, uzivatel1.getSchema())
                ));
            }
            if (zastavkaRepository.count() == 0) {
                final RezimObsluhy naZnameniOVikendu = new RezimObsluhy("na znamení o víkendu", "", uzivatel1.getSchema());
                rezimObsluhyService.save(naZnameniOVikendu);
                Set<DayOfWeek> dny = new HashSet<>();
                dny.add(DayOfWeek.SATURDAY);
                dny.add(DayOfWeek.SUNDAY);
                RezimObsluhy.PeriodaNaZnameni periodaNaZnameni = new RezimObsluhy.PeriodaNaZnameni(LocalTime.of(18, 0), LocalTime.of(6, 0), dny, uzivatel1.getSchema(), naZnameniOVikendu);
                //
                RezimObsluhy vychoziRezim = rezimObsluhyService.findVychoziRezim(naZnameniOVikendu.getSchema());
                zastavkaRepository.saveAll(List.of(
                        new Zastavka("Ríšova", "Točna, na které se nevytočí kloubové autobusy.", naZnameniOVikendu, uzivatel1.getSchema()),
                        new Zastavka("Helenčina", "Zastávka přesunuta o 50 metrů vpřed.", naZnameniOVikendu, uzivatel1.getSchema()),
                        new Zastavka("Bartolomějská", "", vychoziRezim, uzivatel1.getSchema()),
                        new Zastavka("Žebětín hřbitov", "", naZnameniOVikendu, uzivatel1.getSchema()),
                        new Zastavka("Křivánkovo náměstí", "", vychoziRezim, uzivatel1.getSchema())
                ));
                periodaNaZnameniRepository.save(periodaNaZnameni);
            }
        };
    }

}