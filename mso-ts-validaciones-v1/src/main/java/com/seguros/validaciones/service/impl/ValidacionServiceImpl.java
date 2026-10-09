package com.seguros.validaciones.service.impl;

import com.seguros.validaciones.client.SeguroClient;
import com.seguros.validaciones.dto.SeguroResponse;
import com.seguros.validaciones.dto.ValidacionRequest;
import com.seguros.validaciones.dto.ValidacionResponse;
import com.seguros.validaciones.exception.ClienteNoElegibleException;
import com.seguros.validaciones.exception.SeguroNoEncontradoException;
import com.seguros.validaciones.exception.ServiciosSegurosException;
import com.seguros.validaciones.service.ValidacionService;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class ValidacionServiceImpl implements ValidacionService {

    private final SeguroClient seguroClient;

    public ValidacionServiceImpl(SeguroClient seguroClient) {
        this.seguroClient = seguroClient;
    }

    @Override
    public ValidacionResponse validarCliente(ValidacionRequest request) {

        SeguroResponse seguro;

        try {
            seguro = seguroClient.obtenerSeguro(request.getIdSeguros());
        } catch (FeignException.NotFound exception) {
            throw new SeguroNoEncontradoException("No existe un seguro con el id: "+request.getIdSeguros());
        } catch (FeignException exception) {
            throw new ServiciosSegurosException("No fue posible consultar la información del seguro.");
        }

        int edad = calcularEdad(request.getFechaNacimiento());

        boolean edadValida = edad >= seguro.getEdadMinima() && edad <= seguro.getEdadMaxima();

        boolean generoValido = !seguro.isRequiereMujer() || request.getIdGenero() == 2;

        boolean elegible = edadValida && generoValido;

        if (!elegible) {
            throw new ClienteNoElegibleException(
                    "El cliente no cumple con los requisitos del seguro "
                    + seguro.getNombre() + "."
            );
        }

        return crearRespuesta(true,
                "El cliente cumple con los requisitos del seguro "
                        + seguro.getNombre()+ ".");
    }

    private int calcularEdad(LocalDate fechaNacimiento) {

        return Period.between(fechaNacimiento,LocalDate.now()).getYears();

    }

    private ValidacionResponse crearRespuesta(boolean elegible, String mensaje) {

        return ValidacionResponse.builder()
                .elegible(elegible)
                .mensaje(mensaje)
                .build();

    }
}
