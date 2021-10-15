package cz.okozel.ristral.backend.service;

import cz.okozel.ristral.backend.repository.SchemaRepository;
import cz.okozel.ristral.backend.repository.UzivatelRepository;
import cz.okozel.ristral.backend.schema.Schema;
import cz.okozel.ristral.backend.schema.TypSchematu;
import cz.okozel.ristral.backend.uzivatele.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class UzivatelService extends GenerickaService<Uzivatel, UzivatelRepository> {
    private SchemaRepository schemaRepository;

    public UzivatelService(UzivatelRepository uzivatelRepository, SchemaRepository schemaRepository) {
        super(uzivatelRepository);
        this.schemaRepository = schemaRepository;
    }

    @PostConstruct
    public void populateTestData() {
        Schema organizace = new Schema(TypSchematu.ORGANIZACE, "Dopravní podnik městské části Žebětín");
        Schema osobni = new Schema(TypSchematu.OSOBNI, "Pepa Novák");
        System.out.println("id před uložením = " + organizace.getId());
        if (schemaRepository.count() == 0) {
            schemaRepository.saveAll(List.of(organizace, osobni));
        }
        System.out.println("id po uložení = " + organizace.getId());
        if (count() == 0) {
            saveAll(List.of(
                    new UzivatelOrg("Ondřej Kozel", "ondrakozel@outlook.com", "hovnokleslo", organizace),
                    new AdminOrg("administrátor", "admin@organizace.com", "admin", organizace),
                    new OsobniUzivatel("Os. uživatel", "osobak@email.com", "já tady vůbec nemám co dělat", osobni),
                    new SuperadminOrg("superadmin", "super@organizace.com", "nereknu", organizace)
            ));
        }
    }
}
