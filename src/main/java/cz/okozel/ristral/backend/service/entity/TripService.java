package cz.okozel.ristral.backend.service.entity;

import cz.okozel.ristral.backend.entity.lines.Line;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.trips.Trip;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.entity.vozidla.Vozidlo;
import cz.okozel.ristral.backend.repository.TripRepository;
import cz.okozel.ristral.backend.service.entity.generic.GenericSchemaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TripService extends GenericSchemaService<Trip, TripRepository> {
    public TripService(TripRepository hlavniRepositar) {
        super(hlavniRepositar);
    }

    public List<Trip> findAll(Uzivatel user) {
        return hlavniRepositar.findAllByUserEquals(user);
    }

    public List<Trip> findAll(Vozidlo vehicle) {
        return hlavniRepositar.findAllByVehicleEquals(vehicle);
    }

    public List<Trip> findAll(Line line) {
        return hlavniRepositar.findAllByLineEquals(line);
    }

    public void unbindUsers(List<Uzivatel> users) {
        List<Trip> trips = new ArrayList<>();
        for (Uzivatel user : users) trips.addAll(findAll(user));
        unbindUsersWithTrips(trips);
    }

    public void unbindUser(Uzivatel user) {
        unbindUsers(List.of(user));
    }

    public void unbindAllUsers(Schema schema) {
        unbindUsersWithTrips(findAll(schema));
    }

    private void unbindUsersWithTrips(List<Trip> trips) {
        trips.forEach(Trip::unbindUser);
        saveAll(trips);
    }

    public void unbindVehicles(List<Vozidlo> vehicles) {
        List<Trip> trips = new ArrayList<>();
        for (Vozidlo vehicle : vehicles) trips.addAll(findAll(vehicle));
        unbindVehiclesWithTrips(trips);
    }

    public void unbindVehicle(Vozidlo vehicle) {
        unbindVehicles(List.of(vehicle));
    }

    public void unbindAllVehicles(Schema schema) {
        unbindVehiclesWithTrips(findAll(schema));
    }

    private void unbindVehiclesWithTrips(List<Trip> trips) {
        trips.forEach(Trip::unbindVehicle);
        saveAll(trips);
    }

    public void unbindLines(List<Line> lines) {
        List<Trip> trips = new ArrayList<>();
        for (Line line : lines) trips.addAll(findAll(line));
        unbindLinesWithTrips(trips);
    }

    public void unbindLine(Line line) {
        unbindLines(List.of(line));
    }

    public void unbindAllLines(Schema schema) {
        unbindLinesWithTrips(findAll(schema));
    }

    private void unbindLinesWithTrips(List<Trip> trips) {
        trips.forEach(Trip::unbindLine);
        saveAll(trips);
    }
}
