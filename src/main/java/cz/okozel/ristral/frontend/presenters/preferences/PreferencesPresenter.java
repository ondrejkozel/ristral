package cz.okozel.ristral.frontend.presenters.preferences;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import cz.okozel.ristral.frontend.MainLayout;
import cz.okozel.ristral.frontend.presenters.Presenter;
import cz.okozel.ristral.frontend.views.preferences.PreferencesView;

import javax.annotation.security.PermitAll;

@PageTitle("Nastavení")
@Route(value = "preferences", layout = MainLayout.class)
@PermitAll
public class PreferencesPresenter extends Presenter<PreferencesView> {
}
