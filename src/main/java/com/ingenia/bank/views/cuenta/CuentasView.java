package com.ingenia.bank.views.cuenta;

import com.ingenia.bank.views.main.MainView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "cuentas", layout = MainView.class)
@PageTitle("Cuentas")
public class CuentasView extends VerticalLayout {
}
