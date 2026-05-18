// ============================================================
// GESTOR DINÁMICO DE EVENTOS
// Práctica Final - Lenguaje de Marcas - JavaScript en la Web
// ============================================================
 
// ============================================================
// APARTADO 1: Creación de la estructura base y generación
// dinámica del DOM
// Toda la interfaz se genera desde JavaScript, partiendo de
// un único contenedor vacío en el HTML (#app)
// ============================================================
 
// Seleccionamos el contenedor principal del HTML
const app = document.getElementById('app');
 
// Creamos el encabezado principal de la aplicación
const header = document.createElement('header');
const titulo = document.createElement('h1');
titulo.textContent = 'Gestor de Eventos';
header.appendChild(titulo); // Insertamos el título dentro del header
 
// Creamos la sección del formulario y le asignamos un id para poder acceder a ella
const seccionFormulario = document.createElement('section');
seccionFormulario.id = 'seccion-formulario';
 
// Creamos el título de la sección del formulario
const tituloFormulario = document.createElement('h2');
tituloFormulario.textContent = 'Crear nuevo evento';
seccionFormulario.appendChild(tituloFormulario);
 
// Creamos la sección donde se mostrarán los eventos creados
const seccionEventos = document.createElement('section');
seccionEventos.id = 'seccion-eventos';
 
// Creamos el título de la sección de eventos
const tituloEventos = document.createElement('h2');
tituloEventos.textContent = 'Eventos creados';
seccionEventos.appendChild(tituloEventos);
 
// Insertamos todas las secciones dentro del contenedor principal #app
app.appendChild(header);
app.appendChild(seccionFormulario);
app.appendChild(seccionEventos);
 
// ============================================================
// APARTADO 2: Selección y acceso a elementos del DOM
// Accedemos a los elementos creados mediante getElementById
// para poder manipularlos posteriormente
// ============================================================
 
// Seleccionamos las secciones por su id para poder referenciarlas después
const formularioSeccion = document.getElementById('seccion-formulario');
const eventosSeccion = document.getElementById('seccion-eventos');
 
// Verificamos en consola que la selección es correcta
console.log('Formulario encontrado:', formularioSeccion);
console.log('Sección de eventos encontrada:', eventosSeccion);
 
// ============================================================
// APARTADO 3: Manipulación de contenido y estilos dinámicos
// Modificamos textos, estilos y clases CSS desde JavaScript
// para reflejar el estado inicial de la aplicación
// ============================================================
 
// Actualizamos el texto del título de eventos con el contador inicial
tituloEventos.textContent = 'Eventos creados (0)';
 
// Aplicamos un estilo directamente desde JavaScript (borde superior azul)
seccionFormulario.style.borderTop = '4px solid #4a90d9';
 
// Añadimos una clase CSS para marcar visualmente la sección del formulario
seccionFormulario.classList.add('seccion-activa');
 
// ============================================================
// APARTADO 4: Creación dinámica de eventos desde un formulario
// Construimos el formulario completo mediante JavaScript,
// con todos sus campos e interacción sin recargar la página
// ============================================================
 
// Creamos el elemento formulario y desactivamos la validación HTML nativa
// para usar nuestra propia validación JavaScript (Apartado 11)
const form = document.createElement('form');
form.id = 'form-evento';
form.setAttribute('novalidate', ''); // Desactiva validación HTML para usar la nuestra
 
// --- Campo: Título ---
const labelTitulo = document.createElement('label');
labelTitulo.textContent = 'Título:';
const inputTitulo = document.createElement('input');
inputTitulo.type = 'text';
inputTitulo.id = 'input-titulo';
inputTitulo.placeholder = 'Nombre del evento';
inputTitulo.required = true;
 
// --- Campo: Descripción ---
const labelDesc = document.createElement('label');
labelDesc.textContent = 'Descripción:';
const inputDesc = document.createElement('textarea');
inputDesc.id = 'input-descripcion';
inputDesc.placeholder = 'Describe el evento...';
inputDesc.required = true;
 
// --- Campo: Fecha ---
const labelFecha = document.createElement('label');
labelFecha.textContent = 'Fecha:';
const inputFecha = document.createElement('input');
inputFecha.type = 'date';
inputFecha.id = 'input-fecha';
inputFecha.required = true;
 
