package cz.okozel.ristral.backend.service.entity;

import cz.okozel.ristral.backend.entity.lines.Line;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.vozidla.TypVozidla;
import cz.okozel.ristral.backend.repository.LineRepository;
import cz.okozel.ristral.backend.service.entity.generic.GenericSchemaService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LineService extends GenericSchemaService<Line, LineRepository> {

    private final TripService tripService;

    public LineService(LineRepository hlavniRepositar, TripService tripService) {
        super(hlavniRepositar);
        this.tripService = tripService;
    }

    public long count(TypVozidla prefVehicleType) {
        return hlavniRepositar.countLineByPrefVehicleTypeEquals(prefVehicleType);
    }

    @Override
    public void delete(Line objekt) {
        tripService.unbindLine(objekt);
        super.delete(objekt);
    }

    @Override
    public void deleteAll(Iterable<Line> objekty) {
        tripService.unbindLines(StreamSupport.stream(objekty.spliterator(), false).collect(Collectors.toList()));
        super.deleteAll(objekty);
    }

    @Override
    public void deleteAll(Schema schema) {
        tripService.unbindAllLines(schema);
        super.deleteAll(schema);
    }
}
