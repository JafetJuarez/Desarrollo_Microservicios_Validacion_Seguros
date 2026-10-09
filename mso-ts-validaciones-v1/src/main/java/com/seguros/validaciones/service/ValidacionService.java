package com.seguros.validaciones.service;

import com.seguros.validaciones.dto.ValidacionRequest;
import com.seguros.validaciones.dto.ValidacionResponse;

public interface ValidacionService {

    ValidacionResponse validarCliente(ValidacionRequest request);

}