// --- Campo: Categoría (select dinámico) ---
const labelCategoria = document.createElement('label');
labelCategoria.textContent = 'Categoría:';
const selectCategoria = document.createElement('select');
selectCategoria.id = 'input-categoria';
 
// Generamos las opciones del select dinámicamente con forEach
const categorias = ['Selecciona...', 'Trabajo', 'Personal', 'Deporte', 'Ocio'];
categorias.forEach(cat => {
    const option = document.createElement('option');
    option.value = cat === 'Selecciona...' ? '' : cat; // La opción vacía no tiene valor
    option.textContent = cat;
    selectCategoria.appendChild(option);
});
 
// --- Botón de envío ---
const botonEnviar = document.createElement('button');
botonEnviar.type = 'submit';
botonEnviar.textContent = 'Crear Evento';
 
// --- Contenedor para mensajes de error de validación ---
const mensajeError = document.createElement('div');
mensajeError.id = 'mensaje-error';
 
// Ensamblamos todos los campos dentro del formulario
form.appendChild(labelTitulo);
form.appendChild(inputTitulo);
form.appendChild(labelDesc);
form.appendChild(inputDesc);
form.appendChild(labelFecha);
form.appendChild(inputFecha);
form.appendChild(labelCategoria);
form.appendChild(selectCategoria);
form.appendChild(mensajeError);
form.appendChild(botonEnviar);
 
// Insertamos el formulario dentro de la sección correspondiente
seccionFormulario.appendChild(form);
 
// ============================================================
// APARTADOS 5, 6 y 7: Gestión de eventos del usuario,
// delegación de eventos e identificación por jerarquía del DOM
// ============================================================
 
// Array que almacena todos los eventos creados
let listaEventos = [];
 
// Contador de eventos para mostrar en el título
let contadorEventos = 0;
 
// Función reutilizable que construye y devuelve una tarjeta de evento
// Recibe un objeto evento con sus datos y genera su estructura HTML
function crearTarjetaEvento(evento) {
    // Creamos el contenedor principal de la tarjeta
    const tarjeta = document.createElement('div');
    tarjeta.classList.add('tarjeta-evento');
 
    // APARTADO 8: Asignamos atributos data-* personalizados para
    // almacenar el id, categoría y estado de favorito del evento
    tarjeta.dataset.id = evento.id;
    tarjeta.dataset.categoria = evento.categoria;
    tarjeta.dataset.favorito = 'false';
 
    // Construimos el contenido HTML de la tarjeta dinámicamente
    tarjeta.innerHTML = `
        <div class="tarjeta-header">
            <h3>${evento.titulo}</h3>
            <span class="categoria-badge">${evento.categoria}</span>
        </div>
        <p class="tarjeta-desc">${evento.descripcion}</p>
        <p class="tarjeta-fecha">📅 ${evento.fecha}</p>
        <div class="tarjeta-botones">
            <button class="btn-favorito">⭐ Favorito</button>
            <button class="btn-eliminar">🗑️ Eliminar</button>
        </div>
    `;
 
    // APARTADO 5: Eventos de hover para cambio visual al pasar el cursor
    tarjeta.addEventListener('mouseenter', () => {
        tarjeta.classList.add('evento-seleccionado'); // Aplica clase de selección
    });
    tarjeta.addEventListener('mouseleave', () => {
        tarjeta.classList.remove('evento-seleccionado'); // Elimina clase al salir
    });
 
    return tarjeta;
}
 
// APARTADO 12: Función que muestra u oculta el mensaje de lista vacía
function actualizarMensajeVacio() {
    const tarjetas = seccionEventos.querySelectorAll('.tarjeta-evento');
    let mensajeVacio = document.getElementById('mensaje-vacio');
 
    if (tarjetas.length === 0) {
        // Si no hay tarjetas y no existe el mensaje, lo creamos
        if (!mensajeVacio) {
            mensajeVacio = document.createElement('p');
            mensajeVacio.id = 'mensaje-vacio';
            mensajeVacio.textContent = '📭 No hay eventos. ¡Crea el primero!';
            seccionEventos.appendChild(mensajeVacio);
        }
    } else {
        // Si hay tarjetas y existe el mensaje, lo eliminamos
        if (mensajeVacio) mensajeVacio.remove();
    }
}
 
