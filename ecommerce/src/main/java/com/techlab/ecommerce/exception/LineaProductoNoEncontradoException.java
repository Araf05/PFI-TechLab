package com.techlab.ecommerce.exception;

public class LineaProductoNoEncontradoException extends RuntimeException {
    public LineaProductoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
