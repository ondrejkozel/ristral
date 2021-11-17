package cz.okozel.ristral.frontend.views.zastavky;

import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import cz.okozel.ristral.backend.entity.zastavky.Zastavka;
import cz.okozel.ristral.frontend.views.crud.GenericCrudView;

public class ZastavkyView extends GenericCrudView<Zastavka> {

    @Override
    protected CrudEditor<Zastavka> vytvorEditor(Class<Zastavka> tridaObjektu) {
        TextField firstName = new TextField("Název");
        FormLayout form = new FormLayout(firstName);
        Binder<Zastavka> binder = new Binder<>();
        return new BinderCrudEditor<>(binder, form);
    }

}