// APARTADO 6: Delegación de eventos
// En lugar de añadir un listener a cada botón individualmente,
// usamos un único listener en el contenedor padre (seccionEventos)
// Esto permite gestionar correctamente elementos creados dinámicamente
seccionEventos.addEventListener('click', function(e) {
 
    // APARTADO 7: Usamos closest() para navegar por la jerarquía del DOM
    // y encontrar el contenedor .tarjeta-evento más cercano al elemento clicado
    const tarjeta = e.target.closest('.tarjeta-evento');
    if (!tarjeta) return; // Si el clic no fue dentro de una tarjeta, salimos
 
    // Acción: marcar/desmarcar como FAVORITO
    if (e.target.classList.contains('btn-favorito')) {
        // Leemos el estado actual del atributo data-favorito
        const esFavorito = tarjeta.dataset.favorito === 'true';
 
        // Cambiamos el atributo data-favorito al estado contrario
        tarjeta.dataset.favorito = esFavorito ? 'false' : 'true';
 
        // Alternamos la clase CSS que aplica el borde dorado
        tarjeta.classList.toggle('evento-favorito');
 
        // Actualizamos el texto e icono del botón según el estado
        e.target.textContent = esFavorito ? '⭐ Favorito' : '💛 Favorito';
        e.target.style.fontWeight = esFavorito ? 'normal' : 'bold';
    }
 
    // Acción: ELIMINAR la tarjeta del DOM
    if (e.target.classList.contains('btn-eliminar')) {
        tarjeta.remove(); // Eliminamos la tarjeta del DOM
        contadorEventos--;
        tituloEventos.textContent = `Eventos creados (${contadorEventos})`;
        actualizarMensajeVacio(); // Comprobamos si hay que mostrar el mensaje vacío
    }
});
 
// ============================================================
// APARTADO 11: Validación de formularios
// Validamos los datos con JavaScript antes de crear el evento,
// mostrando mensajes de error personalizados al usuario
// ============================================================
 
form.addEventListener('submit', function(e) {
    e.preventDefault(); // Evitamos que la página se recargue al enviar
 
    // Recogemos los valores de los campos eliminando espacios innecesarios
    const titulo = inputTitulo.value.trim();
    const descripcion = inputDesc.value.trim();
    const fecha = inputFecha.value;
    const categoria = selectCategoria.value;
 
    // Limpiamos cualquier error visual previo antes de revalidar
    mensajeError.textContent = '';
    inputTitulo.style.border = '1px solid #ccc';
    inputDesc.style.border = '1px solid #ccc';
    inputFecha.style.border = '1px solid #ccc';
    selectCategoria.style.border = '1px solid #ccc';
 
    // Variable para controlar si hay algún error
    let hayError = false;
 
    // Comprobamos cada campo y mostramos el error correspondiente
    if (titulo.length < 3) {
        mensajeError.textContent = '⚠️ El título debe tener al menos 3 caracteres.';
        inputTitulo.style.border = '2px solid red';
        hayError = true;
    } else if (descripcion.length < 5) {
        mensajeError.textContent = '⚠️ La descripción es demasiado corta.';
        inputDesc.style.border = '2px solid red';
        hayError = true;
    } else if (!fecha) {
        mensajeError.textContent = '⚠️ Debes seleccionar una fecha.';
        inputFecha.style.border = '2px solid red';
        hayError = true;
    } else if (!categoria) {
        mensajeError.textContent = '⚠️ Debes seleccionar una categoría.';
        selectCategoria.style.border = '2px solid red';
        hayError = true;
    }
 
    // Si hay errores, detenemos el proceso y no creamos el evento
    if (hayError) return;
 
    // Si la validación es correcta, creamos el objeto evento
    const nuevoEvento = {
        id: Date.now(), // Usamos la marca de tiempo como id único
        titulo,
        descripcion,
        fecha,
        categoria
    };
 
    // Añadimos el evento al array de eventos
    listaEventos.push(nuevoEvento);
    contadorEventos++;
 
    // Creamos la tarjeta visual y la insertamos en el DOM
    const tarjeta = crearTarjetaEvento(nuevoEvento);
    seccionEventos.appendChild(tarjeta);
 
    // Actualizamos el contador en el título
    tituloEventos.textContent = `Eventos creados (${contadorEventos})`;
 
    // Comprobamos si hay que ocultar el mensaje de lista vacía
    actualizarMensajeVacio();
 
    // Limpiamos el formulario para el siguiente evento
    form.reset();
});
 
