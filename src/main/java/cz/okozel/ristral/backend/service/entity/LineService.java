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
    private final LineRouteService lineRouteService;

    public LineService(LineRepository hlavniRepositar, TripService tripService, LineRouteService lineRouteService) {
        super(hlavniRepositar);
        this.tripService = tripService;
        this.lineRouteService = lineRouteService;
    }

    public long count(TypVozidla prefVehicleType) {
        return hlavniRepositar.countLineByPrefVehicleTypeEquals(prefVehicleType);
    }

    @Override
    public void delete(Line objekt) {
        tripService.unbindLine(objekt);
        lineRouteService.deleteAll(objekt);
        super.delete(objekt);
    }

    @Override
    public void deleteAll(Iterable<Line> objekty) {
        tripService.unbindLines(StreamSupport.stream(objekty.spliterator(), false).peek(lineRouteService::deleteAll).collect(Collectors.toList()));
        super.deleteAll(objekty);
    }

    @Override
    public void deleteAll(Schema schema) {
        tripService.unbindAllLines(schema);
        lineRouteService.deleteAll(schema);
        super.deleteAll(schema);
    }
}
