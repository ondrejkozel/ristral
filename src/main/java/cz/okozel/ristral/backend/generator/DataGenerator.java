package cz.okozel.ristral.backend.generator;

import com.vaadin.flow.spring.annotation.SpringComponent;
import cz.okozel.ristral.backend.entity.aktivity.Aktivita;
import cz.okozel.ristral.backend.entity.aktivity.TypAktivity;
import cz.okozel.ristral.backend.entity.lines.Line;
import cz.okozel.ristral.backend.entity.lines.LineRouteCarrier;
import cz.okozel.ristral.backend.entity.lines.LineRouteLinkData;
import cz.okozel.ristral.backend.entity.routes.NamedView;
import cz.okozel.ristral.backend.entity.routes.Route;
import cz.okozel.ristral.backend.entity.trips.Trip;
import cz.okozel.ristral.backend.entity.trips.TripRouteCarrier;
import cz.okozel.ristral.backend.entity.trips.TripRouteLinkData;
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
    public CommandLineRunner generateDemonstrativeData(TypVozidlaService typVozidlaService, VozidloService vozidloService, RegistratorService registratorService, AktivitaService aktivitaService, ZastavkaService zastavkaService, PeriodaNaZnameniService periodaNaZnameniService, RezimObsluhyService rezimObsluhyService, UzivatelService uzivatelService, SchemaService schemaService, LineRouteService lineRouteService, LineService lineService, TripService tripService, TripRouteService tripRouteService) {
        return args -> {
            final OsobniUzivatel osobniUzivatel = new OsobniUzivatel("ondrejkozel", "Ond??ej Kozel", "ondrakozel@outlook.com", "11111111", null);
            registratorService.zaregistrujOsobniUcetAVytvorMuNoveSchema(osobniUzivatel);
            osobniUzivatel.prevedNaUcetOrganizace(schemaService, uzivatelService);
            SuperadminOrg superAdmin = (SuperadminOrg) uzivatelService.findByUzivatelskeJmeno("ondrejkozel");
            superAdmin.zaregistrujAPridejUcet(new AdminOrg("radovyuzivatel", "??adov?? U??ivatel", "radovy.uzivatel@spolecnost.cz", "11111111", null), registratorService);
            if (aktivitaService.count() == 0) {
                aktivitaService.saveAll(List.of(
                        new Aktivita(TypAktivity.VYTVORENI, "Vytvo??en?? nov??ho u??ivatele", "Byl vytvo??en nov?? u??ivatel Bla bla.", LocalDateTime.now().minusHours(1), superAdmin),
                        new Aktivita(TypAktivity.JINE, "Odesl??n?? zpr??vy", "Byla odesl??na zpr??va administr??torovi.", LocalDateTime.now(), superAdmin)
                ));
            }
            TypVozidla autobus = new TypVozidla("autobus", superAdmin.getSchema());
            if (vozidloService.count() == 0) {
                TypVozidla tramvaj = new TypVozidla("tramvaj", superAdmin.getSchema());
                typVozidlaService.saveAll(List.of(tramvaj, autobus));
                vozidloService.saveAll(List.of(
                        new Vozidlo("Tatra T3", "D??lka: 15104 [mm]\n" +
                                "??????ka: 2500 [mm]\n" +
                                "Hmotnost: 16000 [kg]\n" +
                                "V??kon: 4x40 [kW]\n" +
                                "Rychlost: 65 [km/hod]", 110, tramvaj, superAdmin.getSchema()),
                        new Vozidlo("Solaris Urbino 18", "D??lka: 18000 [mm]\n" +
                                "??????ka: 2550 [mm]\n" +
                                "Hmotnost: 16900 [kg]\n" +
                                "V??kon: 231 [kW]\n" +
                                "Rychlost: 80 [km/hod]", 165, autobus, superAdmin.getSchema()),
                        new Vozidlo("??koda 13T", "D??lka: 31060 [mm]\n" +
                                "??????ka: 2460 [mm]\n" +
                                "Hmotnost: 41200 [kg]\n" +
                                "V??kon: 6x90 [kW]\n" +
                                "Rychlost: 70 [km/hod]", 204, tramvaj, superAdmin.getSchema())
                ));
            }
            if (zastavkaService.count() == 0) {
                final RezimObsluhy naZnameniOVikendu = new RezimObsluhy("na znamen?? o v??kendu", "", superAdmin.getSchema());
                rezimObsluhyService.save(naZnameniOVikendu);
                Set<DayOfWeek> dny = new HashSet<>();
                dny.add(DayOfWeek.SATURDAY);
                dny.add(DayOfWeek.SUNDAY);
                RezimObsluhy.PeriodaNaZnameni periodaNaZnameni = new RezimObsluhy.PeriodaNaZnameni(LocalTime.of(18, 0), LocalTime.of(6, 0), dny, superAdmin.getSchema(), naZnameniOVikendu);
                //
                RezimObsluhy vychoziRezim = rezimObsluhyService.findVychoziRezim(naZnameniOVikendu.getSchema());
                zastavkaService.saveAll(List.of(
                        new Zastavka("R????ova", "To??na, na kter?? se nevyto???? kloubov?? autobusy.", naZnameniOVikendu, superAdmin.getSchema()),
                        new Zastavka("Helen??ina", "Zast??vka p??esunuta o 50 metr?? vp??ed.", naZnameniOVikendu, superAdmin.getSchema()),
                        new Zastavka("Bartolom??jsk??", "", vychoziRezim, superAdmin.getSchema()),
                        new Zastavka("??eb??t??n h??bitov", "", naZnameniOVikendu, superAdmin.getSchema()),
                        new Zastavka("K??iv??nkovo n??m??st??", "", vychoziRezim, superAdmin.getSchema())
                ));
                periodaNaZnameniService.save(periodaNaZnameni);
            }
            //
            List<Zastavka> stops = zastavkaService.findAll(superAdmin.getSchema());
            Route<Zastavka, LineRouteLinkData> lineRoute = Route
                    .start(stops.get(0))
                    .through(new LineRouteLinkData(Duration.ofMinutes(1)))
                    .to(stops.get(1))
                    .through(new LineRouteLinkData(Duration.ofMinutes(2)))
                    .to(stops.get(2))
                    .through(new LineRouteLinkData(Duration.ofMinutes(3)))
                    .to(stops.get(3))
                    .through(new LineRouteLinkData(Duration.ofMinutes(4)))
                    .to(stops.get(4))
                    .finish();
            NamedView<Route<Zastavka, LineRouteLinkData>> routeNamedView = new NamedView<>(lineRoute, "v??choz??", true);
            Line line = new Line("52", "prvn?? linka", autobus, superAdmin.getSchema());
            lineService.save(line);
            lineRouteService.save(new LineRouteCarrier(routeNamedView, line));
            //
            Route<String, TripRouteLinkData> tripRoute = LineRouteCarrier.buildTripRoute(lineRoute, LocalDateTime.now());
            Trip trip = new Trip(line, vozidloService.findAll().get(0), uzivatelService.findByUzivatelskeJmeno("ondrejkozel"), "popis");
            tripService.save(trip);
            tripRouteService.save(new TripRouteCarrier(tripRoute, trip));
        };
    }

}