// ============================================================
// APARTADO 8: Manipulación de atributos y datos personalizados
// Creamos la barra de búsqueda y filtro que usan los atributos
// data-* de las tarjetas para filtrar eventos
// ============================================================
 
// Creamos el contenedor de búsqueda y filtro
const divBusqueda = document.createElement('div');
divBusqueda.id = 'div-busqueda';
 
// Campo de búsqueda por texto
const inputBusqueda = document.createElement('input');
inputBusqueda.type = 'text';
inputBusqueda.id = 'input-busqueda';
inputBusqueda.placeholder = '🔍 Buscar evento por título...';
 
// Select de filtro por categoría
const selectFiltro = document.createElement('select');
selectFiltro.id = 'select-filtro';
const opcionesFiltro = ['Todas las categorías', 'Trabajo', 'Personal', 'Deporte', 'Ocio'];
opcionesFiltro.forEach(op => {
    const option = document.createElement('option');
    option.value = op === 'Todas las categorías' ? '' : op;
    option.textContent = op;
    selectFiltro.appendChild(option);
});
 
divBusqueda.appendChild(inputBusqueda);
divBusqueda.appendChild(selectFiltro);
 
// Insertamos la barra de búsqueda entre el formulario y los eventos
app.insertBefore(divBusqueda, seccionEventos);
 
// ============================================================
// APARTADO 10: Búsqueda y filtrado dinámico
// Filtramos las tarjetas en tiempo real usando los atributos
// data-* y el texto introducido por el usuario
// ============================================================
 
function filtrarEventos() {
    const textoBusqueda = inputBusqueda.value.toLowerCase();
    const categoriaFiltro = selectFiltro.value;
 
    // Seleccionamos todas las tarjetas actuales del DOM
    const tarjetas = seccionEventos.querySelectorAll('.tarjeta-evento');
 
    tarjetas.forEach(tarjeta => {
        const tituloTarjeta = tarjeta.querySelector('h3').textContent.toLowerCase();
        // Usamos el atributo data-categoria para comparar con el filtro
        const categoria = tarjeta.dataset.categoria;
 
        const coincideTexto = tituloTarjeta.includes(textoBusqueda);
        const coincideCategoria = categoriaFiltro === '' || categoria === categoriaFiltro;
 
        // Mostramos u ocultamos la tarjeta según si cumple ambos filtros
        tarjeta.style.display = (coincideTexto && coincideCategoria) ? 'block' : 'none';
    });
}
 
// Escuchamos el evento 'input' para filtrar mientras el usuario escribe
inputBusqueda.addEventListener('input', filtrarEventos);
 
// Escuchamos el cambio en el select de categoría para filtrar
selectFiltro.addEventListener('change', filtrarEventos);
 
// ============================================================
// APARTADO 12: Mejora de la experiencia de usuario
// Añadimos feedback visual, contador dinámico y diseño responsive
// ============================================================
 
// Feedback visual en el campo título: borde verde si es válido, naranja si es corto
inputTitulo.addEventListener('input', function() {
    const longitud = inputTitulo.value.length;
    if (longitud === 0) {
        inputTitulo.style.borderColor = '#ccc'; // Sin contenido: color neutro
    } else if (longitud >= 3) {
        inputTitulo.style.borderColor = 'green'; // Válido: verde
    } else {
        inputTitulo.style.borderColor = 'orange'; // Demasiado corto: naranja
    }
});
 
// Mostramos el mensaje de lista vacía al cargar la página por primera vez
actualizarMensajeVacio();
 