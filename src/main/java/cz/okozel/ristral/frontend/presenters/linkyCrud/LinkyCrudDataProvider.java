package cz.okozel.ristral.frontend.presenters.linkyCrud;

import cz.okozel.ristral.backend.entity.lines.Line;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.service.entity.LineService;
import cz.okozel.ristral.frontend.presenters.crud.GenericDataProvider;

public class LinkyCrudDataProvider extends GenericDataProvider<Line, LineService> {

    public LinkyCrudDataProvider(LineService service, Class<Line> tridaObjektu, Schema schema) {
        super(service, tridaObjektu, schema);
    }

}
