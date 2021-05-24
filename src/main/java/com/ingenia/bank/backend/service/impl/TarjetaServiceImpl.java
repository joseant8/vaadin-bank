package com.ingenia.banca.service.impl;

import com.ingenia.banca.model.Cuenta;
import com.ingenia.banca.model.Tarjeta;
import com.ingenia.banca.repository.TarjetaRepository;
import com.ingenia.banca.service.TarjetaService;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TarjetaServiceImpl implements TarjetaService {
	
	@Autowired
	TarjetaRepository tarjetaRepository;

	@Transactional
    @Override
	public Tarjeta crearTarjeta(Tarjeta tarjetaNueva) {
		return tarjetaRepository.save(tarjetaNueva);
	}

	@Transactional
	@Override
	public List<Tarjeta> obtenerTarjetaByCuenta(Cuenta cuenta) {
		Long id = cuenta.getId();
		return tarjetaRepository.findByCuentaId(id);
	}

	@Transactional
	@Override
	public Tarjeta obtenerTarjetaByNumeroTarjeta(Long numeroTarjeta) {
		return tarjetaRepository.obtenerTarjetaByNumeroTarjeta(numeroTarjeta);
	}

	@Transactional
	@Override
	public Tarjeta obtenerTarjetaById(Long idTarjeta) throws EntityNotFoundException{
		return tarjetaRepository.findById(idTarjeta).orElseThrow(()
                -> new EntityNotFoundException("No se ha encontrado tarjeta con numero: "+idTarjeta));
	}

	
}
