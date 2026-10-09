package com.seguros.consulta.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeguroResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private List<String> beneficios;
    private BigDecimal costoAnual;
    private Integer edadMinima;
    private Integer edadMaxima;
    private boolean requiereMujer;

}
