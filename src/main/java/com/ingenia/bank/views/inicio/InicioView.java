package com.ingenia.bank.views.inicio;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.DataLabelsBuilder;
import com.github.appreciated.apexcharts.config.builder.LegendBuilder;
import com.github.appreciated.apexcharts.config.builder.ResponsiveBuilder;
import com.github.appreciated.apexcharts.config.builder.StrokeBuilder;
import com.github.appreciated.apexcharts.config.builder.TitleSubtitleBuilder;
import com.github.appreciated.apexcharts.config.builder.XAxisBuilder;
import com.github.appreciated.apexcharts.config.builder.YAxisBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.ZoomBuilder;
import com.github.appreciated.apexcharts.config.legend.HorizontalAlign;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.stroke.Curve;
import com.github.appreciated.apexcharts.config.subtitle.Align;
import com.github.appreciated.apexcharts.config.xaxis.XAxisType;
import com.github.appreciated.apexcharts.config.xaxis.labels.DatetimeFormatter;
import com.github.appreciated.apexcharts.helper.Series;
import com.ingenia.bank.backend.model.Categoria;
import com.ingenia.bank.backend.model.Movimiento;
import com.ingenia.bank.backend.model.Tarjeta;
import com.ingenia.bank.backend.model.TipoMovimiento;
import com.ingenia.bank.backend.payload.filter.MovimientoMesFilter;
import com.ingenia.bank.backend.service.CategoriaService;
import com.ingenia.bank.backend.service.MovimientoService;
import com.ingenia.bank.backend.service.TarjetaService;
import com.ingenia.bank.backend.utils.Utils;
import com.ingenia.bank.views.inicio.components.TarjetasDisplayBox;
import com.ingenia.bank.views.main.MainView;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "inicio", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Inicio")
public class InicioView extends HorizontalLayout {
	
	private Grid<Movimiento> grid;
	
	@Autowired
	private MovimientoService movimientoService;
	
	@Autowired
	private TarjetaService tarjetaService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	private String[] fechasDiagrama;
	
