package cz.okozel.ristral.backend.service.entity;

import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.vozidla.TypVozidla;
import cz.okozel.ristral.backend.repository.TypVozidlaRepository;
import cz.okozel.ristral.backend.service.entity.generic.GenericSchemaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypVozidlaService extends GenericSchemaService<TypVozidla, TypVozidlaRepository> {

    private final VozidloService vozidloService;
    private final LineService lineService;

    public TypVozidlaService(TypVozidlaRepository hlavniRepositar, VozidloService vozidloService, LineService lineService) {
        super(hlavniRepositar);
        this.vozidloService = vozidloService;
        this.lineService = lineService;
    }

    public List<TypVozidla> deleteUnusedVehicleTypes(Schema schema) {
        List<TypVozidla> unusedVehicleTypes = findAll(schema) //find all vehicle types with the given scheme
                .stream()
                .filter(typVozidla -> (vozidloService.count(typVozidla) == 0 && lineService.count(typVozidla) == 0)) //getting rid of currently used vehicle types
                .collect(Collectors.toList());
        if (!unusedVehicleTypes.isEmpty()) deleteAll(unusedVehicleTypes);
        return unusedVehicleTypes;
    }

    public static String buildUnusedVehicleTypesMessage(List<TypVozidla> unused) {
        StringBuilder message = new StringBuilder();
        message.append("Typ vozidla ").append(unused.get(0).getNazev());
        if (unused.size() > 1) message.append(" a ").append(unused.size() - 1).append(" dalších");
        message.append(" byl vymazán, protože nebyl nastaven žádnému vozidlu.");
        return message.toString();
    }
}
