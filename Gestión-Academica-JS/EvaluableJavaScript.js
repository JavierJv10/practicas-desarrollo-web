// BLOQUE 1 - Configuración Inicial y Estructura Base

// Array global que almacena todos los estudiantes del sistema
let estudiantes = [];

// Función principal que inicia el sistema y controla el menú
function iniciarSistema() {
    let opcion;

    // do...while garantiza que el menú se muestre al menos una vez
    do {
        console.log("\n--- SISTEMA DE GESTIÓN ACADÉMICA ---");
        console.log("1. Agregar estudiante");
        console.log("2. Mostrar estudiantes");
        console.log("3. Calcular promedio de un estudiante");
        console.log("4. Buscar primera letra repetida en nombre");
        console.log("5. Calcular suma recursiva hasta N");
        console.log("6. Salir");

        opcion = prompt("Elige una opción (1-6):");

        // prompt() devuelve null si el usuario pulsa Cancelar
        if (opcion === null) {
            console.log("-> Operación cancelada. Saliendo...");
            break;
        }
        opcion = opcion.trim();

        switch (opcion) {
            case '1':
                console.log("-> Has elegido: Agregar estudiante");
                const id = Number(prompt("Introduce el ID del estudiante:"));
                const nombre = prompt("Introduce el nombre del estudiante:");
                const edad = Number(prompt("Introduce la edad del estudiante:"));
                agregarEstudiante(id, nombre, edad);
                break;

            case '2':
                console.log("-> Has elegido: Mostrar estudiantes");
                if (estudiantes.length === 0) {
                    console.log("No hay estudiantes registrados.");
                } else {
                    estudiantes.forEach(est => est.mostrarInformacion());
                }
                break;

            case '3':
                console.log("-> Has elegido: Calcular promedio");
                const idPromedio = Number(prompt("Introduce el ID del estudiante:"));
                calcularPromedio(idPromedio);
                break;

            case '4':
                console.log("-> Has elegido: Buscar primera letra repetida");
                const nombreAnalizar = prompt("Introduce el nombre a analizar:");
                console.log(primeraLetraRepetida(nombreAnalizar));
                break;

            case '5':
                console.log("-> Has elegido: Calcular suma recursiva");
                const numero = Number(prompt("Introduce un número entero positivo:"));
                console.log("Resultado:", sumaHastaN(numero));
                break;

            case '6':
                console.log("-> Saliendo del sistema...");
                break;

            default:
                console.log("-> ERROR: Opción no válida. Por favor, elige un número del 1 al 6.");
        }
    } while (opcion !== '6');
}

// BLOQUE 2 - Registro de Estudiantes (Condicionales + Operadores + Manejo de Errores)
function agregarEstudiante(id, nombre, edad) {
    try {
        // Validamos que id sea un número válido (isFinite descarta NaN e Infinity)
        if (!Number.isFinite(id)) {
            throw new Error("El ID introducido no es válido. Debe ser un número finito.");
        }

        // Validamos que nombre sea un string no vacío
        if (typeof nombre !== 'string' || nombre.trim() === '') {
            throw new Error("El nombre no es válido. Debe ser una cadena de texto no vacía.");
        }

        // Validamos que edad sea un entero positivo
        if (!Number.isInteger(edad) || edad <= 0) {
            throw new Error("La edad debe ser un número entero positivo.");
        }

        // Comprobamos que no exista otro estudiante con el mismo id
        const existeId = estudiantes.some(est => est.id === id);
        if (existeId) {
            throw new Error(`Ya existe un estudiante registrado con el ID ${id}.`);
        }

        // Todas las validaciones correctas: creamos e insertamos el estudiante
        const nuevoEstudiante = {
            id: id,
            nombre: nombre.trim(),
            edad: edad,
            notas: [],

            // Método del objeto (Bloque 7): usa 'this' para acceder a sus propias propiedades
            mostrarInformacion: function () {
                console.log("\n--- Información del Estudiante ---");
                console.log("Nombre: " + this.nombre);
                console.log("Edad: " + this.edad);
                console.log("Número de notas registradas: " + this.notas.length);
            }
        };

        estudiantes.push(nuevoEstudiante);
        console.log(`¡Éxito! Estudiante ${nuevoEstudiante.nombre} agregado correctamente.`);
        return nuevoEstudiante;

    } catch (error) {
        console.error("Error al registrar: " + error.message);
        return null;
    } finally {
        // finally se ejecuta siempre, haya error o no
        console.log("Intento de registro finalizado");
    }
}

