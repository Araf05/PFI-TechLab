const API_URL = "http://localhost:8080/productos";

// Estado de la aplicación
let productos = [];
let carrito = [];

// Elementos del DOM - Navegación
const vistaCliente = document.getElementById("vista-cliente");
const vistaAdmin = document.getElementById("vista-admin");
const btnCatalogo = document.getElementById("btn-catalogo");
const navLogo = document.getElementById("nav-logo");
const btnAdminFooter = document.getElementById("btn-admin-footer");
const btnVerCarrito = document.getElementById("btn-ver-carrito");

// Elementos del DOM - Formulario
const formProducto = document.getElementById("form-producto");
const formTitulo = document.getElementById("form-titulo");
const inputId = document.getElementById("prod-id");
const inputNombre = document.getElementById("prod-nombre");
const inputDescripcion = document.getElementById("prod-descripcion");
const inputPrecio = document.getElementById("prod-precio");
const inputStock = document.getElementById("prod-stock");
const btnCancelarEdicion = document.getElementById("btn-cancelar-edicion");

// --- INTERCAMBIO DE VISTAS (NAVBAR Y FOOTER) ---
function mostrarTienda() {
    vistaCliente.classList.remove("hidden");
    vistaAdmin.classList.add("hidden");
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

function mostrarAdmin() {
    vistaAdmin.classList.remove("hidden");
    vistaCliente.classList.add("hidden");
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

btnCatalogo.addEventListener("click", mostrarTienda);
navLogo.addEventListener("click", mostrarTienda);
btnAdminFooter.addEventListener("click", mostrarAdmin);

// Desplazamiento rápido al carrito lateral al tocar el botón del carrito en el navbar
btnVerCarrito.addEventListener("click", () => {
    mostrarTienda();
    document.getElementById("contador-carrito").scrollIntoView({ behavior: 'smooth', block: 'center' });
});

// --- CONSUMO DE API (FETCH) ---

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

formProducto.addEventListener("submit", async (e) => {
    e.preventDefault();

    const datos = {
        nombre: inputNombre.value,
        descripcion: inputDescripcion.value,
        precio: parseFloat(inputPrecio.value),
        stock: parseInt(inputStock.value),
        categoria: null
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
            cargarProductos();
        }
    } catch (error) {
        console.error("Error al guardar producto:", error);
    }
});

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

function renderizarCatalogo() {
    const contenedor = document.getElementById("contenedor-catalogo");
    contenedor.innerHTML = "";

    productos.forEach(p => {
        // Validación de categoría para renderizar un texto limpio
        const nombreCategoria = p.categoria ? p.categoria.nombre : "General";
        
        // Imagen por defecto por si el producto no trae una URL válida
        const imagenUrl = p.imagenUrl || "https://images.unsplash.com/photo-1610890716171-6b1bb98ffd09?q=80&w=800&auto=format&fit=crop";

        // Estilo condicional según las unidades de stock
        const stockTexto = p.stock > 0 ? `${p.stock} unidades disponibles` : "Sin Stock";
        const stockClase = p.stock > 5 ? "text-green-600 bg-green-50" : "text-red-600 bg-red-50";

        contenedor.innerHTML += `
            <div class="bg-white rounded-xl shadow-md overflow-hidden border border-gray-100 flex flex-col justify-between hover:shadow-lg transition duration-200">
                <div>
                    <div class="h-48 w-full bg-gray-200 relative">
                        <img src="${imagenUrl}" alt="${p.nombre}" class="w-full h-full object-cover">
                        <span class="absolute top-3 left-3 bg-indigo-600 text-white text-xs font-bold px-2.5 py-1 rounded-full uppercase tracking-wider shadow-sm">
                            ${nombreCategoria}
                        </span>
                    </div>
                    
                    <div class="p-5 space-y-2">
                        <h3 class="text-xl font-bold text-gray-800 tracking-tight">${p.nombre}</h3>
                        <p class="text-gray-600 text-sm line-clamp-3">${p.descripcion}</p>
                    </div>
                </div>

                <div class="px-5 pb-5 pt-2">
                    <div class="mb-4 flex items-center justify-between">
                        <span class="text-xs font-semibold px-2 py-1 rounded ${stockClase}">
                            📦 ${stockTexto}
                        </span>
                        <span class="text-2xl font-black text-indigo-600">$${p.precio.toLocaleString('es-AR')}</span>
                    </div>
                    <button onclick="agregarAlCarrito(${p.id})" 
                            ${p.stock === 0 ? 'disabled' : ''}
                            class="w-full bg-indigo-600 text-white py-2 rounded-lg font-bold hover:bg-indigo-700 disabled:bg-gray-300 disabled:cursor-not-allowed transition flex items-center justify-center gap-2">
                        ${p.stock > 0 ? 'Agregar al Carrito ➕' : 'Agotado 🚫'}
                    </button>
                </div>
            </div>
        `;
    });
}

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
    window.scrollTo({ top: 0, behavior: 'smooth' });
}

