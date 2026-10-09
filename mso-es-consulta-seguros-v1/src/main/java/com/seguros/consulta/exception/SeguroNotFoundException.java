package com.seguros.consulta.exception;

public class SeguroNotFoundException extends RuntimeException {
    public SeguroNotFoundException(String message) {
        super(message);
    }
}
