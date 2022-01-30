package cz.okozel.ristral.backend.service.entity;

import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.repository.UzivatelRepository;
import cz.okozel.ristral.backend.service.entity.generic.GenericSchemaService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UzivatelService extends GenericSchemaService<Uzivatel, UzivatelRepository> {

    private final TripService tripService;

    public UzivatelService(UzivatelRepository uzivatelRepository, TripService tripService) {
        super(uzivatelRepository);
        this.tripService = tripService;
    }

    public Uzivatel findByUzivatelskeJmeno(String uzivatelskeJmeno) {
        return hlavniRepositar.findByUzivatelskeJmenoEquals(uzivatelskeJmeno);
    }

    public boolean jeTotoUzivateskeJmenoObsazene(String uzivatelskeJmeno) {
        return hlavniRepositar.countUzivatelByUzivatelskeJmenoEquals(uzivatelskeJmeno) > 0;
    }


    @Override
    public void delete(Uzivatel objekt) {
        tripService.unbindUser(objekt);
        super.delete(objekt);
    }

    @Override
    public void deleteAll(Iterable<Uzivatel> objekty) {
        tripService.unbindUsers(StreamSupport.stream(objekty.spliterator(), false).collect(Collectors.toList()));
        super.deleteAll(objekty);
    }

    @Override
    public void deleteAll(Schema schema) {
        tripService.unbindAllUsers(schema);
        super.deleteAll(schema);
    }
}
