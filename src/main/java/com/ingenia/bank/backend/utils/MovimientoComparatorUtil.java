package com.ingenia.bank.backend.utils;

import com.ingenia.bank.backend.model.Movimiento;

import java.util.Comparator;

public class MovimientoComparatorUtil implements Comparator<Movimiento> {

    @Override
    public int compare(Movimiento o1, Movimiento o2) {
        return o2.getFecha().compareTo(o1.getFecha());
    }
}