	private Double[] listadoGastosDiariosDiagrama;

	
	public InicioView(TarjetaService tarjetaService,MovimientoService movimientoService,CategoriaService categoriaService) {
		this.movimientoService = movimientoService;
		this.tarjetaService =  tarjetaService;
		this.categoriaService = categoriaService;
		// Configuracion general de la pantalla incio
		setSizeFull();
		setPadding(true);
		
		// Create left part of view
		VerticalLayout leftLayout = new VerticalLayout();
		leftLayout.setWidth("60%");
		
		HorizontalLayout text = new HorizontalLayout();
		text.setWidthFull();
		H2 tituloTarjeta = new H2("Tarjetas");
		leftLayout.add(tituloTarjeta);
		tituloTarjeta.getElement().getStyle().set("margin-top", "0");
		tituloTarjeta.getElement().getStyle().set("margin-right", "auto");

		Anchor anchor = new Anchor("#", "Ver Tarjetas");
		anchor.getElement().getStyle().set("margin-left", "auto");
		anchor.getElement().getStyle().set("margin-top", "15px");

		
		text.add(tituloTarjeta,anchor);
		leftLayout.add(text);
		
		
		HorizontalLayout creditCardLayout = new HorizontalLayout();
		creditCardLayout.setWidthFull();
//		creditCardLayout.setHeight("257px");
		

//		creditCardLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
		creditCardLayout.setPadding(true);
		
		List<Tarjeta> listaTarjetas = tarjetaService.obtenerTarjetaByCuenta(1L);
		
		for (int i = 0; i < listaTarjetas.size() && i < 3; i++) {
			TarjetasDisplayBox displayTarjeta = new TarjetasDisplayBox(listaTarjetas.get(i), this.movimientoService);
			creditCardLayout.add(displayTarjeta);
		}
		

		leftLayout.add(creditCardLayout);
		
		
		Hr limiter = new Hr();
		limiter.setWidthFull();
		leftLayout.add(limiter);
		
		
		HorizontalLayout movimientoLayout = new HorizontalLayout();
		movimientoLayout.setWidthFull();
		H2 tituloMovimiento= new H2("Movimientos");
		leftLayout.add(tituloMovimiento);
		tituloMovimiento.getElement().getStyle().set("margin-top", "0");
		tituloMovimiento.getElement().getStyle().set("margin-right", "auto");

		Anchor anchorMovimientos = new Anchor("#", "Ver mas");
		anchorMovimientos.getElement().getStyle().set("margin-left", "0");
		anchorMovimientos.getElement().getStyle().set("margin-top", "15px");

		
		movimientoLayout.add(tituloMovimiento,anchorMovimientos);
		
		createGrid();
		
		grid.setDataProvider(new ListDataProvider<>(this.movimientoService.obtenerMovimientosDeCuentaOrdenadosFecha(1L)));
		
		leftLayout.add(movimientoLayout,grid);
		

		
		// Create right part of view
		VerticalLayout rightLayout = new VerticalLayout();
		rightLayout.setWidth("40%");
		HorizontalLayout textAnalisis = new HorizontalLayout();
		text.setWidthFull();
		H2 tituloAnalisis = new H2("Balance Cuenta");
		leftLayout.add(tituloAnalisis);
		tituloAnalisis.getElement().getStyle().set("margin-top", "0");
		tituloAnalisis.getElement().getStyle().set("margin-right", "auto");

		textAnalisis.add(tituloAnalisis);
		
		rightLayout.add(textAnalisis);
		
		
		rightLayout.add(areaChartExample());
		
		HorizontalLayout gastos = new HorizontalLayout();
		gastos.setWidthFull();
		
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int diaFinalMes = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		LocalDate fechaInit = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
		LocalDate fechaFin = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), diaFinalMes);
		
		List<Movimiento> movimientosMes =  this.movimientoService.obtenerMovimientoFechaCuenta(1L,fechaInit,fechaFin);
		double gastosMes = Utils.obtenerGastos(movimientosMes); 
		double ingresosMes = Utils.obtenerIngresos(movimientosMes); 
		
		Span ingresosMensaual = new Span("Ingresos del mes: "+ingresosMes+" €");
		ingresosMensaual.getElement().getStyle().set("margin-top", "0");
		ingresosMensaual.getElement().getStyle().set("margin-right", "auto");
		ingresosMensaual.getElement().getStyle().set("color", "#20F14E");
		ingresosMensaual.getElement().getStyle().set("size", "16px");
		gastos.add(ingresosMensaual);
		
		Span gastosMensaual = new Span("Gastos del mes: "+gastosMes+" €");
		gastosMensaual.getElement().getStyle().set("margin-top", "0");
		gastosMensaual.getElement().getStyle().set("margin-left", "auto");
		gastosMensaual.getElement().getStyle().set("color", "#FF0F0F");
		gastosMensaual.getElement().getStyle().set("size", "16px");
		gastos.add(gastosMensaual);
		

		rightLayout.add(gastos);
		
		rightLayout.add(donutChart());
		
		
		
		add(leftLayout,new Divider(),rightLayout);
		
	}
	
    /**
     * Method that generate a grid with the DB products
     * @return Return a Grid with all the columns and object for product crud
     */
	private Grid<Movimiento> createGrid() {
		grid = new Grid<>();
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,GridVariant.LUMO_ROW_STRIPES);
		
		
		// Adding columns to the grid with the Product properties
