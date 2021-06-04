package com.ingenia.bank.views.prestamo.form;

import com.ingenia.bank.backend.model.Cuenta;
import com.ingenia.bank.backend.model.Prestamo;
import com.ingenia.bank.backend.model.Usuario;
import com.ingenia.bank.backend.service.PrestamoService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
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


    private PrestamoService prestamoService;
    private Prestamo prestamo;
    private Usuario currentUser;
    List<Cuenta> cuentasList;
    private Binder<Prestamo> prestamoBinder = new BeanValidationBinder<Prestamo>(Prestamo.class);

    private FormLayout formLayout;

    // los nombres de las varaiables de los campos deben coincidir con los atributos de la clase modelo para hacer el binding
    private ComboBox<Cuenta> cuentaIngreso;
    private ComboBox<Cuenta> cuentaCobro;
    //private DatePicker expirationDate;
    private NumberField cantidad;
    private TextField concepto;

    public NuevoPrestamoForm(List<Cuenta> cuentasList, PrestamoService prestamoService, Usuario currentUser){
        this.prestamoService = prestamoService;
        this.cuentasList = cuentasList;
        this.currentUser = currentUser;

        this.prestamo = new Prestamo();

        add(createFormLayout(), createToolbarLayout());

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
        prestamoBinder.forField(cuentaIngreso).asRequired("Campo cuenta ingreso es requerido");

        cuentaCobro = new ComboBox<Cuenta>();
        cuentaCobro.setItemLabelGenerator(Cuenta::getIban);
        cuentaCobro.setLabel("Cuenta Cobro");
        cuentaCobro.setItems(this.cuentasList);
        cuentaCobro.setWidth("300px");
        prestamoBinder.forField(cuentaCobro).asRequired("Campo cuenta cobro es requerido");

        HorizontalLayout row01 = new HorizontalLayout();
        row01.setPadding(false);
        row01.setMargin(false);

        cantidad = new NumberField();
        cantidad.setLabel("Cantidad");
        cantidad.setWidth("150px");
        prestamoBinder.forField(cantidad).asRequired("Campo cantidad es requerido");

        concepto = new TextField();
        concepto.setLabel("Concepto");
        concepto.setWidth("200px");
        prestamoBinder.forField(concepto);

        row01.add(cantidad);
        row01.setFlexGrow(1, concepto);
        formLayout.setColspan(row01, 2);

        formLayout.add(cuentaIngreso, cuentaCobro, new HtmlComponent("br"), row01);

        return formLayout;
    }


    private Component createToolbarLayout() {

        Button previewButton = new Button("Previsualizar préstamo", event -> {

            prestamoBinder.writeBeanIfValid(prestamo);

            prestamo = prestamoService.configurarPrestamo(prestamo);
            prestamo.setUsuario(this.currentUser);
            NuevoPrestamoDialog dialog = new NuevoPrestamoDialog(this.prestamo);
            dialog.setWidth("800px");
            dialog.setCloseOnEsc(true);
            dialog.setCloseOnOutsideClick(false);

            dialog.addOpenedChangeListener(e -> {
                if(!e.isOpened()) {
                    if (dialog.dialogResultIsConfirmed())
                        try {
                            prestamoService.crearPrestamo(prestamo);
                            Notification.show("Préstamo solicitado con éxito", NOTIFICATION_DEFAULT_DURATION, Notification.Position.TOP_END).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                            UI.getCurrent().getPage().reload();  // actualiza la página actual
                        } catch (Exception ex) {
                            Notification.show(ex.getMessage(), NOTIFICATION_DEFAULT_DURATION, Notification.Position.TOP_END);
                        }
                }
            });

            this.formResult = FORM_RESULT.SAVE;
            dialog.open();

        });

        previewButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        previewButton.addClickShortcut(Key.ENTER).listenOn(this);
        previewButton.getElement().getStyle().set("margin-right", "auto");


        HorizontalLayout formToolBar = new HorizontalLayout(previewButton);
        formToolBar.setWidthFull();
        formToolBar.getElement().getStyle().set("padding-top", "5px");

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
