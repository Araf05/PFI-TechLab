package com.techlab.ecommerce.service;

import java.util.List;
import java.util.ArrayList;

import com.techlab.ecommerce.model.Pedido;
import com.techlab.ecommerce.exception.LineaProductoNoEncontradoException;
import com.techlab.ecommerce.exception.PedidoNoEncontradoException;
import com.techlab.ecommerce.model.LineaPedido;
import com.techlab.ecommerce.model.Producto;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    private List<Pedido> listaPedidos = new ArrayList<Pedido>();
    private int contadorPedido = 1;
    private int contadorLineasPedido = 1;

    public Pedido registrarPedido(Pedido p) {

        p.setId(contadorPedido++);
        listaPedidos.add(p);

        return p;
    }

    public LineaPedido creaLineaPedido(Pedido pedido, LineaPedido linea) {

        linea.getProducto().setStock(
            linea.getProducto().getStock() - linea.getCantidad()
        );

        linea.setId(contadorLineasPedido++);
        pedido.agregarLinea(linea);

        return linea;
    }

    public Pedido buscarPedidoId(int id) {
        for (Pedido p : listaPedidos) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new PedidoNoEncontradoException("El pedido buscado no existe");
    }

    public LineaPedido buscarLineaPorId(Pedido pedido, int id) {
        for (LineaPedido linea : pedido.getLineasPedido()) {
            if (linea.getId() == id) {
                return linea;
            }
        }
        throw new LineaProductoNoEncontradoException("El producto que busca no se encuentra en este pedido");
    }

    public void quitarProducto(Pedido pedido, int idLineaPedido) {
        LineaPedido linea = buscarLineaPorId(pedido, idLineaPedido);
        Producto producto = linea.getProducto();

        producto.setStock(producto.getStock() + linea.getCantidad());

        pedido.quitarLinea(linea);
    }

    public List<Pedido> listarPedidos() {
        return listaPedidos;
    }

}
