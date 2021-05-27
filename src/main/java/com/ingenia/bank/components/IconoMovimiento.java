package com.ingenia.bank.components;

import com.ingenia.bank.backend.model.Movimiento;
import com.ingenia.bank.backend.model.TipoMovimiento;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class IconoMovimiento extends HorizontalLayout{

    public IconoMovimiento(Movimiento movimiento) {

        super();
        Image img;

        if(movimiento.getTipo().equals(TipoMovimiento.INGRESO)) {
            img = new Image("images/profit.png", "Profit");
        }else {
            img = new Image("images/devaluation.png", "visa");
        }

        img.setWidth("20px");
        img.setHeight("20px");

        add(img);
    }
}