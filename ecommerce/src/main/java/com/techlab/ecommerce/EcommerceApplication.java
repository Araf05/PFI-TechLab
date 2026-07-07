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
	// 			Categoria familiar = categoriaService.crearCategoria(new Categoria(null,"Familiar", "Reglas sencillas y partidas dinámicas diseñadas para reunir a personas de todas las edades y disfrutar juntos."));
	// 			Categoria solitario = categoriaService.crearCategoria(new Categoria(null, "Solitario", "Desafíos individuales centrados en la lógica y la estrategia personal, ideales para jugar a tu propio ritmo."));
	// 			Categoria competitivo = categoriaService.crearCategoria(new Categoria(null, "Competitivo", "Partidas intensas donde mides tu estrategia directamente contra otros jugadores para superarlos y ganar."));

	// 			productoService.creaProducto(new Producto(
	// 					null,
	// 					"Imperio Galáctico",
	// 					"Un juego competitivo de estrategia espacial profunda donde los jugadores compiten por colonizar planetas, gestionar recursos y construir flotas para dominar la galaxia. Ideal para noches de juego intensas.",
	// 					45,
	// 					45000.0,
	// 					competitivo,
	// 					"https://images.unsplash.com/photo-1610890716171-6b1bb98ffd09?q=80&w=800&auto=format&fit=crop",
	// 					true));

	// 			productoService.creaProducto(new Producto(
	// 					null,
	// 					"El Enigma del Bosque",
	// 					"Un desafío en solitario donde encarnas a un explorador perdido en un bosque místico que cambia constantemente. Debes resolver acertijos lógicos y gestionar tu supervivencia antes de que caiga la noche.",
	// 					30,
	// 					28900.0,
	// 					solitario,
	// 					"https://images.unsplash.com/photo-1511512578047-dfb367046420?q=80&w=800&auto=format&fit=crop",
	// 					true));

	// 			productoService.creaProducto(new Producto(
	// 					null,
	// 					"Aventuras en la Granja",
	// 					"Un juego familiar sumamente divertido y dinámico. Los jugadores deben cooperar y competir amigablemente para construir la granja más próspera, cosechar vegetales y cuidar animales. Reglas sencillas para todas las edades.",
	// 					120,
	// 					19500.0,
	// 					familiar,
	// 					"https://images.unsplash.com/photo-1585504198199-20277593b94f?q=80&w=800&auto=format&fit=crop",
	// 					true));

	// 			productoService.creaProducto(new Producto(
	// 					null,
	// 					"Código Cyberpunk",
	// 					"Juego táctico competitivo de cartas y tablero ambientado en una megaciudad futurista. Hackea los sistemas de tus rivales, mejora tus implantes cibernéticos y controla las corporaciones para ganar el control de la red.",
	// 					60,
	// 					38000.0,
	// 					competitivo,
	// 					"https://images.unsplash.com/photo-1542751371-adc38448a05e?q=80&w=800&auto=format&fit=crop",
	// 					true));

	// 			productoService.creaProducto(new Producto(
	// 					null,
	// 					"Crónicas del Laberinto",
	// 					"Un atrapante juego solitario de mazmorras y cartas en el que avanzas habitación por habitación derrotando monstruos y recolectando reliquias. Cada partida es única gracias a su sistema de generación procedural.",
	// 					25,
	// 					32500.0,
	// 					solitario,
	// 					"https://images.unsplash.com/photo-1606167668584-78701c57f13d?q=80&w=800&auto=format&fit=crop",
	// 					true));
	// 		}
	// 	};
	// }

}
