package com.techlab.ecommerce.model;

import org.hibernate.annotations.SoftDelete;
import org.hibernate.annotations.SoftDeleteType;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SoftDelete(columnName = "fecha_eliminado", strategy = SoftDeleteType.TIMESTAMP)
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "fecha", nullable = false)
    private Timestamp fecha = new Timestamp(System.currentTimeMillis());

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("lineasPedido")
    private List<LineaPedido> lineas = new ArrayList<>();

    @Positive(message = "El costo total del pedido debe ser mayor a cero.")
    @Column(name = "costoTotal", nullable = false)
    private Double costoTotal;

    
    public void setFecha() {
        fecha = new Timestamp(System.currentTimeMillis());
    }

    public void agregarLinea(LineaPedido linea) {
        this.lineas.add(linea);
        linea.setPedido(this);
        recalcularTotal();
    }

    public void quitarLinea(LineaPedido linea) {
        this.lineas.remove(linea);
        recalcularTotal();
    }

    private void recalcularTotal() {
        costoTotal = 0.0;

        for (LineaPedido linea : lineas) {
            costoTotal += linea.calcularSubtotal();
        }
    }

    /// Override
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\nPedido #").append(id).append("\n");
        sb.append("Fecha: ").append(fecha).append("\n");
        for (LineaPedido linea : lineas) {
            sb.append(linea).append("\n");
        }
        sb.append("TOTAL: $").append(costoTotal);

        return sb.toString();
    }

}
