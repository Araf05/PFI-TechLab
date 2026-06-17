package com.techlab.ecommerce;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.techlab.ecommerce.model.Categoria;
import com.techlab.ecommerce.model.Producto;
import com.techlab.ecommerce.service.CategoriaService;
import com.techlab.ecommerce.service.ProductoService;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	// @Bean
	// public CommandLineRunner cargarDatos(ProductoService productoService, CategoriaService categoriaService ) {
	// 	return args -> {
	// 		if(categoriaService.leerTodos().isEmpty()) {
	// 			Categoria almacen = categoriaService.crearCategoria(new Categoria("Almacén", "Productosde almacén"));
	// 			Categoria bebidas = categoriaService.crearCategoria(new Categoria("Bebidas", "Bebidas frías y calientes"));
	// 			Categoria panificados = categoriaService.crearCategoria(new Categoria("Panificados", "Productos de panadería"));
	// 			Categoria lacteos = categoriaService.crearCategoria(new Categoria("Lacteos", "Productos lacteos"));
				
	// 			productoService.creaProducto(new Producto("Cafe molido 500g", 500, bebidas, 3500));
	// 			productoService.creaProducto(new Producto("Pan Lactal", 100, panificados, 2500));
	// 			productoService.creaProducto(new Producto("Queso Regianito", 200, lacteos, 3000));
	// 		}
	// 	};
	// }

}