// BLOQUE 3 - Gestión de Notas (Arrays + Bucles)
function agregarNota(idEstudiante, nota) {

    // Las notas solo son válidas si son número y están en el rango 0-10
    if (typeof nota !== 'number' || nota < 0 || nota > 10) {
        console.error("Error: La nota (" + nota + ") no es válida. Debe estar entre 0 y 10.");
        return;
    }

    let estudianteEncontrado = null;

    // Recorremos el array con for para buscar al estudiante por su id
    // Usamos === (comparación estricta) para evitar conversiones de tipo
    for (let i = 0; i < estudiantes.length; i++) {
        if (estudiantes[i].id === idEstudiante) {
            estudianteEncontrado = estudiantes[i];
            break;
        }
    }

    if (estudianteEncontrado === null) {
        console.error("Error: No se encontró ningún estudiante con el ID " + idEstudiante + ".");
        return;
    }

    // Insertamos la nota en el array de notas del estudiante con push()
    estudianteEncontrado.notas.push(nota);
    console.log("Éxito: Nota " + nota + " añadida a " + estudianteEncontrado.nombre + ".");
}

// BLOQUE 4 - Cálculo de Promedio
// Usamos arrow function para demostrar una sintaxis alternativa a function nombrada
const calcularPromedio = (idEstudiante) => {
    let estudianteEncontrado = null;

    for (let i = 0; i < estudiantes.length; i++) {
        if (estudiantes[i].id === idEstudiante) {
            estudianteEncontrado = estudiantes[i];
            break;
        }
    }

    if (estudianteEncontrado === null) {
        console.error("Error: No se encontró al estudiante para calcular el promedio.");
        return;
    }

    let arrayNotas = estudianteEncontrado.notas;

    if (arrayNotas.length === 0) {
        console.log("El estudiante " + estudianteEncontrado.nombre + " aún no tiene notas registradas.");
        return;
    }

    // Sumamos manualmente con forEach en lugar de reduce, acumulando en sumaTotal
    let sumaTotal = 0;
    arrayNotas.forEach((nota) => {
        sumaTotal += nota;
    });

    let promedio = sumaTotal / arrayNotas.length;

    // Clasificamos al estudiante según su promedio
    let estado = "";
    if (promedio < 5) {
        estado = "Suspende";
    } else if (promedio >= 5 && promedio < 9) {
        estado = "Aprobado";
    } else if (promedio >= 9) {
        estado = "Excelente";
    }

    // toFixed(2) redondea el promedio a 2 decimales para mostrarlo limpio
    console.log("El estudiante " + estudianteEncontrado.nombre + " tiene un promedio de " + promedio.toFixed(2) + ". Estado: " + estado);
};

// BLOQUE 5 - Análisis de Strings (length + indexado + bucles)
function primeraLetraRepetida(nombre) {

    if (typeof nombre !== 'string') {
        return "Error: El valor introducido no es una cadena de texto.";
    }

    // Convertimos a minúsculas para que 'A' y 'a' se traten como la misma letra
    let nombreMinusculas = nombre.toLowerCase();

    // Recorremos carácter a carácter usando length e indexado
    for (let i = 0; i < nombreMinusculas.length; i++) {
        let letraActual = nombreMinusculas[i];

        // Ignoramos espacios, guiones y cualquier carácter que no sea letra
        if (!/[a-záéíóúüñ]/.test(letraActual)) {
            continue;
        }

        // Comparamos la letra actual con todas las anteriores
        for (let j = 0; j < i; j++) {
            if (letraActual === nombreMinusculas[j]) {
                return "La primera letra repetida es: " + letraActual;
            }
        }
    }

    return "No hay repeticiones en el nombre.";
}

// BLOQUE 6 - Recursividad
function sumaHastaN(n) {

    // Validamos que n sea un número entero positivo
    if (typeof n !== 'number' || !Number.isInteger(n) || n <= 0) {
        return "Error: n debe ser un número entero positivo.";
    }

    // Caso base: cuando n llega a 1, la recursión se detiene
    if (n === 1) {
        return 1;
    }

    // Caso recursivo: sumamos n al resultado de la misma función con n-1
    return n + sumaHastaN(n - 1);
}

/* Traza de ejecución para sumaHastaN(3):
   sumaHastaN(3) -> 3 + sumaHastaN(2)
   sumaHastaN(2) -> 2 + sumaHastaN(1)
   sumaHastaN(1) -> 1  (caso base, se detiene aquí)
   Resultado final: 3 + 2 + 1 = 6
*/

