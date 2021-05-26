package com.ingenia.bank.views.inicio.components;

import java.time.LocalDate;
import java.util.List;

import com.github.appreciated.card.ClickableCard;
import com.ingenia.bank.backend.model.Movimiento;
import com.ingenia.bank.backend.model.Tarjeta;
import com.ingenia.bank.backend.service.MovimientoService;
import com.ingenia.bank.backend.utils.Utils;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TarjetasDisplayBox extends ClickableCard {

	MovimientoService movimientoService;

	public TarjetasDisplayBox(Tarjeta tarjeta,MovimientoService movimientoService) {
		super(componentEvent -> Notification.show("Click Card "+tarjeta.getNumero()));
		this.movimientoService = movimientoService;
		
		// Set some style
		this.setWidth("255px");
		this.setHeight("150px");
		this.getElement().getStyle().set("radius", "24px");
		
		// Create layout
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        
        HorizontalLayout icon = new HorizontalLayout();
        // Icono entidad
        Image imgLogo1 = new Image("images/ingenia.svg", "Ingenia");
        imgLogo1.setWidth("40px");
        imgLogo1.setHeight("25px");
        Image imgLogo2 = new Image("images/bank.svg", "Ingenia");
        imgLogo2.setWidth("30px");
        imgLogo2.setHeight("20px");
        imgLogo2.getElement().getStyle().set("margin-left", "1px");
        icon.add(imgLogo1,imgLogo2);

        layout.add(icon);
//        Span bancoEntidad = new Span("Ingenia Bank");
//        bancoEntidad.getElement().getStyle().set("font-family", "DM Sans");
//        bancoEntidad.getElement().getStyle().set("font-weight", "bold");
//        bancoEntidad.getElement().getStyle().set("color", "#090A25");
//        layout.add(bancoEntidad);
        
      
        // Get saldo
        LocalDate initial = LocalDate.now();
        LocalDate start = initial.withDayOfMonth(1);
        LocalDate end = initial.withDayOfMonth(initial.lengthOfMonth());
        
        List<Movimiento> listaMovimientos = movimientoService.obtenerMovimientoFechaTarjeta(tarjeta.getId(), start, end);
        
        double balance = Utils.obtenerSaldoDeMovimientos(listaMovimientos);
        Span saldoTexto = new Span();
        if(balance < 0) {
        	balance *= -1;
        	saldoTexto.getElement().getStyle().set("color", "#D01E69");
        }else {
        	saldoTexto.getElement().getStyle().set("color", "#73CAA5");
        }
        saldoTexto.add(balance+" €");
        saldoTexto.getElement().getStyle().set("font-weight", "bold");
        layout.setHorizontalComponentAlignment(Alignment.CENTER,
        		saldoTexto);
        layout.add(saldoTexto);

        
        HorizontalLayout downPart = new HorizontalLayout();
        downPart.setWidthFull();
        Image img = new Image("images/Vector.png", "visa");
        img.setWidth("35px");
        img.setHeight("15px");
        img.getElement().getStyle().set("padding-top", "10px");
        
        downPart.add(img);
                

        Span tarjetaNumber = new Span(Utils.enmascararNumeroTarjeta(tarjeta.getNumero()));
        tarjetaNumber.getElement().getStyle().set("margin-left", "auto");
        tarjetaNumber.getElement().getStyle().set("color", "#D01E69");
        tarjetaNumber.getElement().getStyle().set("font-weight", "bold");
        downPart.add(tarjetaNumber);
        
        downPart.getElement().getStyle().set("margin-top", "auto");
        downPart.setPadding(true);
        
        layout.add(downPart);
        add(layout);
        
    }
	
}
