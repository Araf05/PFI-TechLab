package com.techlab.ecommerce.service;

import java.util.List;

import com.techlab.ecommerce.exception.ProductoNoEncontradoException;
import com.techlab.ecommerce.model.Producto;
import com.techlab.ecommerce.repository.ProductoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {
    private ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public Producto creaProducto(Producto p) {
        return repository.save(p);
    }

    public List<Producto> leerTodos() {
        return repository.findAll();
    }

    public Producto buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new ProductoNoEncontradoException("No se ha encontrado un producto con el id " + id));
    }

    public Producto actualizar(Integer id, Producto datos) {
        Producto p = buscarPorId(id);

        p.setNombre(datos.getNombre());
        p.setDescripcion(datos.getDescripcion());
        p.setPrecio(datos.getPrecio());
        p.setStock(datos.getStock());
        if(datos.getImagen() != null) {
            p.setImagen(datos.getImagen());
        }
        p.setCategoria(datos.getCategoria());

        return repository.save(p);
    }

    public void eliminar(Integer id) {
        Producto p = buscarPorId(id);
        p.setDisponible(false);
        repository.save(p);
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return repository.findByNombreContaining(nombre);
    }

    public List<Producto> buscarPorCategoria(String categoria) {
        return repository.findByCategoriaNombreContainingIgnoreCase(categoria);
    }
}
