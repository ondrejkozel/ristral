package cz.okozel.ristral.backend.service.entity;

import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.trips.TripRouteCarrier;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.repository.TripRouteCarrierRepository;
import cz.okozel.ristral.backend.service.entity.generic.GenericSchemaService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TripRouteService extends GenericSchemaService<TripRouteCarrier, TripRouteCarrierRepository> {
    public TripRouteService(TripRouteCarrierRepository hlavniRepositar) {
        super(hlavniRepositar);
    }

    public List<TripRouteCarrier> getSoonestTripsFor(Uzivatel user) {
        return hlavniRepositar.findAllByAssociatedTrip_UserEqualsAndTimeOfArrivalGreaterThanOrderByTimeOfDeparture(user, LocalDateTime.now());
    }

    public List<TripRouteCarrier> getSoonestTripsFor(Schema schema) {
        return hlavniRepositar.findAllBySchemaEqualsAndTimeOfArrivalGreaterThanOrderByTimeOfDeparture(schema, LocalDateTime.now());
    }
}
