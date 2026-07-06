package com.techlab.ecommerce.service;

import com.techlab.ecommerce.exception.CategoriaNoEncontradaException;
import com.techlab.ecommerce.model.Categoria;
import com.techlab.ecommerce.repository.CategoriaRepository;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
    private CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public Categoria crearCategoria(Categoria c){
        return repository.save(c);
    }

    public List<Categoria> leerTodos() {
        return repository.findAll();
    }

    public Categoria buscarPorId(Integer id) {
        return repository.findById(id)
            .orElseThrow(() -> new CategoriaNoEncontradaException("No se ha encontrado una categoría con el id " + id));
    }

    public Categoria actualizar(Integer id, Categoria datos) {
        Categoria c = buscarPorId(id);

        c.setNombre(datos.getNombre());
        c.setDescripcion(datos.getDescripcion());

        return repository.save(c);
    }

    public void eliminar(Integer id) {
        Categoria c = buscarPorId(id);
        repository.delete(c);
    }
}
