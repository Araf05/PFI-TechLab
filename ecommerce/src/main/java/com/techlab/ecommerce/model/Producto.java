package com.techlab.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre del producto no puede estar vacío.")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    @PositiveOrZero(message = "El stock del producto no puede ser negativo.")
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Positive(message = "El precio del producto debe ser mayor a cero.")
    @Column(name = "precio", nullable = false)
    private Double precio;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Column(name = "disponible")
    private Boolean disponible = true;

    /// Constructores
    public Producto() {
    }

    public Producto(String nombre, String descripcion, Integer stock, Categoria categoria, Double precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.disponible = true;
    }

    /// Getters
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getStock() {
        return stock;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Double getPrecio() {
        return precio;
    }

    public Integer getId() {
        return this.id;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    /// Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }


    /// Override
    @Override
    public String toString() {
        String nombreCategoria = (categoria != null) ? categoria.getNombre() : "Sin categoría";
        return String.format(
                "│ %-4d │ %-24s │ %-24s | %-19s │ $%10.2f │ %-5d │ %-20b |",
                id,
                nombre,
                descripcion,
                nombreCategoria,
                precio,
                stock,
                disponible);
    }

}
