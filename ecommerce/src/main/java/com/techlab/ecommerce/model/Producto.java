package com.techlab.ecommerce.model;

import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "productos")
@SoftDelete(columnName = "fecha_eliminado", strategy = SoftDeleteType.TIMESTAMP)
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "El nombre del producto no puede estar vacío.")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @PositiveOrZero(message = "El stock del producto no puede ser negativo.")
    @Column(name = "stock", nullable = false)
    private int stock;

    @Positive(message = "El precio del producto debe ser mayor a cero.")
    @Column(name = "precio", nullable = false)
    private double precio;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    /// Constructores
    public Producto() {
    }

    public Producto(String nombre, int stock, Categoria categoria, double precio) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    /// Getters
    public String getNombre() {
        return nombre;
    }

    public int getStock() {
        return stock;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public int getId() {
        return this.id;
    }

    /// Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setId(int contadorId) {
        this.id = contadorId;
    }

    /// Override
    @Override
    public String toString() {
        String nombreCategoria = (categoria != null) ? categoria.getNombre() : "Sin categoría";
        return String.format(
                "│ %-4d │ %-24s │ %-19s │ $%10.2f │ %-5d │",
                id,
                nombre,
                nombreCategoria,
                precio,
                stock);
    }

}
