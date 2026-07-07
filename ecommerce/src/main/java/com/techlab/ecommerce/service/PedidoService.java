package com.techlab.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.techlab.ecommerce.model.Pedido;
import com.techlab.ecommerce.exception.LineaProductoNoEncontradoException;
import com.techlab.ecommerce.exception.PedidoNoEncontradoException;
import com.techlab.ecommerce.exception.StockInsufiecienteException;
import com.techlab.ecommerce.model.LineaPedido;
import com.techlab.ecommerce.model.Producto;
import com.techlab.ecommerce.repository.PedidoRepository;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    private final PedidoRepository repository;
    private final ProductoService productoService;

    public PedidoService(PedidoRepository repository, ProductoService productoService) {
        this.repository = repository;
        this.productoService = productoService;
    }

    @Transactional
    public Pedido registrarPedido(Pedido p) {
        List<LineaPedido> lineasRecibidas = new ArrayList<>(p.getLineas());
        p.getLineas().clear();

        for (LineaPedido linea : lineasRecibidas) {
            Producto productoPedido = productoService.buscarPorId(linea.getProducto().getId());

            if (productoPedido.getStock() < linea.getCantidad()) {
                throw new StockInsufiecienteException(
                        "Stock insuficiente para el producto: " + productoPedido.getNombre());
            }

            productoPedido.setStock(productoPedido.getStock() - linea.getCantidad());

            linea.setProducto(productoPedido);
            linea.setPrecioUnitario(productoPedido.getPrecio());
            p.agregarLinea(linea);
        }

        return repository.save(p);
    }

    @Transactional
    public LineaPedido creaLineaPedido(Integer pedido_id, Integer producto_id, Integer cantidad) {
        Pedido pedido = buscarPedidoId(pedido_id);
        Producto producto = productoService.buscarPorId(producto_id);

        if (producto.getStock() < cantidad) {
            throw new StockInsufiecienteException("Stock insuficiente para el producto: " + producto.getNombre());
        }
        producto.setStock(producto.getStock() - cantidad);

        Optional<LineaPedido> lineaExistente = pedido.getLineas().stream()
                .filter(l -> l.getProducto().getId().equals(producto_id))
                .findFirst();

        if (lineaExistente.isPresent()) {
            LineaPedido linea = lineaExistente.get();
            linea.setCantidad(linea.getCantidad() + cantidad);
            pedido.recalcularTotal();
            repository.save(pedido);
            return linea;
        } else {
            // Si no existe, mantiene tu comportamiento original perfectamente
            LineaPedido nuevaLinea = new LineaPedido(pedido, producto, cantidad);
            pedido.agregarLinea(nuevaLinea);
            repository.save(pedido);
            return nuevaLinea;
        }
    }

    public Pedido buscarPedidoId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new PedidoNoEncontradoException("El pedido con id " + id + " no existe"));
    }

    public LineaPedido buscarLineaPorId(Pedido pedido, Integer idLineaPedido) {
        return pedido.getLineas().stream()
                .filter(linea -> linea.getId() == idLineaPedido)
                .findFirst()
                .orElseThrow(() -> new LineaProductoNoEncontradoException(
                        "La línea de producto no se encuentra en este pedido"));
    }

    @Transactional
    public void quitarProducto(Integer idPedido, Integer idLineaPedido) {
        Pedido pedido = buscarPedidoId(idPedido);
        LineaPedido linea = buscarLineaPorId(pedido, idLineaPedido);
        Producto producto = linea.getProducto();

        producto.setStock(producto.getStock() + linea.getCantidad());

        pedido.quitarLinea(linea);
        repository.save(pedido);
    }

    public List<Pedido> listarPedidos() {
        return repository.findAll();
    }

    @Transactional
    public void eliminarPedido(Integer idPedido) {
        Pedido pedido = buscarPedidoId(idPedido);

        for (LineaPedido linea : pedido.getLineas()) {
            Producto producto = linea.getProducto();
            producto.setStock(producto.getStock() + linea.getCantidad());
        }

        repository.delete(pedido);
    }

    @Transactional
    public Pedido vaciarPedido(Integer idPedido) {
        Pedido pedido = buscarPedidoId(idPedido);
        
        for (LineaPedido linea : pedido.getLineas()) {
            Producto producto = linea.getProducto();
            producto.setStock(producto.getStock() + linea.getCantidad());
        }

        pedido.getLineas().clear();
        pedido.setCostoTotal(0.0);

        return repository.save(pedido);
    }

}
