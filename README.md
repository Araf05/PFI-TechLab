# PFI-TechLab

## Descripción del Proyecto

Una plataforma web de comercio electrónico estructurados por categorías. Permite a los usuarios explorar el catálogo con stock en tiempo real, gestionar un carrito de compras dinámico y procesar pedidos conectados a un backend robusto.

---

## Tecnologías Utilizadas

- **Backend:**
  - Java 25
  - Spring Boot
  - Spring Data JPA
  - Spring Boot Validation
  - Lombok
- **Base de Datos:**
  - MySql connector j
- **Frontend:**
  - HTML5
  - JavaScript
  - Tailwind CSS (vía CDN)

---

## Estructura de Carpetas (Backend)

```text
src/main/java/com/techlab/ecommerce/
│
├── config/              # Configuración global de CORS
├── controller/          # Endpoints de la API REST
├── exception/           # Manejo global de errores
├── model/               # Entidades de dominio con JPA y Lombok
├── repository/          # Interfaces de acceso a datos de Spring Data
└── service/             # Lógica de negocio y gestión de transacciones (@Transactional)

```

---

## Cómo Inicializar el Proyecto

### Backend (Spring Boot)

1. Clona el repositorio.
2. Crea localmente una base de datos vacía llamada `ecommerce` en tu gestor de MySQL.
3. Configura tus credenciales en src/main/resources/application.properties:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username={DB_USERNAME}
spring.datasource.password={DB_PASSWORD}
```

4. Ejecuta el proyecto desde tu IDE (IntelliJ, Eclipse, VS Code)

### Frontend

Abre el archivo index.html directamente en cualquier navegador web o utiliza la extensión Live Server de VS Code para levantarlo localmente.

---

## Endpoints Principales de la API

### Categorias (/categorias)

- **GET /categorias** - Listar todas las categorías
- **GET /categorias/{id}** - Buscar una categoría por id
- **POST /categorias** - Crear una nueva categoría

```JSON
{
  "nombre": "Nombre de categoría",
  "descripcion": "Descripcion de categoría"
}
```

- **PUT /categorias/{id}** - Modificar una categoría existente

```JSON
{
  "nombre": "Cooperativo",
  "descripcion": "Experiencias basadas en la sinergia colectiva donde los jugadores hacen equipo para superar los desafíos del tablero y ganar o perder juntos."
}
```

- **DELETE /categorias/{id}** - Eliminar una categoría

### Productos (/productos)

- **GET /productos** - Listar todos los productos del catálogo
- **GET /productos/{id}** - Buscar un producto por id
- **GET /productos/nombre/{nombre}** - Listar productos por nombre
- **GET /productos/categoria/{categoria}** - Listar productos por categoria
- **POST /productos** - Crear un nuevo producto

```JSON
{
  "nombre": "Crónicas de la Mazmorra",
  "descripcion": "Un emocionante juego de mesa cooperativo donde los jugadores exploran pasadizos ocultos, recolectan tesoros mágicos y combinan sus habilidades únicas para derrotar al jefe del laberinto antes de que se agote el tiempo.",
  "stock": 40,
  "categoria": {
      "id": 4
  },
  "imagen": "https://images.unsplash.com/photo-1606167668584-78701c57f13d?q=80&w=800&auto=format&fit=crop",
  "precio": 30000.00,
  "disponible": true
}
```

- **PUT /productos/{id}** - Modificar un producto existente

```JSON
{
  "nombre": "Crónicas de la Mazmorra",
  "descripcion": "Un emocionante juego de mesa cooperativo donde los jugadores exploran pasadizos ocultos, recolectan tesoros mágicos y combinan sus habilidades únicas para derrotar al jefe del laberinto antes de que se agote el tiempo.",
  "stock": 100,
  "categoria": {
      "id": 4
  },
  "imagen": "https://images.unsplash.com/photo-1606167668584-78701c57f13d?q=80&w=800&auto=format&fit=crop",
  "precio": 40000.00,
  "disponible": true
}
```

- **DELETE /productos/{id}** - Eliminar un producto

### Pedidos (/pedidos)

- **GET /pedidos** - Listar el historial de pedidos realizados
- **GET /pedidos/{id}** - Buscar un pedido por su id
- **POST /pedidos** - Confirmar el checkout del carrito y registrar un nuevo pedido

```JSON
{
  "lineasPedido": [
    {
      "producto": {
        "id": 1
      },
      "cantidad": 2
    },
    {
      "producto": {
        "id": 3
      },
      "cantidad": 1
    }
  ]
}
```

- **PUT /pedidos/{idPedido}/lineas** - Añadir un producto individual a un pedido existente (recibe LineaPedido)

```JSON
{
  "producto": {
    "id": 4
  },
  "cantidad": 1
}
```

- **PUT /pedidos/{idPedido}/vaciar** - Vaciar todas las líneas de un pedido y reponer el stock
- **DELETE /pedidos/{idPedido}** - Cancelar/Eliminar un pedido completo y reponer el stock en la tienda
