package com.techlab.ecommerce.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<String> handleProductoNoEncontrado(ProductoNoEncontradoException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(PedidoNoEncontradoException.class)
    public ResponseEntity<String> handlePedidoNoEncontrado(PedidoNoEncontradoException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(CategoriaNoEncontradaException.class)
    public ResponseEntity<String> handleCategoriaNoEncontrada(CategoriaNoEncontradaException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(StockInsufiecienteException.class)
    public ResponseEntity<String> handleStockInsuficiente(StockInsufiecienteException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(LineaProductoNoEncontradoException.class)
    public ResponseEntity<String> handleLineaProductoNoEncontrada(LineaProductoNoEncontradoException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

   
}
