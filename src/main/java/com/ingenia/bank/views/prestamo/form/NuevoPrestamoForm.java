package com.ingenia.bank.views.prestamo.form;

import com.ingenia.bank.backend.model.Cuenta;
import com.ingenia.bank.backend.model.Prestamo;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

import java.util.List;

public class NuevoPrestamoForm extends VerticalLayout {

    public static enum FORM_RESULT {SAVE, CANCEL};
    private FORM_RESULT formResult;
    private static final int NOTIFICATION_DEFAULT_DURATION = 5000;

    private Prestamo prestamo;
    private Binder<Prestamo> prestamoBinder = new BeanValidationBinder<Prestamo>(Prestamo.class);

    List<Cuenta> cuentasList;

    private FormLayout formLayout;

    // los nombres deben coincidir con los atributos de la clase para hacer el binding
    private ComboBox<Cuenta> cuentaIngreso;
    private ComboBox<Cuenta> cuentaCobro;
    private TextField lot;
    private TextField serialNumber;
    //private DatePicker expirationDate;
    private NumberField cantidad;

    public NuevoPrestamoForm(List<Cuenta> cuentasList){
        this.cuentasList = cuentasList;
        this.prestamo = new Prestamo();

        add(createFormLayout(), new Hr(), createToolbarLayout());

        prestamoBinder.bindInstanceFields(this);
    }

    private Component createFormLayout() {
        formLayout = new FormLayout();
        formLayout.setWidthFull();

        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("1px", 1),
                new FormLayout.ResponsiveStep("600px", 2),
                new FormLayout.ResponsiveStep("700px", 3));


        // Definir los campos del formulario

        cuentaIngreso = new ComboBox<Cuenta>();
        cuentaIngreso.setItemLabelGenerator(Cuenta::getIban);
        cuentaIngreso.setLabel("Cuenta Ingreso");
        cuentaIngreso.setItems(this.cuentasList);
        cuentaIngreso.setWidth("300px");
        prestamoBinder.forField(cuentaIngreso).asRequired("Campo requerido");

        cuentaCobro = new ComboBox<Cuenta>();
        cuentaCobro.setItemLabelGenerator(Cuenta::getIban);
        cuentaCobro.setLabel("Cuenta Ingreso");
        cuentaCobro.setItems(this.cuentasList);
        cuentaCobro.setWidth("300px");
        prestamoBinder.forField(cuentaCobro).asRequired("Campo requerido");


        HorizontalLayout row01 = new HorizontalLayout();
        row01.setPadding(false);
        row01.setMargin(false);

        lot = new TextField();
        lot.setId("lot");
        lot.setLabel("Lot");
        lot.setWidth("200px");

        serialNumber = new TextField();
        serialNumber.setId("serial-number");
        serialNumber.setLabel("Serial number");

        row01.add(lot, serialNumber);
        row01.setFlexGrow(1, serialNumber);
        formLayout.setColspan(row01, 2);

        HorizontalLayout row02 = new HorizontalLayout();
        row02.setPadding(false);
        row02.setMargin(false);

        cantidad = new NumberField();
        //cantidad.setId("cantidad");
        cantidad.setLabel("Cantidad");
        prestamoBinder.forField(cantidad).asRequired("Cantidad is required");

        row02.add(cantidad);

        formLayout.add(cuentaIngreso, cuentaCobro, row01, row02);

        return formLayout;
    }


    private Component createToolbarLayout() {

        Button saveButton = new Button("Confirmar", event -> {

            prestamoBinder.writeBeanIfValid(prestamo);

            NuevoPrestamoDialog dialog = new NuevoPrestamoDialog(this.prestamo);
            dialog.setWidth("700px");
            dialog.setCloseOnEsc(true);
            dialog.setCloseOnOutsideClick(false);


            dialog.addOpenedChangeListener(e -> {
                if(!e.isOpened()) {
                    if (dialog.dialogResultIsSave())
                        try {
                            Notification.show("Stock Saved", NOTIFICATION_DEFAULT_DURATION, Notification.Position.TOP_END);
                        } catch (Exception ex) {
                            Notification.show(ex.getMessage(), NOTIFICATION_DEFAULT_DURATION, Notification.Position.TOP_END);
                        }
                }
            });

            this.formResult = FORM_RESULT.SAVE;
            dialog.open();

        });

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickShortcut(Key.ENTER).listenOn(this);
        saveButton.getElement().getStyle().set("margin-left", "auto");

        Button cancelButton = new Button("Cancelar", event -> {
            this.formResult = FORM_RESULT.CANCEL;
        });

        HorizontalLayout formToolBar = new HorizontalLayout(saveButton, cancelButton);
        formToolBar.setWidthFull();
        formToolBar.getElement().getStyle().set("padding-top", "30px");

        return formToolBar;
    }


    public FORM_RESULT getDialogResult() {
        return this.formResult;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
        prestamoBinder.readBean(prestamo);
    }

    public Prestamo getPrestamo() {
        return this.prestamo;
    }
}
