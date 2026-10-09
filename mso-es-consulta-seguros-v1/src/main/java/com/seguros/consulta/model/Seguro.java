package com.seguros.consulta.model;

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
public class Seguro {

    private Long id;
    private String nombre;
    private String descripcion;
    private List<String> beneficios;
    private BigDecimal costoAnual;
    private Integer edadMinima;
    private Integer edadMaxima;
    private boolean requiereMujer;

}
