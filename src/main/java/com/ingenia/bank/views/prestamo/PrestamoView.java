package com.ingenia.bank.views.prestamo;

import com.ingenia.bank.backend.model.Cuenta;
import com.ingenia.bank.backend.model.Prestamo;
import com.ingenia.bank.backend.model.Usuario;
import com.ingenia.bank.backend.service.CuentaService;
import com.ingenia.bank.backend.service.PrestamoService;
import com.ingenia.bank.backend.service.UsuarioService;
import com.ingenia.bank.views.main.MainView;
import com.ingenia.bank.views.prestamo.form.NuevoPrestamoForm;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Route(value = "prestamo", layout = MainView.class)
@PageTitle("Prestamo")
public class PrestamoView extends VerticalLayout {

    private Optional<Usuario> currentUser;
    private List<Cuenta> cuentasList;
    private List<Prestamo> prestamosUsuario;

    private Grid<Prestamo> grid;


    public PrestamoView(PrestamoService prestamoService, CuentaService cuentaService, UsuarioService usuarioService) {

        this.currentUser = usuarioService.obtenerUsuarioActualConectado();
        this.cuentasList = cuentaService.obtenerTodasCuentasByUsuarioId(currentUser.get().getId());

        setSizeFull();
        add(new H2("Solicitar Préstamo"));

        this.prestamosUsuario = prestamoService.obtenerPrestamosUsuario(currentUser.get().getId());

        NuevoPrestamoForm prestamoForm = new NuevoPrestamoForm(this.cuentasList, prestamoService, currentUser.get());
        add(prestamoForm);

        // linea delimitadora
        Hr limiter = new Hr();
        limiter.setWidthFull();
        add(limiter);

        add(new H2("Préstamos anteriores"));
        add(createGrid());

    }


    /**
     * Crea un grid con los todos los préstamos solicitados del usuario actual
     * @return grid
     */
    private Grid<Prestamo> createGrid() {
        grid = new Grid<>();
        grid.setWidthFull();

        loadPrestamosGrid();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        grid.addColumn(p -> p.getCantidad() + " €").setHeader("Cantidad").setFlexGrow(1);
        grid.addColumn(p -> p.getCuentaIngreso().getIban()).setHeader("Cuenta Ingreso").setFlexGrow(1);
        grid.addColumn(p -> p.getCuentaCobro().getIban()).setHeader("Cuenta Cobro").setFlexGrow(1);
        grid.addColumn(p -> dateFormat.format(p.getFechaInicio())).setHeader("Fecha solicitud").setWidth("250px").setFlexGrow(0);
        grid.addColumn(p -> p.getInteres() + " %").setHeader("Interés").setFlexGrow(1);
        grid.addColumn(p -> p.getCuota() + " €").setHeader("Cuota").setFlexGrow(1);
        grid.addColumn(p -> p.getNumeroCuotas()).setHeader("Número de cuotas").setFlexGrow(1);


        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,GridVariant.LUMO_ROW_STRIPES);
        return grid;
    }

    /**
     * Carga la lista de préstamos en el grid y los ordena según orden de creación descendente (para que salgan primero los más recientes).
     */
    private void loadPrestamosGrid() {
        //grid.setDataProvider(new ListDataProvider<>(this.prestamosUsuario));
        ListDataProvider<Prestamo> provider;
        provider = DataProvider.ofCollection(this.prestamosUsuario);
        provider.setSortOrder(Prestamo::getId, SortDirection.DESCENDING);
        grid.setDataProvider(provider);
    }

}
