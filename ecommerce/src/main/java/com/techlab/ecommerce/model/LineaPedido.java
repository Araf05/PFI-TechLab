package com.techlab.ecommerce.model;

public class LineaPedido {
    private int id;
    private Producto producto;
    private int cantidad;
    private double precioUnitario;

    ///Constructor
    public LineaPedido() {
    }

    public LineaPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
    }

    /// Getters
    public int getId() {
        return id;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    /// Setters 
    public void setId(int id) {
        this.id = id;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double calcularSubtotal() {
        return precioUnitario * cantidad;
    }

    /// Override
    @Override
    public String toString() {
        return String.format(
                "│ %-4d │ %-20s │ %-15s │ $%10.2f │ %-5d │ $%8.2f |",
                id,
                producto.getNombre(),
                producto.getCategoria(),
                precioUnitario,
                cantidad,
                calcularSubtotal());
    }

}
