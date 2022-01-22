package cz.okozel.ristral.backend.generator;

import com.vaadin.flow.spring.annotation.SpringComponent;
import cz.okozel.ristral.backend.entity.aktivity.Aktivita;
import cz.okozel.ristral.backend.entity.aktivity.TypAktivity;
import cz.okozel.ristral.backend.entity.lines.LineRouteCarrier;
import cz.okozel.ristral.backend.entity.lines.LineRouteLinkData;
import cz.okozel.ristral.backend.entity.routes.NamedView;
import cz.okozel.ristral.backend.entity.routes.Route;
import cz.okozel.ristral.backend.entity.uzivatele.AdminOrg;
import cz.okozel.ristral.backend.entity.uzivatele.OsobniUzivatel;
import cz.okozel.ristral.backend.entity.uzivatele.SuperadminOrg;
import cz.okozel.ristral.backend.entity.vozidla.TypVozidla;
import cz.okozel.ristral.backend.entity.vozidla.Vozidlo;
import cz.okozel.ristral.backend.entity.zastavky.RezimObsluhy;
import cz.okozel.ristral.backend.entity.zastavky.Zastavka;
import cz.okozel.ristral.backend.service.RegistratorService;
import cz.okozel.ristral.backend.service.entity.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner generateDemonstrativeData(TypVozidlaService typVozidlaService, VozidloService vozidloService, RegistratorService registratorService, AktivitaService aktivitaService, ZastavkaService zastavkaService, PeriodaNaZnameniService periodaNaZnameniService, RezimObsluhyService rezimObsluhyService, UzivatelService uzivatelService, SchemaService schemaService, LineRouteService lineRouteService) {
        return args -> {
            final OsobniUzivatel osobniUzivatel = new OsobniUzivatel("ondrejkozel", "Ondřej Kozel", "ondrakozel@outlook.com", "11111111", null);
            registratorService.zaregistrujOsobniUcetAVytvorMuNoveSchema(osobniUzivatel);
            osobniUzivatel.prevedNaUcetOrganizace(schemaService, uzivatelService);
            SuperadminOrg superAdmin = (SuperadminOrg) uzivatelService.findByUzivatelskeJmeno("ondrejkozel");
            superAdmin.zaregistrujAPridejUcet(new AdminOrg("radovyuzivatel", "Řadový Uživatel", "radovy.uzivatel@spolecnost.cz", "11111111", null), registratorService);
            if (aktivitaService.count() == 0) {
                aktivitaService.saveAll(List.of(
                        new Aktivita(TypAktivity.VYTVORENI, "Vytvoření nového uživatele", "Byl vytvořen nový uživatel Bla bla.", LocalDateTime.now().minusHours(1), superAdmin),
                        new Aktivita(TypAktivity.JINE, "Odeslání zprávy", "Byla odeslána zpráva administrátorovi.", LocalDateTime.now(), superAdmin)
                ));
            }
            if (vozidloService.count() == 0) {
                TypVozidla tramvaj = new TypVozidla("tramvaj", superAdmin.getSchema());
                TypVozidla autobus = new TypVozidla("autobus", superAdmin.getSchema());
                typVozidlaService.saveAll(List.of(tramvaj, autobus));
                vozidloService.saveAll(List.of(
                        new Vozidlo("Tatra T3", "Délka: 15104 [mm]\n" +
                                "Šířka: 2500 [mm]\n" +
                                "Hmotnost: 16000 [kg]\n" +
                                "Výkon: 4x40 [kW]\n" +
                                "Rychlost: 65 [km/hod]", 110, tramvaj, superAdmin.getSchema()),
                        new Vozidlo("Solaris Urbino 18", "Délka: 18000 [mm]\n" +
                                "Šířka: 2550 [mm]\n" +
                                "Hmotnost: 16900 [kg]\n" +
                                "Výkon: 231 [kW]\n" +
                                "Rychlost: 80 [km/hod]", 165, autobus, superAdmin.getSchema()),
                        new Vozidlo("Škoda 13T", "Délka: 31060 [mm]\n" +
                                "Šířka: 2460 [mm]\n" +
                                "Hmotnost: 41200 [kg]\n" +
                                "Výkon: 6x90 [kW]\n" +
                                "Rychlost: 70 [km/hod]", 204, tramvaj, superAdmin.getSchema())
                ));
            }
            if (zastavkaService.count() == 0) {
                final RezimObsluhy naZnameniOVikendu = new RezimObsluhy("na znamení o víkendu", "", superAdmin.getSchema());
                rezimObsluhyService.save(naZnameniOVikendu);
                Set<DayOfWeek> dny = new HashSet<>();
                dny.add(DayOfWeek.SATURDAY);
                dny.add(DayOfWeek.SUNDAY);
                RezimObsluhy.PeriodaNaZnameni periodaNaZnameni = new RezimObsluhy.PeriodaNaZnameni(LocalTime.of(18, 0), LocalTime.of(6, 0), dny, superAdmin.getSchema(), naZnameniOVikendu);
                //
                RezimObsluhy vychoziRezim = rezimObsluhyService.findVychoziRezim(naZnameniOVikendu.getSchema());
                zastavkaService.saveAll(List.of(
                        new Zastavka("Ríšova", "Točna, na které se nevytočí kloubové autobusy.", naZnameniOVikendu, superAdmin.getSchema()),
                        new Zastavka("Helenčina", "Zastávka přesunuta o 50 metrů vpřed.", naZnameniOVikendu, superAdmin.getSchema()),
                        new Zastavka("Bartolomějská", "", vychoziRezim, superAdmin.getSchema()),
                        new Zastavka("Žebětín hřbitov", "", naZnameniOVikendu, superAdmin.getSchema()),
                        new Zastavka("Křivánkovo náměstí", "", vychoziRezim, superAdmin.getSchema())
                ));
                periodaNaZnameniService.save(periodaNaZnameni);
            }
            //
            List<Zastavka> stops = zastavkaService.findAll(superAdmin.getSchema());
            Route<Zastavka, LineRouteLinkData> route = Route
                    .start(stops.get(0))
                    .through(new LineRouteLinkData(Duration.ofMinutes(10)))
                    .to(stops.get(1))
                    .through(new LineRouteLinkData(Duration.ofMinutes(5)))
                    .to(stops.get(2))
                    .finish();
            NamedView<Route<Zastavka, LineRouteLinkData>> routeNamedView = new NamedView<>(route, "linka 52", true);
            lineRouteService.save(new LineRouteCarrier(routeNamedView, superAdmin.getSchema()));
        };
    }

}