//        grid.addColumn(c -> c.getId()).setHeader("Id").setWidth("70px").setFlexGrow(0).setSortable(false);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		grid.addComponentColumn(c -> new IconLayout(c))
		.setWidth("100px").setHeader("Tarjeta").setFlexGrow(1);
		grid.addColumn(c -> c.getCantidad()+" €").setHeader("Cantidad").setFlexGrow(0);
        grid.addColumn(c -> c.getConcepto()).setHeader("Concepto").setFlexGrow(1);
        grid.addColumn(c -> dateFormat.format(c.getFecha())).setHeader("Fecha").setWidth("150px").setFlexGrow(0);
        
        return grid;
	}
	
	
    private ApexCharts areaChartExample() {
    	obtenerGastosDiariosMes();
    	
        return ApexChartsBuilder.get()
                .withChart(ChartBuilder.get()
                        .withType(Type.area)
                        .withZoom(ZoomBuilder.get()
                                .withEnabled(false)
                                .build())
                        .build())
                .withDataLabels(DataLabelsBuilder.get()
                        .withEnabled(false)
                        .build())
                .withStroke(StrokeBuilder.get().withCurve(Curve.straight).build())
                .withSeries(new Series<>("Gastos",this.listadoGastosDiariosDiagrama))
                .withLabels(this.fechasDiagrama)
                .withYaxis(YAxisBuilder.get()
                        .withOpposite(true).build())
                .withLegend(LegendBuilder.get().withHorizontalAlign(HorizontalAlign.left).build())
                .withColors("#D01E69")
                .build();
    }

	
	private void obtenerGastosDiariosMes() {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int diaFinalMes = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		List<Movimiento> movimientos = this.movimientoService.obtenerMovimientoFechaCuenta(1L, LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonthValue(),1),
																							LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonthValue(), diaFinalMes));
		List<Double> listaGastos = new ArrayList<Double>();
		List<String> listaFechas = new ArrayList<String>();
		
		Date fechaUtilizadaActual = null;
		double gastoDiario = 0;
		
		for (Iterator iterator = movimientos.iterator(); iterator.hasNext();) {
			Movimiento movimiento = (Movimiento) iterator.next();
			if(fechaUtilizadaActual == null) {
				fechaUtilizadaActual = movimiento.getFecha();		
			}
			
			if(!fechaUtilizadaActual.equals(movimiento.getFecha())) {
				listaGastos.add(gastoDiario);
				listaFechas.add(fechaUtilizadaActual.getDate()+"/"+Utils.getMonthForInt(fechaUtilizadaActual.getMonth()).substring(0, 3));
				gastoDiario = 0;
				fechaUtilizadaActual = movimiento.getFecha();
			}
			
			if(movimiento.getTipo().equals(TipoMovimiento.GASTO)) {
				gastoDiario += movimiento.getCantidad();					
			}
			
			if(!iterator.hasNext()) {
				listaGastos.add(gastoDiario);
				listaFechas.add(fechaUtilizadaActual.getDate()+"/"+Utils.getMonthForInt(fechaUtilizadaActual.getMonth()).substring(0, 3));
			}
			
			
		}
		
		this.listadoGastosDiariosDiagrama = new Double[listaGastos.size()];
		this.listadoGastosDiariosDiagrama = listaGastos.toArray(this.listadoGastosDiariosDiagrama);
		this.fechasDiagrama = new String[listaFechas.size()];
		this.fechasDiagrama =  listaFechas.toArray(this.fechasDiagrama);
		
	}

	private ApexCharts donutChart() {
		List<Categoria> listaCategorias = categoriaService.obtenerTodasCategorias();
		String[] labes = obtenerLabes(listaCategorias);
		Double[] serie = obtenerMovimientos(listaCategorias);
		String[] colors = obtenerColores(listaCategorias);
		return ApexChartsBuilder.get()
                .withChart(ChartBuilder.get().withType(Type.donut).build())
                .withLegend(LegendBuilder.get()
                        .withPosition(Position.right)
                        .build())
                .withSeries(serie)
                .withResponsive(ResponsiveBuilder.get()
                        .withBreakpoint(480.0)
                        .build())
                .withLabels(labes)
                		.withColors(colors)
                .build();
	}

	
	private String[] obtenerColores(List<Categoria> listaCategorias) {
		String[] colores = new String[listaCategorias.size()];
		Random obj = new Random();
		for (int i = 0; i < listaCategorias.size(); i++) {
			int rand_num = obj.nextInt(0xffffff + 1);
			colores[i] = String.format("#%06x", rand_num);
		}
		return colores;
	}

	private Double[] obtenerMovimientos(List<Categoria> listaCategorias) {
		Series<Double> serie = new Series<>();
		Double[] serieData = new Double[listaCategorias.size()];
		for (int i = 0; i < listaCategorias.size(); i++) {
			Categoria categoriaActual = listaCategorias.get(i);
			serieData[i] = Utils.obtenerGastos(this.movimientoService.obtenerMovimientosCuentaByCategoria(1L,new MovimientoMesFilter("05", categoriaActual.getId())));
		}
		serie.setData(serieData);
		return serieData;
	}

	private String[] obtenerLabes(List<Categoria> listaCategorias) {
		String[] labelsCategorias = new String[listaCategorias.size()];
		for (int i = 0; i < listaCategorias.size(); i++) {
			labelsCategorias[i] = listaCategorias.get(i).getNombre();
		}
		return labelsCategorias;
	}


	public class IconLayout extends HorizontalLayout{
		public IconLayout(Movimiento movimiento) {
			super();
			Image img ;
			if(movimiento.getTipo().equals(TipoMovimiento.INGRESO)) {
				img = new Image("images/profit.png", "Profit");
			}else {
				img = new Image("images/devaluation.png", "visa");
			}
			img.setWidth("20px");
			img.setHeight("20px");				
	        add(img, new Span(movimiento.getTarjeta() != null ? Utils.enmascararNumeroTarjeta(movimiento.getTarjeta().getNumero()) : ""));
	        
			
		}
	}
	
	public class Divider extends Span {

	    public Divider() {
	        getStyle().set("background-color", "grey");
	        getStyle().set("opacity", "0.3");
	        getStyle().set("flex", "0 0 1px");
	        getStyle().set("align-self", "stretch");
	    }
	}
	
	
}
