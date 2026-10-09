package com.seguros.validaciones.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class ValidacionRequest {

    @NotNull(message = "El id del seguro es obligatorio")
    @Positive(message = "El id del seguro debe ser mayor que cero")
    private Long idSeguros;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser anterior a la fecha actual")
    private LocalDate fechaNacimiento;

    @NotNull(message = "El id del género es obligatorio")
    @Min(value = 1, message = "El id del género debe ser 1 o 2")
    @Max(value = 2, message = "El id del género debe ser 1 o 2")
    private Integer idGenero;

    public Long getIdSeguros() {
        return idSeguros;
    }

    public void setIdSeguros(Long idSeguros) {
        this.idSeguros = idSeguros;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Integer idGenero) {
        this.idGenero = idGenero;
    }
}
