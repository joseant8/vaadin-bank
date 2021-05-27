package com.ingenia.bank.views.cuenta;

import com.ingenia.bank.backend.model.Cuenta;
import com.ingenia.bank.backend.service.CuentaService;
import com.ingenia.bank.backend.service.MovimientoService;
import com.ingenia.bank.views.cuenta.form.CuentaDialog;
import com.ingenia.bank.views.main.MainView;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "cuentas", layout = MainView.class)
@PageTitle("Cuentas")
public class CuentasView extends VerticalLayout {

    private Grid<Cuenta> grid = new Grid<>();

    private Long idUsuario = 1L;

    private CuentaService cuentaService;
    private MovimientoService movimientoService;

    public CuentasView(CuentaService cuentaService, MovimientoService movimientoService) {

        this.movimientoService = movimientoService;
        this.cuentaService = cuentaService;

        setSizeFull();
        createGrid();

        this.grid.setDataProvider(new ListDataProvider<>(cuentaService.obtenerTodasCuentasByUsuarioId(idUsuario)));
        this.grid.addItemClickListener(event -> {
            new CuentaDialog(this.cuentaService, this.movimientoService, event.getItem().getId()).open();
        });

        add(new H2("Cuentas"),grid);
    }


    /**
     * Crea un grid con las cuentas
     * @return grid
     */
    private Grid<Cuenta> createGrid() {

        grid = new Grid<>();

        grid.addColumn(cuenta -> cuenta.getId()).setHeader("Id").setWidth("100px").setFlexGrow(0).setSortable(true);
        grid.addColumn(cuenta -> cuenta.getIban()).setHeader("Iban").setFlexGrow(1).setSortable(true);
        grid.addColumn(cuenta -> cuenta.getSaldo()).setHeader("Saldo").setFlexGrow(1).setSortable(true);

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,GridVariant.LUMO_ROW_STRIPES);

        return grid;
    }
}
