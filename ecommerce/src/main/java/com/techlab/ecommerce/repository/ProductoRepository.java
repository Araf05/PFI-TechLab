package com.techlab.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.techlab.ecommerce.model.Producto;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByNombreContaining(String nombre);

    List<Producto> findByCategoriaNombre(@Param("categoria") String categoria);
}
