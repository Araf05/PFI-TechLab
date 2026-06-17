package com.techlab.ecommerce.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class Pedido {
    private int id;
    private Timestamp fecha = new Timestamp(System.currentTimeMillis());
    private List<LineaPedido> lineasPedido = new ArrayList<>();
    private double costoTotal;

    /// Constructor
    public Pedido() {
    }

    /// Getters
    public int getId() {
        return id;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public List<LineaPedido> getLineasPedido() {
        return lineasPedido;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    /// Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setFecha() {
        fecha = new Timestamp(System.currentTimeMillis());
    }

    public void agregarLinea(LineaPedido linea) {
        this.lineasPedido.add(linea);
        recalcularTotal();
    }

    public void quitarLinea(LineaPedido linea) {
        this.lineasPedido.remove(linea);
        recalcularTotal();
    }

    private void recalcularTotal() {
        costoTotal = 0;

        for (LineaPedido linea : lineasPedido) {
            costoTotal += linea.calcularSubtotal();
        }
    }

    /// Override
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\nPedido #").append(id).append("\n");
        sb.append("Fecha: ").append(fecha).append("\n");
        for (LineaPedido linea : lineasPedido) {
            sb.append(linea).append("\n");
        }
        sb.append("TOTAL: $").append(costoTotal);

        return sb.toString();
    }

}
