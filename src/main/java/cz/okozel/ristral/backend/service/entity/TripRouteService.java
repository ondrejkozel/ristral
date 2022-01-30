package cz.okozel.ristral.backend.service.entity;

import cz.okozel.ristral.backend.entity.trips.TripRouteCarrier;
import cz.okozel.ristral.backend.repository.TripRouteCarrierRepository;
import cz.okozel.ristral.backend.service.entity.generic.GenericSchemaService;
import org.springframework.stereotype.Service;

@Service
public class TripRouteService extends GenericSchemaService<TripRouteCarrier, TripRouteCarrierRepository> {
    public TripRouteService(TripRouteCarrierRepository hlavniRepositar) {
        super(hlavniRepositar);
    }
}
