package com.seguros.consulta.repository;

import com.seguros.consulta.model.Seguro;

import java.util.List;
import java.util.Optional;

public interface SeguroRepository {

    List<Seguro> findAll();

    Optional<Seguro> findById(Long id);

}
