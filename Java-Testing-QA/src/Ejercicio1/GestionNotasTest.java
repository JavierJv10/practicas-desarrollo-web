package Ejercicio1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

class GestionNotasTest {

	// --- 1. PRUEBAS PARA calcularMedia ---

	@Test
	@DisplayName("Cálculo de media: Casos de estructura de array (Vacío, Nulo, Un elemento, Varios)")
	void testCalcularMediaEstructura() {
		// Partición: Array nulo
		assertEquals(0, GestionNotas.calcularMedia(null), "Debe retornar 0 si es nulo");

		// Partición: Array vacío
		assertEquals(0, GestionNotas.calcularMedia(new double[] {}), "Debe retornar 0 si está vacío");

		// Cobertura de bucle: Un solo elemento
		assertEquals(7.0, GestionNotas.calcularMedia(new double[] { 7.0 }), 0.001,
				"Media de un elemento es el propio elemento");

		// Partición: Varios elementos
		double[] notas = { 4, 6, 8 }; // Suma 18 / 3 = 6
		assertEquals(6.0, GestionNotas.calcularMedia(notas), 0.001, "Media estándar de varios elementos");
	}

	// --- 2. PRUEBAS PARA encontrarNotaMaxima ---

	@Test
	@DisplayName("Nota máxima: Nulo, vacío, un elemento y máximo en distintas posiciones")
	void testEncontrarNotaMaxima() {
		// Rama: Array nulo
		assertEquals(0, GestionNotas.encontrarNotaMaxima(null), "Array nulo debe dar 0");

		// Rama: Array vacío
		assertEquals(0, GestionNotas.encontrarNotaMaxima(new double[] {}), "Array vacío debe dar 0");

		// Bucle: Un solo elemento
		assertEquals(7.5, GestionNotas.encontrarNotaMaxima(new double[] { 7.5 }), "Max de un elemento");

		// Máximo al principio
		assertEquals(9.0, GestionNotas.encontrarNotaMaxima(new double[] { 9.0, 4.0, 5.0 }), "Máximo al principio");

		// Máximo en el medio
		assertEquals(9.0, GestionNotas.encontrarNotaMaxima(new double[] { 3.0, 9.0, 2.0 }), "Máximo en el medio");

		// Máximo al final
		assertEquals(9.0, GestionNotas.encontrarNotaMaxima(new double[] { 3.0, 2.0, 9.0 }), "Máximo al final");
	}

	// --- 3. PRUEBAS PARA contarAprobados ---

	@Test
	@DisplayName("Contar aprobados: Nulo, vacío y valores frontera (4.9, 5.0, 10.0)")
	void testContarAprobadosLimites() {
		// Rama: Array nulo
		assertEquals(0, GestionNotas.contarAprobados(null), "Array nulo tiene 0 aprobados");

		// Bucle: Array vacío
		assertEquals(0, GestionNotas.contarAprobados(new double[] {}), "Array vacío tiene 0 aprobados");

		// Valores límite: 4.9 (suspenso) y 5.0 (aprobado)
		double[] notasFrontera = { 4.9, 5.0 };
		assertEquals(1, GestionNotas.contarAprobados(notasFrontera), "Solo el 5.0 debe contar como aprobado");

		// Partición: Todo aprobados
		assertEquals(3, GestionNotas.contarAprobados(new double[] { 5, 8, 10 }), "Todos aprobados");

		// Partición: Todo suspensos
		assertEquals(0, GestionNotas.contarAprobados(new double[] { 0, 2, 4.9 }), "Todos suspensos");
	}

	// --- 4. PRUEBAS PARA actualizarNota ---

	@Test
	@DisplayName("Actualizar nota: Saturación (menores que 0 y mayores que 10) y valores válidos")
	void testActualizarNotaLimites() {
		double[] notas = { 5.0, 5.0, 5.0 };

		// Rama: Array nulo (no debe lanzar excepción)
		assertDoesNotThrow(() -> GestionNotas.actualizarNota(null, 0, 5.0), "Array nulo no debe lanzar excepción");

		// Límite: Menor que 0 (debe fijar en 0)
		GestionNotas.actualizarNota(notas, 0, -5.0);
		assertEquals(0.0, notas[0], "Nota negativa se fija en 0");

		// Límite: Mayor que 10 (debe fijar en 10)
		GestionNotas.actualizarNota(notas, 1, 15.0);
		assertEquals(10.0, notas[1], "Nota > 10 se fija en 10");

		// Valores límite válidos: 0.0 y 10.0
		GestionNotas.actualizarNota(notas, 2, 0.0);
		assertEquals(0.0, notas[2], "Nota 0.0 es válida");

		GestionNotas.actualizarNota(notas, 2, 10.0);
		assertEquals(10.0, notas[2], "Nota 10.0 es válida");

		// Valor intermedio válido: 9.9
		GestionNotas.actualizarNota(notas, 2, 9.9);
		assertEquals(9.9, notas[2], "Nota 9.9 es válida");
	}

	@Test
	@DisplayName("Actualizar nota: Robustez con índices fuera de rango")
	void testActualizarNotaIndices() {
		double[] notas = { 8.0 };

		// Índice negativo
		GestionNotas.actualizarNota(notas, -1, 10.0);
		assertEquals(8.0, notas[0], "Índice negativo no debe modificar el array");

		// Índice igual a length (fuera de rango)
		GestionNotas.actualizarNota(notas, 1, 10.0);
		assertEquals(8.0, notas[0], "Índice igual a length no debe modificar el array");

		// Índice muy grande
		GestionNotas.actualizarNota(notas, 99, 10.0);
		assertEquals(8.0, notas[0], "Índice muy grande no debe modificar el array");
	}
}