package com.seguros.consulta.service.impl;

import com.seguros.consulta.exception.SeguroNotFoundException;
import com.seguros.consulta.model.Seguro;
import com.seguros.consulta.repository.SeguroRepository;
import com.seguros.consulta.service.SeguroService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeguroServiceImpl implements SeguroService {

    private final SeguroRepository seguroRepository;

    public SeguroServiceImpl(SeguroRepository seguroRepository) {
        this.seguroRepository = seguroRepository;
    }


    @Override
    public List<Seguro> obtenerTodos() {
        return seguroRepository.findAll();
    }

    @Override
    public Seguro obtenerPorId(Long id) {
        return seguroRepository.findById(id)
                .orElseThrow(() ->
                        new SeguroNotFoundException(
                                "No existe un seguro con el id: " + id
                        )
                );
    }
}
