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
        H5 cuentaIngresoTitle = new H5("Cuenta ingreso: ");
        H5 cuentaIngresoData = new H5(prestamo.getCuentaIngreso().getIban());
        cuentaIngresoData.getElement().getStyle().set("color", "#D01E69");
        hl1.add(cuentaIngresoTitle, cuentaIngresoData);

        HorizontalLayout hl2 = new HorizontalLayout();
        H5 cuentaCobroTitle = new H5("Cuenta cobro: ");
        H5 cuentaCobroData = new H5(prestamo.getCuentaCobro().getIban());
        cuentaCobroData.getElement().getStyle().set("color", "#D01E69");
        hl2.add(cuentaCobroTitle, cuentaCobroData);

        HorizontalLayout hl3 = new HorizontalLayout();
        H5 cantidadTitle = new H5("Cantidad del préstamo: ");
        H5 cantidadData = new H5(prestamo.getCantidad() + " €");
        cantidadData.getElement().getStyle().set("color", "#D01E69");
        hl3.add(cantidadTitle, cantidadData);

        HorizontalLayout hl4 = new HorizontalLayout();
        H5 interesTitle = new H5("Interés: ");
        H5 interesData = new H5(prestamo.getInteres() + " %");
        interesData.getElement().getStyle().set("color", "#D01E69");
        hl4.add(interesTitle, interesData);

        HorizontalLayout hl5 = new HorizontalLayout();
        H5 cuotaTitle = new H5("Cuota: ");
        H5 cuotaData = new H5(prestamo.getCuota() + " €");
        cuotaData.getElement().getStyle().set("color", "#D01E69");
        hl5.add(cuotaTitle, cuotaData);

        HorizontalLayout hl6 = new HorizontalLayout();
        H5 numCuotasTitle = new H5("Número de cuotas: ");
        H5 numCuotasData = new H5(prestamo.getNumeroCuotas().toString());
        numCuotasData.getElement().getStyle().set("color", "#D01E69");
        hl6.add(numCuotasTitle, numCuotasData);

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
