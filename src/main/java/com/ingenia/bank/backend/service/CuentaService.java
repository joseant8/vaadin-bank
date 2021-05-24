package com.ingenia.banca.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ingenia.banca.model.Cuenta;

@Service
public interface CuentaService {

	/**
	 * Crea una nueva cuenta
	 * @param cuenta cuenta que queremos crear
	 * @return La cuenta creada
	 */
	Cuenta crearCuenta(Cuenta cuenta);

	/**
	 * Obtener una cuenta según el id
	 * @param idCuenta id de la cuenta
	 * @return Cuenta
	 */
	Cuenta obtenerCuentaById(Long idCuenta);

	/**
	 * Obtener las cuentas del usuario según su id
	 * @param idUsuario id del usuario
	 * @return Lista de cuentas
	 */
	List<Cuenta> obtenerTodasCuentasByUsuarioId(Long idUsuario);

}
