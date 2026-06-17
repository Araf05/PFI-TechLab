package com.techlab.ecommerce.exception;

public class StockInsufiecienteException extends RuntimeException {
    public StockInsufiecienteException(String mensaje) {
        super(mensaje);
    }
}
