package com.ingenia.bank.views.movimiento;

import com.ingenia.bank.backend.model.Cuenta;
import com.ingenia.bank.backend.model.Movimiento;
import com.ingenia.bank.backend.model.Tarjeta;
import com.ingenia.bank.backend.model.Usuario;
import com.ingenia.bank.backend.service.MovimientoService;
import com.ingenia.bank.backend.service.UsuarioService;
import com.ingenia.bank.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.Optional;

@Route(value = "movimientos", layout = MainView.class)
@PageTitle("Movimientos")
public class MovimientosView extends VerticalLayout {

    private MovimientoService movimientoService;
    private List<Movimiento> movimientosList;
    private List<Cuenta> cuentasList;
    private Grid<Movimiento> grid = new Grid<>(Movimiento.class);
    private Optional<Usuario> currentUser;

    public MovimientosView(MovimientoService movimientoService, UsuarioService usuarioService){
        addClassName("movimientos-view");

        this.movimientoService = movimientoService;
        this.currentUser = usuarioService.obtenerUsuarioById(1L);
        //this.cuentasList = this.currentUser.get().getCuentas();
        //this.movimientosList = movimientoService.obtenerMovimientosDeCuenta(this.cuentasList.get(0).getId());

        this.movimientosList = movimientoService.obtenerMovimientosDeCuenta(1L);

        setPadding(true);
        add(createGridMovimientos());

    }


    /**
     * Carga la lista de movimientos ordenados por fecha en el grid
     */
    private void loadMovimientosGrid() {
        ListDataProvider<Movimiento> movimientosProvider;
        movimientosProvider = DataProvider.ofCollection(this.movimientosList);
        movimientosProvider.setSortOrder(Movimiento::getFecha, SortDirection.DESCENDING);
        grid.setDataProvider(movimientosProvider);
    }


    /**
     * Crea el grid de productos y lo configura
     * @return Component grid
     */
    private Component createGridMovimientos(){

        loadMovimientosGrid();

        // indicamos columnas y el orden
        grid.setColumns("cuenta.iban", "cantidad", "tipo", "concepto", "fecha", "saldoActual");
        grid.getColumnByKey("cuenta.iban").setHeader("Cuenta");
        grid.getColumnByKey("saldoActual").setHeader("Saldo Cuenta");
        //grid.getColumnByKey("tarjeta.numero").setHeader("Tarjeta");
        grid.addColumn(mov -> getTarjeta(mov.getTarjeta())).setHeader("Tarjeta");


        // estilos del grid
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS,
                GridVariant.LUMO_ROW_STRIPES); //para que las filas pares e impares tengan colores diferentes

        return grid;
    }

    /**
     * Devuelve el número de la tarjeta si se realizó el movimiento con tarjeta y null en caso contrario.
     * @param tarjeta
     * @return número tarjeta
     */
    private Long getTarjeta(Tarjeta tarjeta){
        if(tarjeta != null){
            return tarjeta.getNumero();
        }else{
            return null;
        }
    }







}
