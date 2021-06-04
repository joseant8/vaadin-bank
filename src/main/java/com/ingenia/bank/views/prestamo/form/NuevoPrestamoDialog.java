package com.ingenia.bank.views.prestamo.form;

import com.ingenia.bank.backend.model.Prestamo;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class NuevoPrestamoDialog extends Dialog {

    private boolean isConfirmed = false;

    public NuevoPrestamoDialog(Prestamo prestamo){
        super();
        add(dataPrestamoDialog(prestamo), createToolbarLayout());
    }

    private Component dataPrestamoDialog(Prestamo prestamo){

        VerticalLayout vl = new VerticalLayout();

        HorizontalLayout hl1 = new HorizontalLayout();
        hl1.add(new H5("Cuenta ingreso: "), new H5(prestamo.getCuentaIngreso().getIban()));

        HorizontalLayout hl2 = new HorizontalLayout();
        hl2.add(new H5("Cuenta cobro: "), new H5(prestamo.getCuentaCobro().getIban()));

        HorizontalLayout hl3 = new HorizontalLayout();
        hl3.add(new H5("Cantidad del préstamo: "), new H5(prestamo.getCantidad() + " €"));

        HorizontalLayout hl4 = new HorizontalLayout();
        hl4.add(new H5("Interés: "), new H5(prestamo.getInteres() + " %"));

        HorizontalLayout hl5 = new HorizontalLayout();
        hl5.add(new H5("Cuota: "), new H5(prestamo.getCuota() + " €"));

        HorizontalLayout hl6 = new HorizontalLayout();
        hl6.add(new H5("Número de cuotas: "), new H5(prestamo.getNumeroCuotas().toString()));

        vl.add(hl1, hl2, hl3, hl4, hl5, hl6);
        return vl;

    }

    public boolean dialogResultIsConfirmed(){
        return isConfirmed;
    }

    /**
     * Crea los botones del dialog para aceptar (solicitar préstamo) o cancelar la operación.
     * @return componente
     */
    private Component createToolbarLayout() {

        Button saveButton = new Button("Solicitar préstamo", event -> {
            this.isConfirmed = true;
            close();
        });

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickShortcut(Key.ENTER).listenOn(this);
        saveButton.getElement().getStyle().set("margin-right", "auto");

        Button cancelButton = new Button("Cancelar", event -> {
            this.isConfirmed = false;
            close();
        });

        HorizontalLayout formToolBar = new HorizontalLayout(saveButton, cancelButton);
        formToolBar.setWidthFull();
        formToolBar.getElement().getStyle().set("padding-top", "30px");

        return formToolBar;
    }
}
