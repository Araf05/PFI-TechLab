package com.techlab.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
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

    @Column(name = "imagen", nullable = true)
    private String imagen;

    @Column(name = "disponible", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean disponible;
}