btnCancelarEdicion.addEventListener("click", cancelarEdicion);

function cancelarEdicion() {
    formTitulo.innerText = "Agregar Nuevo Producto";
    inputId.value = "";
    formProducto.reset();
    btnCancelarEdicion.classList.add("hidden");
}

// --- LÓGICA DEL CARRITO ---

function agregarAlCarrito(id) {
    const prod = productos.find(p => p.id === id);
    if (!prod || prod.stock === 0) return;

    const itemEnCarrito = carrito.find(item => item.producto.id === id);

    if (itemEnCarrito) {
        if (itemEnCarrito.cantidad < prod.stock) {
            itemEnCarrito.cantidad++;
        } else {
            alert("No puedes agregar más unidades de las disponibles en stock.");
            return;
        }
    } else {
        carrito.push({ producto: prod, cantidad: 1 });
    }
    actualizarCarrito();
}

function actualizarCarrito() {
    const contenedor = document.getElementById("elementos-carrito");
    const contadorLateral = document.getElementById("contador-carrito");
    const contadorNavbar = document.getElementById("contador-navbar");
    const totalElemento = document.getElementById("total-carrito");

    if (carrito.length === 0) {
        contenedor.innerHTML = `<p class="text-gray-400 text-center py-4">El carrito está vacío.</p>`;
        contadorLateral.innerText = "0";
        contadorNavbar.innerText = "0";
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

        // Decidimos qué ícono mostrar: si tiene más de 1, un signo menos; si tiene 1, el tacho de basura
        const iconoBoton = item.cantidad > 1 ? "➖" : "🗑️";
        const tituloBoton = item.cantidad > 1 ? "Restar 1 unidad" : "Quitar del carrito";

        contenedor.innerHTML += `
            <div class="flex justify-between items-center py-3 border-b border-gray-100 last:border-0">
                <div class="pr-2">
                    <h4 class="font-medium text-sm text-gray-800">${item.producto.nombre}</h4>
                    <p class="text-xs text-gray-400">$${item.producto.precio.toLocaleString('es-AR')} x ${item.cantidad}</p>
                </div>
                <div class="flex items-center space-x-3 flex-shrink-0">
                    <span class="font-semibold text-sm text-gray-700">$${subtotal.toFixed(2)}</span>
                    <button onclick="restarOQuitarDelCarrito(${item.producto.id})" 
                            title="${tituloBoton}"
                            class="text-xs bg-gray-100 hover:bg-red-50 hover:text-red-600 p-1.5 rounded transition">
                        ${iconoBoton}
                    </button>
                </div>
            </div>
        `;
    });

    // Inyectamos dinámicamente el botón de "Vaciar Carrito" al final de la lista de elementos
    contenedor.innerHTML += `
        <div class="pt-3 text-right">
            <button onclick="vaciarCarritoLocal()" 
                    class="text-xs text-red-500 hover:text-red-700 font-semibold transition flex items-center gap-1 ml-auto">
                🗑️ Vaciar carrito por completo
            </button>
        </div>
    `;

    contadorLateral.innerText = cantArticulos;
    contadorNavbar.innerText = cantArticulos;
    totalElemento.innerText = `$${total.toFixed(2)}`;
}

document.getElementById("btn-checkout").addEventListener("click", async () => {
    if (carrito.length === 0) {
        alert("El carrito está vacío.");
        return;
    }

    const pedidoDTO = {
        items: carrito.map(item => ({
            productoId: item.producto.id,
            cantidad: item.cantidad
        }))
    };

    try {
        const res = await fetch("http://localhost:8080/pedidos", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(pedidoDTO)
        });

        if (res.ok) {
            alert("🎉 ¡Pedido realizado con éxito!");
            carrito = [];
            actualizarCarrito();
            cargarProductos();
        } else {
            alert("Hubo un problema al procesar el pedido.");
        }
    } catch (error) {
        console.error("Error al enviar el pedido:", error);
    }
});

document.addEventListener("DOMContentLoaded", cargarProductos);

// Función para restar 1 unidad o eliminar por completo el producto si solo queda 1
function restarOQuitarDelCarrito(id) {
    const itemEnCarrito = carrito.find(item => item.producto.id === id);
    if (!itemEnCarrito) return;

    if (itemEnCarrito.cantidad > 1) {
        // Si hay más de uno, restamos una unidad
        itemEnCarrito.cantidad--;
    } else {
        // Si queda solo uno, filtramos el array para removerlo por completo
        carrito = carrito.filter(item => item.producto.id !== id);
    }
    
    // Volvemos a renderizar la interfaz del carrito para aplicar los cambios
    actualizarCarrito();
}

// Función para limpiar por completo el carrito localmente
function vaciarCarritoLocal() {
    if (carrito.length === 0) return;
    
    if (confirm("¿Estás seguro de que quieres vaciar todo el carrito?")) {
        carrito = [];
        actualizarCarrito();
    }
}