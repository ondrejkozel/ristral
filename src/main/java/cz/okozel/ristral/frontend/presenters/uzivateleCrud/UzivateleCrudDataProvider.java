package cz.okozel.ristral.frontend.presenters.uzivateleCrud;

import com.vaadin.flow.component.crud.CrudFilter;
import com.vaadin.flow.data.provider.Query;
import cz.okozel.ristral.backend.entity.schema.Schema;
import cz.okozel.ristral.backend.entity.uzivatele.Uzivatel;
import cz.okozel.ristral.backend.entity.uzivatele.UzivatelOrg;
import cz.okozel.ristral.backend.security.PrihlasenyUzivatel;
import cz.okozel.ristral.backend.service.RegistratorService;
import cz.okozel.ristral.frontend.presenters.crud.GenericDataProvider;

import java.util.stream.Stream;

public class UzivateleCrudDataProvider extends GenericDataProvider<UzivatelOrg, UzivatelOrgService> {

    private final Schema schema;
    private final Uzivatel aktPrihlasenyUzivatel;
    private final RegistratorService registratorService;

    public UzivateleCrudDataProvider(UzivatelOrgService service, Class<UzivatelOrg> tridaObjektu, PrihlasenyUzivatel prihlasenyUzivatel, RegistratorService registratorService) {
        //noinspection OptionalGetWithoutIsPresent
        super(service, tridaObjektu, prihlasenyUzivatel.getPrihlasenyUzivatel().get().getSchema());
        this.aktPrihlasenyUzivatel = prihlasenyUzivatel.getPrihlasenyUzivatel().get();
        this.schema = aktPrihlasenyUzivatel.getSchema();
        this.registratorService = registratorService;
    }

    @Override
    protected Stream<UzivatelOrg> fetchFromBackEnd(Query<UzivatelOrg, CrudFilter> query) {
        return super.fetchFromBackEnd(query).filter(uzivatel -> !uzivatel.equals(aktPrihlasenyUzivatel));
    }

    @Override
    public void uloz(UzivatelOrg objekt) {
        if (objekt.isPersisted()) super.uloz(objekt);
        else {
            objekt.setSchema(schema);
            registratorService.zaregistrujPodrizenyUcet(objekt);
        }
    }

}
