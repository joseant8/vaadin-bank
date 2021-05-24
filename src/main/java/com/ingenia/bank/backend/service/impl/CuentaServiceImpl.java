package com.ingenia.banca.service.impl;

import com.ingenia.banca.model.Cuenta;
import com.ingenia.banca.repository.CuentaRepository;
import com.ingenia.banca.service.CuentaService;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CuentaServiceImpl implements CuentaService {

	@Autowired
	CuentaRepository cuentaRepository;
	
	
	@Override
	public Cuenta crearCuenta(Cuenta cuenta) {
		 if(cuentaRepository.existsByIban(cuenta.getIban())){
			 return cuenta;
		 }
		return cuentaRepository.save(cuenta);
	}

	@Override
	public Cuenta obtenerCuentaById(Long idCuenta) {
		return cuentaRepository.findById(idCuenta).orElseThrow(()
				-> new EntityNotFoundException("No se ha encontrado categoria con id: "+idCuenta));
	}

	@Override
	public List<Cuenta> obtenerTodasCuentasByUsuarioId(Long idUsuario) {
		return cuentaRepository.obtenerCuentasByUserId(idUsuario);
	}

}
