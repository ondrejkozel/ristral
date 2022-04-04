package cz.okozel.ristral.backend.service.entity;

import cz.okozel.ristral.backend.entity.lines.Line;
import cz.okozel.ristral.backend.entity.lines.LineRouteCarrier;
import cz.okozel.ristral.backend.repository.LineRouteCarrierRepository;
import cz.okozel.ristral.backend.service.entity.generic.GenericSchemaService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LineRouteService extends GenericSchemaService<LineRouteCarrier, LineRouteCarrierRepository> {

    public LineRouteService(LineRouteCarrierRepository hlavniRepositar) {
        super(hlavniRepositar);
    }

    @Transactional
    public void deleteAll(Line line) {
        hlavniRepositar.deleteAllByAssociatedLineEquals(line);
    }

    public List<LineRouteCarrier> findAll(Line line) {
        return hlavniRepositar.findAllByAssociatedLineEquals(line);
    }
}
