package com.seguros.validaciones.controller;

import com.seguros.validaciones.dto.ValidacionRequest;
import com.seguros.validaciones.dto.ValidacionResponse;
import com.seguros.validaciones.service.ValidacionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/validaciones")
public class ValidacionController {

    private final ValidacionService validacionService;

    public ValidacionController(ValidacionService validacionService) {
        this.validacionService = validacionService;
    }

    @PostMapping
    public ResponseEntity<ValidacionResponse> validarCliente(
            @Valid @RequestBody ValidacionRequest request) {
        ValidacionResponse response = validacionService.validarCliente(request);

        return ResponseEntity.ok(response);
    }
}
