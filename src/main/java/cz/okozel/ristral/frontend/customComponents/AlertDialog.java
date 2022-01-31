package cz.okozel.ristral.frontend.customComponents;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AlertDialog extends Dialog {

    public AlertDialog(String header, String... paragraphs) {
        H2 headline = new H2(header);
        headline.getStyle()
                .set("margin", "var(--lumo-space-m) 0")
                .set("font-size", "1.5em")
                .set("font-weight", "bold");
        Button closeButton = new Button("Zavřít");
        closeButton.addClickListener(e -> close());
        //
        VerticalLayout dialogLayout = new VerticalLayout(headline);
        for (String paragraph : paragraphs) dialogLayout.add(new Paragraph(paragraph));
        dialogLayout.add(closeButton);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "400px").set("max-width", "100%");
        dialogLayout.setAlignSelf(FlexComponent.Alignment.END, closeButton);
        add(dialogLayout);
    }
}
