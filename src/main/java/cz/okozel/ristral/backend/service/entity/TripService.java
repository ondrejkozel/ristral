package cz.okozel.ristral.backend.service.entity;

import cz.okozel.ristral.backend.entity.trips.Trip;
import cz.okozel.ristral.backend.repository.TripRepository;
import cz.okozel.ristral.backend.service.entity.generic.GenericSchemaService;
import org.springframework.stereotype.Service;

@Service
public class TripService extends GenericSchemaService<Trip, TripRepository> {
    public TripService(TripRepository hlavniRepositar) {
        super(hlavniRepositar);
    }
}
