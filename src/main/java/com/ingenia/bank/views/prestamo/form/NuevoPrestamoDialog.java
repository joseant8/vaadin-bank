package com.ingenia.bank.views.prestamo.form;

import com.ingenia.bank.backend.model.Prestamo;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class NuevoPrestamoDialog extends Dialog {

    public static enum DIALOG_RESULT {SAVE, CANCEL};
    private DIALOG_RESULT dialogResult;

    public NuevoPrestamoDialog(Prestamo prestamo){
        super();

        add(new H5("Cuenta Ingreso"), new H5(prestamo.getCuentaIngreso().getIban()));

        add(new H5("Cuenta Cobro"), new H4(prestamo.getCuentaCobro().getIban()));

        add(new H5("Cantidad"), new H4(prestamo.getCantidad().toString()));

        add(createToolbarLayout());


    }

    public boolean dialogResultIsSave(){
        if(this.dialogResult == DIALOG_RESULT.SAVE){
            return true;
        }else{
            return false;
        }
    }

    private Component createToolbarLayout() {

        Button saveButton = new Button("Confirm", event -> {
            this.dialogResult = DIALOG_RESULT.SAVE;
            Notification.show("Préstamo creado", 5000, Notification.Position.TOP_END);
            close();
        });

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickShortcut(Key.ENTER).listenOn(this);
        saveButton.getElement().getStyle().set("margin-left", "auto");

        Button cancelButton = new Button("Cancel", event -> {
            this.dialogResult = DIALOG_RESULT.CANCEL;
            close();
        });

        HorizontalLayout formToolBar = new HorizontalLayout(saveButton, cancelButton);
        formToolBar.setWidthFull();
        formToolBar.getElement().getStyle().set("padding-top", "30px");

        return formToolBar;
    }
}
