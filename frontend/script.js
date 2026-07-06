const API_URL = "http://localhost:8080/productos";

// Estado de la aplicación
let productos = [];
let carrito = [];

// Elementos del DOM - Navegación
const vistaCliente = document.getElementById("vista-cliente");
const vistaAdmin = document.getElementById("vista-admin");
const btnCatalogo = document.getElementById("btn-catalogo");
const btnAdmin = document.getElementById("btn-admin");

// Elementos del DOM - Formulario
const formProducto = document.getElementById("form-producto");
const formTitulo = document.getElementById("form-titulo");
const inputId = document.getElementById("prod-id");
const inputNombre = document.getElementById("prod-nombre");
const inputDescripcion = document.getElementById("prod-descripcion");
const inputPrecio = document.getElementById("prod-precio");
const inputStock = document.getElementById("prod-stock");
const btnCancelarEdicion = document.getElementById("btn-cancelar-edicion");

// --- INTERCAMBIO DE VISTAS ---
btnCatalogo.addEventListener("click", () => {
    vistaCliente.classList.remove("hidden");
    vistaAdmin.classList.add("hidden");
});

btnAdmin.addEventListener("click", () => {
    vistaAdmin.classList.remove("hidden");
    vistaCliente.classList.add("hidden");
});

// --- CONSUMO DE API (FETCH) ---

// Obtener todos los productos (READ)
async function cargarProductos() {
    try {
        const res = await fetch(API_URL);
        productos = await res.json();
        renderizarCatalogo();
        renderizarTablaAdmin();
    } catch (error) {
        console.error("Error al buscar productos:", error);
    }
}

// Guardar o Actualizar Producto (CREATE / UPDATE)
formProducto.addEventListener("submit", async (e) => {
    e.preventDefault();

    const datos = {
        nombre: inputNombre.value,
        descripcion: inputDescripcion.value,
        precio: parseFloat(inputPrecio.value),
        stock: parseInt(inputStock.value),
        categoria: null // Lo dejamos null temporalmente como tus datos de prueba
    };

    const id = inputId.value;
    const esEdicion = id !== "";
    
    const url = esEdicion ? `${API_URL}/${id}` : API_URL;
    const method = esEdicion ? "PUT" : "POST";

    try {
        const res = await fetch(url, {
            method: method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(datos)
        });

        if (res.ok) {
            formProducto.reset();
            cancelarEdicion();
            cargarProductos(); // Recargar vistas
        }
    } catch (error) {
        console.error("Error al guardar producto:", error);
    }
});

// Eliminar Producto (DELETE)
async function eliminarProducto(id) {
    if (!confirm("¿Seguro que quieres eliminar este producto?")) return;

    try {
        const res = await fetch(`${API_URL}/${id}`, { method: "DELETE" });
        if (res.ok) cargarProductos();
    } catch (error) {
        console.error("Error al eliminar:", error);
    }
}

// --- RENDERIZADO DE INTERFAZ ---

// Renderizar el catálogo para los clientes
function renderizarCatalogo() {
    const contenedor = document.getElementById("contenedor-catalogo");
    contenedor.innerHTML = "";

    productos.forEach(p => {
        contenedor.innerHTML += `
            <div class="bg-white p-5 rounded-lg shadow-sm border border-gray-100 flex flex-col justify-between">
                <div>
                    <div class="bg-indigo-50 w-12 h-12 rounded flex items-center justify-center text-xl mb-3">📦</div>
                    <h3 class="text-lg font-bold text-gray-800">${p.nombre}</h3>
                    <p class="text-gray-500 text-sm my-2">${p.descripcion}</p>
                </div>
                <div class="mt-4 flex justify-between items-center">
                    <span class="text-xl font-extrabold text-indigo-600">$${p.precio}</span>
                    <button onclick="agregarAlCarrito(${p.id})" class="bg-indigo-600 text-white text-sm px-3 py-1.5 rounded font-semibold hover:bg-indigo-700 transition">
                        Agregar +
                    </button>
                </div>
            </div>
        `;
    });
}

