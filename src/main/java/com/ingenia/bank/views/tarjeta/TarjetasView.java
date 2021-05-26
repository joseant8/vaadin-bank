package com.ingenia.bank.views.tarjeta;

import org.springframework.beans.factory.annotation.Autowired;

import com.ingenia.bank.backend.model.Tarjeta;
import com.ingenia.bank.backend.service.MovimientoService;
import com.ingenia.bank.backend.service.TarjetaService;
import com.ingenia.bank.views.main.MainView;
import com.ingenia.bank.views.tarjeta.form.TarjetaDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "tarjetas", layout = MainView.class)
@PageTitle("Tarjetas")
public class TarjetasView extends VerticalLayout {
	
	private Grid<Tarjeta> grid = new Grid<>();
	
	private Long idCuenta = 1L;
	
	@Autowired
	private TarjetaService tarjetaService;
	
	@Autowired
	private MovimientoService movimientoService;
	
	public TarjetasView(TarjetaService tarjetaService, MovimientoService movimientoService) {
		this.movimientoService = movimientoService;
		this.tarjetaService = tarjetaService;
		setSizeFull();
		
		createGrid();
		grid.setDataProvider(new ListDataProvider<>(tarjetaService.obtenerTarjetaByCuenta(idCuenta)));
		grid.addItemClickListener(event -> {
			new TarjetaDialog(this.movimientoService, this.tarjetaService, event.getItem().getId()).open();
		});
		add(new H2("Tarjetas"),grid);
	}
	
	
    /**
     * Metodo que se encarga de crear un Grid para la visualizacion de los datos de un movimiento
     * @return Devuelve un grid con los campos para representar Movimientos
     */
	private Grid<Tarjeta> createGrid() {
		grid = new Grid<>();
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,GridVariant.LUMO_ROW_STRIPES);

		grid.addColumn(t -> t.getId()).setHeader("Id").setWidth("100px").setFlexGrow(0).setSortable(true);
        grid.addColumn(t -> t.getNumero()).setHeader("Numero Tarjeta").setFlexGrow(1).setSortable(true);
        grid.addColumn(t -> t.getCuenta()==null ? "" : t.getCuenta().getIban()).setHeader("Cuenta").setFlexGrow(1).setSortable(true);
        
        return grid;
	}
}
