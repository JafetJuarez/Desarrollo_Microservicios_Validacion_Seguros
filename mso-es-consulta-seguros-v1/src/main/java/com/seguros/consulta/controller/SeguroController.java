package com.seguros.consulta.controller;

import com.seguros.consulta.dto.SeguroResponse;
import com.seguros.consulta.model.Seguro;
import com.seguros.consulta.service.SeguroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seguros")
public class SeguroController {

    private final SeguroService seguroService;

    public SeguroController(SeguroService seguroService) {
        this.seguroService = seguroService;
    }

    @GetMapping
    public ResponseEntity<List<SeguroResponse>> obtenerTodos() {

        List<SeguroResponse> seguros = seguroService.obtenerTodos()
                .stream()
                .map(this::convertirAResponse)
                .toList();

        return ResponseEntity.ok(seguros);

    }

    @GetMapping("/{id}")
    public ResponseEntity<SeguroResponse> obtenerPorId(
            @PathVariable Long id) {

        Seguro seguro = seguroService.obtenerPorId(id);

        return ResponseEntity.ok(convertirAResponse(seguro));

    }

    private SeguroResponse convertirAResponse(Seguro seguro) {

        return SeguroResponse.builder()
                .id(seguro.getId())
                .nombre(seguro.getNombre())
                .descripcion(seguro.getDescripcion())
                .beneficios(seguro.getBeneficios())
                .costoAnual(seguro.getCostoAnual())
                .edadMinima(seguro.getEdadMinima())
                .edadMaxima(seguro.getEdadMaxima())
                .requiereMujer(seguro.isRequiereMujer())
                .build();

    }

}
