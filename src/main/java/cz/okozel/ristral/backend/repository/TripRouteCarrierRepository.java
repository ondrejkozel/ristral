package cz.okozel.ristral.backend.repository;

import cz.okozel.ristral.backend.entity.trips.TripRouteCarrier;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.repository.generic.GenericSchemaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TripRouteCarrierRepository extends GenericSchemaRepository<TripRouteCarrier> {
    List<TripRouteCarrier> findAllByAssociatedTrip_UserEqualsAndTimeOfArrivalGreaterThanOrderByTimeOfDeparture(Uzivatel user, LocalDateTime time);
}
