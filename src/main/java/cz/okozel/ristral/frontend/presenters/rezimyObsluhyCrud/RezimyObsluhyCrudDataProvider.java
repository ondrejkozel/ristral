package cz.okozel.ristral.frontend.presenters.rezimyObsluhyCrud;

import com.vaadin.flow.component.notification.Notification;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.zastavky.RezimObsluhy;
import cz.okozel.ristral.backend.entity.zastavky.Zastavka;
import cz.okozel.ristral.backend.service.entity.PeriodaNaZnameniService;
import cz.okozel.ristral.backend.service.entity.RezimObsluhyService;
import cz.okozel.ristral.backend.service.entity.ZastavkaService;
import cz.okozel.ristral.frontend.presenters.crud.GenericDataProvider;

import java.util.List;

public class RezimyObsluhyCrudDataProvider extends GenericDataProvider<RezimObsluhy, RezimObsluhyService> {

    private final ZastavkaService zastavkaService;
    private final PeriodaNaZnameniService periodaNaZnameniService;

    public RezimyObsluhyCrudDataProvider(RezimObsluhyService rezimObsluhyService, Schema schema, ZastavkaService zastavkaService, PeriodaNaZnameniService periodaNaZnameniService) {
        super(rezimObsluhyService, RezimObsluhy.class, schema);
        this.zastavkaService = zastavkaService;
        this.periodaNaZnameniService = periodaNaZnameniService;
    }

    @Override
    public void smaz(RezimObsluhy objekt) {
        if (objekt.isUpravitelny()) {
            List<Zastavka> zastavkySTimtoRezimemObsluhy = zastavkaService.findAllByRezimObsluhy(schema, objekt);
            if (!zastavkySTimtoRezimemObsluhy.isEmpty()) {
                RezimObsluhy vychoziRezim = service.findVychoziRezim(schema);
                zastavkySTimtoRezimemObsluhy.forEach(zastavka -> zastavka.setRezimObsluhy(vychoziRezim));
                zastavkaService.saveAll(zastavkySTimtoRezimemObsluhy);
                periodaNaZnameniService.findAllByRezimObsluhy(objekt).forEach(periodaNaZnameniService::delete);
                Notification.show(String.format("Tomuto počtu zastávek byl nastaven režim obsluhy na výchozí: %d.", zastavkySTimtoRezimemObsluhy.size()));
            }
            super.smaz(objekt);
        }
        else Notification.show("Výchozí režim obsluhy nejde odstranit.");
    }

}
