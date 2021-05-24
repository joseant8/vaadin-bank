package com.ingenia.banca.repository;

import com.ingenia.banca.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByNombre(String nombre);

    Boolean existsByNombre(String nombre);
}
