package cz.okozel.ristral.backend.service.entity;

import cz.okozel.ristral.backend.entity.uzivatele.UzivatelOrg;
import cz.okozel.ristral.backend.repository.UzivatelOrgRepository;
import cz.okozel.ristral.backend.service.entity.generic.GenericSchemaService;
import org.springframework.stereotype.Service;


/**
 * služba pouze pro crud uživatelů
 */
@Service
public class UzivatelOrgService extends GenericSchemaService<UzivatelOrg, UzivatelOrgRepository> {

    public UzivatelOrgService(UzivatelOrgRepository hlavniRepositar) {
        super(hlavniRepositar);
    }

}
