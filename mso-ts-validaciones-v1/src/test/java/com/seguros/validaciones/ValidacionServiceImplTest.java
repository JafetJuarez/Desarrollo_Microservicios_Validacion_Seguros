package com.seguros.validaciones;

import com.seguros.validaciones.client.SeguroClient;
import com.seguros.validaciones.dto.SeguroResponse;
import com.seguros.validaciones.dto.ValidacionRequest;
import com.seguros.validaciones.dto.ValidacionResponse;
import com.seguros.validaciones.service.impl.ValidacionServiceImpl;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidacionServiceImplTest {

    @Mock
    private SeguroClient seguroClient;

    @InjectMocks
    private ValidacionServiceImpl validacionService;

    @Test
    void debeValidarClienteElegible() {

        SeguroResponse seguro = SeguroResponse.builder()
                .id(1L)
                .nombre("VIDA")
                .descripcion("Protección financiera familiar")
                .beneficios(List.of(
                        "Beneficio económico",
                        "Servicio funerario"
                ))
                .costoAnual(BigDecimal.valueOf(700))
                .edadMinima(18)
                .edadMaxima(75)
                .requiereMujer(false)
                .build();

        ValidacionRequest request = new ValidacionRequest();
        request.setIdSeguros(1L);
        request.setNombre("Juan");
        request.setApellido("Perez");
        request.setFechaNacimiento(LocalDate.of(1995,5,20)
        );
        request.setIdGenero(1);

        when(seguroClient.obtenerSeguro(1L)).thenReturn(seguro);

        ValidacionResponse resultado = validacionService.validarCliente(request);

        assertTrue(resultado.isElegible());
        assertEquals("El cliente cumple con los requisitos del seguro VIDA.",resultado.getMensaje());

    }

    @Test
    void debeLanzarExcepcionCuandoClienteNoEsElegible() {

        SeguroResponse seguro = SeguroResponse.builder()
                .id(1L)
                .nombre("VIDA")
                .edadMinima(18)
                .edadMaxima(75)
                .requiereMujer(false)
                .build();

        ValidacionRequest request = new ValidacionRequest();
        request.setIdSeguros(1L);
        request.setNombre("Juan");
        request.setApellido("Perez");
        request.setFechaNacimiento(LocalDate.of(2015,5,20)
        );
        request.setIdGenero(1);

        when(seguroClient.obtenerSeguro(1L)).thenReturn(seguro);

        assertThrows(com.seguros.validaciones.exception.ClienteNoElegibleException.class,
                () -> validacionService.validarCliente(request));
    }

    @Test
    void debeLanzarExcepcionCuandoSeguroNoExiste() {

        ValidacionRequest request = new ValidacionRequest();
        request.setIdSeguros(99L);
        request.setNombre("Juan");
        request.setApellido("Perez");
        request.setFechaNacimiento(LocalDate.of(19955,5,20)
        );
        request.setIdGenero(1);

        when(seguroClient.obtenerSeguro(99L)).thenThrow(
                org.mockito.Mockito.mock(feign.FeignException.NotFound.class));

        assertThrows(com.seguros.validaciones.exception.SeguroNoEncontradoException.class,
                () -> validacionService.validarCliente(request));
    }

    @Test
    void debeLanzarExcepcionCuandoFallaConsultaDeSeguros() {


        ValidacionRequest request = new ValidacionRequest();
        request.setIdSeguros(1L);
        request.setNombre("Juan");
        request.setApellido("Perez");
        request.setFechaNacimiento(LocalDate.of(19955,5,20)
        );
        request.setIdGenero(1);

        when(seguroClient.obtenerSeguro(1L)).thenThrow(org.mockito.Mockito.mock(feign.FeignException.class));

        assertThrows(com.seguros.validaciones.exception.ServiciosSegurosException.class,
                () -> validacionService.validarCliente(request));

    }

}
