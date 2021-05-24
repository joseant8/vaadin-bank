package com.ingenia.banca.utils;

import java.util.Iterator;
import java.util.List;

import com.ingenia.banca.model.Movimiento;
import com.ingenia.banca.model.TipoMovimiento;

public class Utils {
	
	public static double obtenerSaldoDeMovimientos(List<Movimiento> listaMovimientos){
		double balance = 0;
		for (Iterator iterator = listaMovimientos.iterator(); iterator.hasNext();) {
			Movimiento movimiento = (Movimiento) iterator.next();
			if(movimiento.getTipo().equals(TipoMovimiento.INGRESO)) {
				balance += movimiento.getCantidad();
			}else {
				balance -= movimiento.getCantidad();
			}
		}
		return balance;
	}

}
