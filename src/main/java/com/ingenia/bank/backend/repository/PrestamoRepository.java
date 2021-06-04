package com.ingenia.bank.backend.repository;

import com.ingenia.bank.backend.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    @Query("SELECT p FROM Prestamo p WHERE p.usuario.id = :id")
    List<Prestamo> obtenerPrestamosUsuario(@Param("id") Long id);

}
