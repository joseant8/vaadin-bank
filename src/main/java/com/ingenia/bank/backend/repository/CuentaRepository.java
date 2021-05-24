package com.ingenia.banca.repository;

import com.ingenia.banca.model.Cuenta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

	boolean existsByIban(String iban);

	@Query("select c from Cuenta c join c.usuarios u where u.id = :idUsuario")
	List<Cuenta> obtenerCuentasByUserId(@Param("idUsuario")Long idUsuario);


}
