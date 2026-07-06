package com.techlab.ecommerce.model;

import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@SoftDelete(columnName = "fecha_elminado", strategy = SoftDeleteType.TIMESTAMP)
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @NotBlank(message = "El nombre de la categoría no puede estar vacío.")
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @NotBlank(message = "La descripción de la categoría no puede estar vacío.")
    @Column(name = "descripcion", length = 200)
    private String descripcion;

}