// BLOQUE 7 - Objetos y this
// El método mostrarInformacion() ya está definido dentro del objeto estudiante en el Bloque 2.
// Aquí demostramos que 'this' funciona correctamente con un objeto de prueba independiente.

function demostrarThis() {
    console.log("\n--- Demostración del Bloque 7 (Uso de 'this') ---");

    // Creamos un objeto de prueba con el mismo formato que un estudiante real
    let estudiantePrueba = {
        id: 999,
        nombre: "Estudiante de Prueba",
        edad: 20,
        notas: [7, 8, 9, 10],

        // 'this' dentro de un método hace referencia al propio objeto que lo contiene
        mostrarInformacion: function () {
            console.log("- Nombre: " + this.nombre);
            console.log("- Edad: " + this.edad);
            console.log("- Número de notas registradas: " + this.notas.length);
        }
    };

    // Al llamar al método, 'this' toma los valores de estudiantePrueba
    estudiantePrueba.mostrarInformacion();
}

demostrarThis();

// BLOQUE 8 - Manipulación Avanzada de Arrays
function mostrarEstudiantesDestacados() {

    // filter() devuelve un nuevo array solo con los estudiantes cuyo promedio >= 9
    let estudiantesDestacados = estudiantes.filter((estudiante) => {
        let arrayNotas = estudiante.notas;

        if (arrayNotas.length === 0) {
            return false;
        }

        // Calculamos el promedio manualmente, igual que en el Bloque 4
        let sumaTotal = 0;
        arrayNotas.forEach((nota) => {
            sumaTotal += nota;
        });
        let promedio = sumaTotal / arrayNotas.length;

        return promedio >= 9;
    });

    // map() transforma el array de objetos estudiante en un array de solo sus nombres
    let nombresDestacados = estudiantesDestacados.map((estudiante) => {
        return estudiante.nombre;
    });

    if (nombresDestacados.length > 0) {
        console.log("Estudiantes destacados (Promedio >= 9): ", nombresDestacados);
    } else {
        console.log("Actualmente no hay estudiantes con promedio excelente.");
    }
}

// BLOQUE 9 - Ámbito de Variables

// Variable global: declarada fuera de cualquier función, accesible desde cualquier parte del archivo.
// El array 'estudiantes' del Bloque 1 es también un ejemplo de variable global.
let sistemaActivo = true;

function demostrarAmbitoVariables() {
    console.log("\n--- BLOQUE 9: Demostración de Ámbitos ---");

    // Variable local: solo existe mientras se ejecuta esta función.
    // Intentar acceder a ella desde fuera provocaría un ReferenceError.
    let mensajeLocal = "Soy una variable local, solo existo dentro de demostrarAmbitoVariables()";
    console.log(mensajeLocal);

    // Desde dentro de la función sí podemos leer variables globales
    console.log("Variable global accesible desde función local -> Sistema activo: " + sistemaActivo);

    console.log("\n--- Diferencia entre 'var' y 'let' ---");

    if (true) {
        // 'var' tiene ámbito de función (function scope): ignora las llaves {} del bloque if
        // y sigue existiendo fuera de él. Esto puede causar bugs difíciles de detectar.
        var variableConVar = "Soy VAR: puedo escapar del bloque if.";

        // 'let' tiene ámbito de bloque (block scope): queda atrapada dentro de estas llaves {}
        // y deja de existir en cuanto el bloque if termina.
        let variableConLet = "Soy LET: solo existo dentro de este bloque if.";

        console.log("Dentro del if - var: " + variableConVar);
        console.log("Dentro del if - let: " + variableConLet);
    }

    // 'var' sigue siendo accesible aquí porque no respeta el ámbito de bloque
    console.log("Fuera del if - var: " + variableConVar);

    // Si descomentáramos la siguiente línea obtendríamos: ReferenceError: variableConLet is not defined
    // Esto confirma que 'let' murió al terminar el bloque if, haciéndolo más seguro que 'var'.
    // console.log("Fuera del if - let: " + variableConLet);
}

demostrarAmbitoVariables();

// BLOQUE 10 - Manejo Global de Errores
// Encapsulamos el flujo principal para capturar cualquier error inesperado que escape de las funciones internas

try {
    iniciarSistema();

} catch (e) {
    // Captura cualquier error no controlado que llegue hasta este nivel
    console.log("Error general del sistema");

} finally {
    // Se ejecuta siempre, tanto si el usuario elige 'Salir' como si ocurre un error
    console.log("Sistema finalizado correctamente");
}