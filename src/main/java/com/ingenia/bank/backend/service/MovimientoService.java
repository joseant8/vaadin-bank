package com.ingenia.banca.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ingenia.banca.model.Cuenta;
import com.ingenia.banca.model.Movimiento;
import com.ingenia.banca.model.Tarjeta;
import com.ingenia.banca.payload.filter.MovimientoMesFilter;
import com.ingenia.banca.payload.filter.MovimientosFilter;

@Service
public interface MovimientoService {
	
	public List<Movimiento> obtenerMovimientosDeTarjeta(Long idTarjeta);
	
	public List<Movimiento> obtenerMovimientosDeCuenta(Long idCuenta);

	public List<Movimiento> obtenerMovimientosFiltrados(MovimientosFilter filtro);
	
	public List<Movimiento> obtenerMovimientoFechaCuenta(Long idCuenta,LocalDate fechaInit, LocalDate fechaFin);

	public List<Movimiento> obtenerMovimientoFechaTarjeta(Long idTarjeta,LocalDate fechaInit, LocalDate fechaFin);

	public List<Movimiento> obtenerMovimientosCuentaByCategoria(Long idCuenta, MovimientoMesFilter filtroMovimiento);

	public List<Movimiento> obtenerMovimientosTarjetaByCategoria(Long idTarjeta, MovimientoMesFilter filtroMovimiento);

	public List<Movimiento> obtenerMovimientosUsuarioByCategoria(Long idUsuario, MovimientoMesFilter filtroMovimiento);

	public Movimiento crearMovimiento(Movimiento movimientoNuevo) throws Exception;
}
