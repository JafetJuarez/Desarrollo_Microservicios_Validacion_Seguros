package com.seguros.consulta.service;

import com.seguros.consulta.model.Seguro;

import java.util.List;

public interface SeguroService {

    List<Seguro> obtenerTodos();

    Seguro obtenerPorId(Long id);

}
