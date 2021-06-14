package com.ingenia.bank.backend.service.impl;

import com.ingenia.bank.backend.model.Cuenta;
import com.ingenia.bank.backend.model.Tarjeta;
import com.ingenia.bank.backend.repository.CuentaRepository;
import com.ingenia.bank.backend.repository.TarjetaRepository;
import com.ingenia.bank.backend.service.TarjetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TarjetaServiceImpl implements TarjetaService {
	
	@Autowired
	TarjetaRepository tarjetaRepository;

	@Autowired
	CuentaRepository cuentaRepository;

	@Transactional
    @Override
	public Tarjeta crearTarjeta(Tarjeta tarjetaNueva) {
		return tarjetaRepository.save(tarjetaNueva);
	}

	@Transactional
	@Override
	public List<Tarjeta> obtenerTarjetasByCuenta(Long cuentaId) {
		
		return tarjetaRepository.findByCuentaId(cuentaId);
	}

	@Transactional
	@Override
	public List<Tarjeta> obtenerTarjetasByUsuario(Long usuarioId) {
		List<Cuenta> cuentas = cuentaRepository.obtenerCuentasByUserId(usuarioId);
		List<Tarjeta> tarjetas = new ArrayList<>();
		for(Cuenta c: cuentas){
			tarjetas.addAll(c.getTarjetas());
		}
		return tarjetas;
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