// Renderizar la tabla del Administrador
function renderizarTablaAdmin() {
    const cuerpo = document.getElementById("tabla-productos-cuerpo");
    cuerpo.innerHTML = "";

    productos.forEach(p => {
        cuerpo.innerHTML += `
            <tr class="hover:bg-gray-50">
                <td class="p-3 text-sm font-medium text-gray-800">${p.nombre}</td>
                <td class="p-3 text-sm text-indigo-600 font-semibold">$${p.precio}</td>
                <td class="p-3 text-sm text-gray-500">${p.stock} u.</td>
                <td class="p-3 text-sm text-center space-x-2">
                    <button onclick="prepararEdicion(${p.id})" class="text-blue-600 hover:underline font-medium">Editar</button>
                    <button onclick="eliminarProducto(${p.id})" class="text-red-600 hover:underline font-medium">Eliminar</button>
                </td>
            </tr>
        `;
    });
}

// Cargar datos en el formulario para editar
function prepararEdicion(id) {
    const prod = productos.find(p => p.id === id);
    if (!prod) return;

    formTitulo.innerText = "Editar Producto";
    inputId.value = prod.id;
    inputNombre.value = prod.nombre;
    inputDescripcion.value = prod.descripcion;
    inputPrecio.value = prod.precio;
    inputStock.value = prod.stock;

    btnCancelarEdicion.classList.remove("hidden");
    window.scrollTo({ top: 0, behavior: 'smooth' }); // Sube la pantalla al formulario
}

btnCancelarEdicion.addEventListener("click", cancelarEdicion);

function cancelarEdicion() {
    formTitulo.innerText = "Agregar Nuevo Producto";
    inputId.value = "";
    formProducto.reset();
    btnCancelarEdicion.classList.add("hidden");
}

// --- LÓGICA DEL CARRITO (LADO CLIENTE) ---

function agregarAlCarrito(id) {
    const prod = productos.find(p => p.id === id);
    if (!prod) return;

    const itemEnCarrito = carrito.find(item => item.producto.id === id);

    if (itemEnCarrito) {
        itemEnCarrito.cantidad++;
    } else {
        carrito.push({ producto: prod, cantidad: 1 });
    }
    actualizarCarrito();
}

function actualizarCarrito() {
    const contenedor = document.getElementById("elementos-carrito");
    const contador = document.getElementById("contador-carrito");
    const totalElemento = document.getElementById("total-carrito");

    if (carrito.length === 0) {
        contenedor.innerHTML = `<p class="text-gray-400 text-center py-4">El carrito está vacío.</p>`;
        contador.innerText = "0";
        totalElemento.innerText = "$0.00";
        return;
    }

    contenedor.innerHTML = "";
    let total = 0;
    let cantArticulos = 0;

    carrito.forEach(item => {
        const subtotal = item.producto.precio * item.cantidad;
        total += subtotal;
        cantArticulos += item.cantidad;

        contenedor.innerHTML += `
            <div class="flex justify-between items-center py-2.5">
                <div>
                    <h4 class="font-medium text-sm text-gray-800">${item.producto.nombre}</h4>
                    <p class="text-xs text-gray-400">$${item.producto.precio} x ${item.cantidad}</p>
                </div>
                <span class="font-semibold text-sm text-gray-700">$${subtotal.toFixed(2)}</span>
            </div>
        `;
    });

    contador.innerText = cantArticulos;
    totalElemento.innerText = `$${total.toFixed(2)}`;
}

// Enviar Carrito al PedidoController del Backend
document.getElementById("btn-checkout").addEventListener("click", async () => {
    if (carrito.length === 0) {
        alert("El carrito está vacío.");
        return;
    }

    // Estructura sugerida para enviar a tu PedidoController
    // Adapta este objeto según lo que espere recibir tu backend
    const pedidoDTO = {
        items: carrito.map(item => ({
            productoId: item.producto.id,
            cantidad: item.cantidad
        }))
    };

    try {
        const res = await fetch("http://localhost:8080/pedidos", { // Cambia esto por tu endpoint real de pedidos
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(pedidoDTO)
        });

        if (res.ok) {
            alert("🎉 ¡Pedido realizado con éxito!");
            carrito = [];
            actualizarCarrito();
            cargarProductos(); // Recargar por si varió el stock en el backend
        } else {
            alert("Hubo un problema al procesar el pedido.");
        }
    } catch (error) {
        console.error("Error al enviar el pedido:", error);
    }
});

// Inicialización al cargar la página
document.addEventListener("DOMContentLoaded", cargarProductos);