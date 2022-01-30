package cz.okozel.ristral.backend.service.entity;

import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.vozidla.TypVozidla;
import cz.okozel.ristral.backend.entity.vozidla.Vozidlo;
import cz.okozel.ristral.backend.repository.VozidloRepository;
import cz.okozel.ristral.backend.service.entity.generic.GenericSchemaService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class VozidloService extends GenericSchemaService<Vozidlo, VozidloRepository> {

    private final TripService tripService;

    public VozidloService(VozidloRepository hlavniRepositar, TripService tripService) {
        super(hlavniRepositar);
        this.tripService = tripService;
    }

    public long count(TypVozidla vehicleType) {
        return hlavniRepositar.countVozidloByTypEquals(vehicleType);
    }

    @Override
    public void delete(Vozidlo objekt) {
        tripService.unbindVehicle(objekt);
        super.delete(objekt);
    }

    @Override
    public void deleteAll(Iterable<Vozidlo> objekty) {
        tripService.unbindVehicles(StreamSupport.stream(objekty.spliterator(), false).collect(Collectors.toList()));
        super.deleteAll(objekty);
    }

    @Override
    public void deleteAll(Schema schema) {
        tripService.unbindAllVehicles(schema);
        super.deleteAll(schema);
    }
}
