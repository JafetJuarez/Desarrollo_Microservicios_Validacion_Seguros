package com.seguros.consulta;

import com.seguros.consulta.exception.SeguroNotFoundException;
import com.seguros.consulta.model.Seguro;
import com.seguros.consulta.repository.SeguroRepository;
import com.seguros.consulta.service.impl.SeguroServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class SeguroServiceImplTest {

    @Mock
    private SeguroRepository seguroRepository;

    @InjectMocks
    private SeguroServiceImpl seguroService;

    @Test
    void debeObtenerTodosLosSeguros(){

        List<Seguro> seguros = List.of(
                Seguro.builder()
                        .id(1L)
                        .nombre("VIDA")
                        .costoAnual(BigDecimal.valueOf(700))
                        .edadMinima(75)
                        .edadMinima(18)
                        .build(),

                Seguro.builder()
                        .id(2L)
                        .nombre("INFARTO")
                        .costoAnual(BigDecimal.valueOf(400))
                        .edadMinima(15)
                        .edadMaxima(64)
                        .build()

        );

        when(seguroRepository.findAll()).thenReturn(seguros);

        List<Seguro> resultado = seguroService.obtenerTodos();

        assertEquals(2,resultado.size());
        assertEquals("VIDA",resultado.get(0).getNombre());
        assertEquals("INFARTO",resultado.get(1).getNombre());

    }

    @Test
    void debeObtenerSeguroPorIdCuandoExiste(){
        Seguro seguro = Seguro.builder()
                .id(1L)
                .nombre("VIDA")
                .build();

        when(seguroRepository.findById(1L)).thenReturn(Optional.of(seguro));

        Seguro resultado = seguroService.obtenerPorId(1L);

        assertEquals(1L,resultado.getId());
        assertEquals("VIDA",resultado.getNombre());
    }

    @Test
    void debeLanzarExcepcionCuandoSeguroNoExiste(){
        when(seguroRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(SeguroNotFoundException.class,
                () -> seguroService.obtenerPorId(99L));
    }

}
