package com.techlab.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlab.ecommerce.model.Pedido;
import com.techlab.ecommerce.service.PedidoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "http://localhost:3000")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {
        return ResponseEntity.ok(service.listarPedidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedidoPorId(@PathVariable int id) {
        return ResponseEntity.ok(service.buscarPedidoId(id));
    }

    @PostMapping("")
    public ResponseEntity<Pedido> crearPedido(@Valid @RequestBody Pedido pedido) {
        Pedido creado = service.registrarPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @DeleteMapping("/{idPedido}/lineas/{idLinea}")
    public ResponseEntity<Void> quitarProducto(@PathVariable int idPedido, int idLinea) {
        service.quitarProducto(idPedido, idLinea);
        return ResponseEntity.ok().build();
    }

}
