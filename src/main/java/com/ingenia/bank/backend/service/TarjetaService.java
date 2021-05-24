package com.ingenia.banca.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ingenia.banca.model.Cuenta;
import com.ingenia.banca.model.Tarjeta;

@Service
public interface TarjetaService {
	
	public Tarjeta crearTarjeta(Tarjeta tarjetaNueva);
	
	public List<Tarjeta> obtenerTarjetaByCuenta(Cuenta cuenta);

	public Tarjeta obtenerTarjetaByNumeroTarjeta(Long numeroTarjeta);
	
	public Tarjeta obtenerTarjetaById(Long idTarjeta);
	
}
