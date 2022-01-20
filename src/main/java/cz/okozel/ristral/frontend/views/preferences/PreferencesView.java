package cz.okozel.ristral.frontend.views.preferences;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import cz.okozel.ristral.frontend.customComponents.AlertDialog;

public class PreferencesView extends VerticalLayout {

    public PreferencesView() {
        add(buildPersonalDataSection());
    }

    private Section buildPersonalDataSection() {
        Section section = new Section("Osobní údaje");
        Button button = new Button("Změna osobních údajů", event -> new AlertDialog(
                "Změna osobních údajů",
                "Hlavním cílem je, aby systém v co nejkratším čase dokázal operovat se zastávkami, " +
                        "linkami, vozidly a jízdami, a tak musela být správa osobních údajů odsunuta až na druhou kolej " +
                        ":(",
                "Pokud ale vážně potřebujete změnit své jméno, email nebo něco jiného, napište na " +
                        "ondrakozel@outlook.com").open());
        section.add(button);
        return section;
    }

    static class Section extends VerticalLayout {

        public Section(String header) {
            add(new H2(header));
        }

    }

}
