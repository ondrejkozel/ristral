package cz.okozel.ristral.backend.service.entity;

import cz.okozel.ristral.backend.entity.lines.Line;
import cz.okozel.ristral.backend.repository.LineRepository;
import cz.okozel.ristral.backend.service.entity.generic.GenericSchemaService;
import org.springframework.stereotype.Service;

@Service
public class LineService extends GenericSchemaService<Line, LineRepository> {

    public LineService(LineRepository hlavniRepositar) {
        super(hlavniRepositar);
    }
}
