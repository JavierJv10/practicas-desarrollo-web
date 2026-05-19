package Ejercicio1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

public class PruebasCajaNegraCompleto {

	// ---------------------------------------------------------------
	// PARTICIONES EQUIVALENTES - Estructura del Array
	// ---------------------------------------------------------------
	@Test
	@DisplayName("Pruebas de Particiones Equivalentes (Estructura del Array)")
	void testParticionesArray() {
		// 1. Array Nulo -> todos los métodos deben devolver 0 sin excepción
		assertDoesNotThrow(() -> GestionNotas.calcularMedia(null), "calcularMedia: nulo no lanza excepción");
		assertEquals(0, GestionNotas.calcularMedia(null), "calcularMedia: nulo devuelve 0");

		assertDoesNotThrow(() -> GestionNotas.encontrarNotaMaxima(null),
				"encontrarNotaMaxima: nulo no lanza excepción");
		assertEquals(0, GestionNotas.encontrarNotaMaxima(null), "encontrarNotaMaxima: nulo devuelve 0");

		assertDoesNotThrow(() -> GestionNotas.contarAprobados(null), "contarAprobados: nulo no lanza excepción");
		assertEquals(0, GestionNotas.contarAprobados(null), "contarAprobados: nulo devuelve 0");

		// 2. Array Vacío
		double[] vacio = {};
		assertEquals(0, GestionNotas.calcularMedia(vacio), "calcularMedia: vacío devuelve 0");
		assertEquals(0, GestionNotas.encontrarNotaMaxima(vacio), "encontrarNotaMaxima: vacío devuelve 0");
		assertEquals(0, GestionNotas.contarAprobados(vacio), "contarAprobados: vacío devuelve 0");

		// 3. Un solo elemento
		double[] uno = { 7.0 };
		assertEquals(7.0, GestionNotas.calcularMedia(uno), 0.001, "calcularMedia: un elemento");
		assertEquals(7.0, GestionNotas.encontrarNotaMaxima(uno), "encontrarNotaMaxima: un elemento");
		assertEquals(1, GestionNotas.contarAprobados(uno), "contarAprobados: un elemento aprobado");

		// 4. Varios elementos (mezcla de aprobados y suspensos)
		double[] varios = { 3.0, 8.5, 4.0, 9.0 };
		assertEquals(2, GestionNotas.contarAprobados(varios), "contarAprobados: 2 aprobados en mezcla");
	}

	// ---------------------------------------------------------------
	// PARTICIONES EQUIVALENTES - Contenido de las notas
	// ---------------------------------------------------------------
	@Test
	@DisplayName("Pruebas de Particiones de Contenido (Aprobados/Suspensos)")
	void testContenidoNotas() {
		// Partición: Todas aprobadas
		double[] todasAprobadas = { 5.0, 8.0, 10.0 };
		assertEquals(3, GestionNotas.contarAprobados(todasAprobadas), "Todos deben estar aprobados");

		// Partición: Todas suspensas
		double[] todasSuspensas = { 0.0, 2.5, 4.9 };
		assertEquals(0, GestionNotas.contarAprobados(todasSuspensas), "Nadie debe estar aprobado");

		// Partición: Mezcla de aprobados y suspensos
		double[] mezcla = { 2.0, 5.0, 4.9, 5.1 };
		assertEquals(2, GestionNotas.contarAprobados(mezcla), "Solo 5.0 y 5.1 son aprobados");
	}

	// ---------------------------------------------------------------
	// VALORES LÍMITE - Escala de notas (0 a 10)
	// ---------------------------------------------------------------
	@Test
	@DisplayName("Pruebas de Valores Límite (Escala 0-10)")
	void testValoresLimite() {
		double[] notas = { 5.0, 5.0, 5.0, 5.0, 5.0 };

		// Límite inferior: exactamente 0 (válido, debe asignarse)
		GestionNotas.actualizarNota(notas, 0, 0.0);
		assertEquals(0.0, notas[0], "Nota 0.0 es válida y se asigna");

		// Límite inferior: menor que 0 (debe saturar en 0)
		GestionNotas.actualizarNota(notas, 0, -1.0);
		assertEquals(0.0, notas[0], "Nota -1.0 satura en 0");

		// Frontera de aprobado: 4.9 (suspenso) y 5.0 (aprobado)
		double[] frontera = { 4.9, 5.0 };
		assertEquals(1, GestionNotas.contarAprobados(frontera), "4.9 suspenso, 5.0 aprobado");

		// Valor interior alto: 9.9 (válido, debe asignarse)
		GestionNotas.actualizarNota(notas, 1, 9.9);
		assertEquals(9.9, notas[1], "Nota 9.9 es válida y se asigna");

		// Límite superior: exactamente 10 (válido, debe asignarse)
		GestionNotas.actualizarNota(notas, 2, 10.0);
		assertEquals(10.0, notas[2], "Nota 10.0 es válida y se asigna");

		// Límite superior: 10.1 (debe saturar en 10)
		GestionNotas.actualizarNota(notas, 3, 10.1);
		assertEquals(10.0, notas[3], "Nota 10.1 satura en 10");

		// Valor muy alto: 15.0 (debe saturar en 10)
		GestionNotas.actualizarNota(notas, 4, 15.0);
		assertEquals(10.0, notas[4], "Nota 15.0 satura en 10");
	}

	// ---------------------------------------------------------------
	// VALORES LÍMITE - Índices en actualizarNota
	// ---------------------------------------------------------------
	@Test
	@DisplayName("Pruebas de Valores Límite (Índices en actualizarNota)")
	void testValoresLimiteIndices() {
		double[] notas = { 5.0, 7.0, 9.0 }; // length = 3

		// Índice -1 (justo por debajo del mínimo válido)
		GestionNotas.actualizarNota(notas, -1, 8.0);
		assertEquals(5.0, notas[0], "Índice -1 no debe modificar el array");

		// Índice 0 (primer índice válido)
		GestionNotas.actualizarNota(notas, 0, 6.0);
		assertEquals(6.0, notas[0], "Índice 0 debe modificar correctamente");

		// Índice 2 (último índice válido, length-1)
		GestionNotas.actualizarNota(notas, 2, 8.0);
		assertEquals(8.0, notas[2], "Índice length-1 debe modificar correctamente");

		// Índice 3 (igual a length, fuera de rango)
		GestionNotas.actualizarNota(notas, 3, 8.0);
		assertEquals(8.0, notas[2], "Índice igual a length no debe modificar el array");

		// Índice muy grande
		GestionNotas.actualizarNota(notas, 99, 8.0);
		assertEquals(8.0, notas[2], "Índice muy grande no debe modificar el array");
	}
}