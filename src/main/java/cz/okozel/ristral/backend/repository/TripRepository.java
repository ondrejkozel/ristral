package cz.okozel.ristral.backend.repository;

import cz.okozel.ristral.backend.entity.lines.Line;
import cz.okozel.ristral.backend.entity.trips.Trip;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.entity.vozidla.Vozidlo;
import cz.okozel.ristral.backend.repository.generic.GenericSchemaRepository;

import java.util.List;

public interface TripRepository extends GenericSchemaRepository<Trip> {

    List<Trip> findAllByUserEquals(Uzivatel user);

    List<Trip> findAllByVehicleEquals(Vozidlo vehicle);

    List<Trip> findAllByLineEquals(Line line);
}
