package cz.okozel.ristral.frontend.presenters.lineEdit;

import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cz.okozel.ristral.frontend.MainLayout;
import cz.okozel.ristral.frontend.presenters.Presenter;
import cz.okozel.ristral.frontend.views.lineEdit.LineEditView;

import javax.annotation.security.PermitAll;

@PageTitle("Správa tras")
@Route(value = "lines/edit", layout = MainLayout.class)
@PermitAll
public class LineEditPresenter extends Presenter<LineEditView> implements HasUrlParameter<String> {

    @Override
    public void setParameter(BeforeEvent event, String parameter) {

    }
}
