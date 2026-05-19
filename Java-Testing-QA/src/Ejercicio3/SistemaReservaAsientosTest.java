package Ejercicio3;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

class SistemaReservasFullTest {

	// --- TEST DE contarLibres y contarOcupados ---

	@Test
	@DisplayName("Contar libres y ocupados: Nulo, vacío y varios")
	void testContarLibresYOcupados() {
		// Caso: Array nulo
		assertEquals(0, SistemaReservaAsientos.contarLibres(null), "Nulo debe dar 0 libres");
		assertEquals(0, SistemaReservaAsientos.contarOcupados(null), "Nulo debe dar 0 ocupados");

		// Caso: Array vacío
		assertEquals(0, SistemaReservaAsientos.contarLibres(new boolean[] {}), "Vacío debe dar 0 libres");
		assertEquals(0, SistemaReservaAsientos.contarOcupados(new boolean[] {}), "Vacío debe dar 0 ocupados");

		// Caso: Mezcla de libres y ocupados
		boolean[] fila = { false, true, false, true, true };
		assertEquals(2, SistemaReservaAsientos.contarLibres(fila), "Debe contar 2 libres");
		assertEquals(3, SistemaReservaAsientos.contarOcupados(fila), "Debe contar 3 ocupados");
	}

	// --- TEST DE ROBUSTEZ (Índices fuera de rango) ---

	@Test
	@DisplayName("Detección de errores en límites de array")
	void testRobustezIndices() {
		boolean[] fila = { false, false, false }; // Longitud 3

		// No debe fallar con índices fuera de rango
		assertDoesNotThrow(() -> {
			SistemaReservaAsientos.reservarAsiento(fila, -1);
			SistemaReservaAsientos.reservarAsiento(fila, 3); // índice 3 no existe
		}, "El sistema no debe lanzar excepción con índices inválidos");

		// Verificamos que la fila sigue igual
		assertArrayEquals(new boolean[] { false, false, false }, fila, "El array no debe modificarse");

		// Array nulo no debe lanzar excepción
		assertDoesNotThrow(() -> SistemaReservaAsientos.reservarAsiento(null, 0),
				"Array nulo en reservar no debe lanzar excepción");
		assertDoesNotThrow(() -> SistemaReservaAsientos.cancelarReserva(null, 0),
				"Array nulo en cancelar no debe lanzar excepción");
	}

	// --- TEST DE LÓGICA DE NEGOCIO (Estados de reserva) ---

	@Test
	@DisplayName("Validación de lógica de cambio de estado")
	void testLogicaEstados() {
		boolean[] fila = { true, false }; // [0] ocupado, [1] libre

		// Caso: Reservar uno ya ocupado (no cambia)
		SistemaReservaAsientos.reservarAsiento(fila, 0);
		assertTrue(fila[0], "El asiento debe seguir ocupado");

		// Caso: Cancelar uno que ya está libre (no cambia)
		SistemaReservaAsientos.cancelarReserva(fila, 1);
		assertFalse(fila[1], "El asiento debe seguir libre");

		// Caso: Reservar un asiento libre (cambia a ocupado)
		SistemaReservaAsientos.reservarAsiento(fila, 1);
		assertTrue(fila[1], "El asiento libre debe quedar ocupado");

		// Caso: Cancelar un asiento ocupado (cambia a libre)
		SistemaReservaAsientos.cancelarReserva(fila, 0);
		assertFalse(fila[0], "El asiento ocupado debe quedar libre");
	}

	// --- TEST DE LÓGICA DE FILA COMPLETA ---

	@Test
	@DisplayName("Validación de detección de fila completa")
	void testLogicaFilaCompleta() {
		// Nulo: debe dar false
		assertFalse(SistemaReservaAsientos.estaCompleta(null), "Nulo debe dar false");

		// Vacía: debe dar false
		assertFalse(SistemaReservaAsientos.estaCompleta(new boolean[] {}), "Vacía debe dar false");

		// Fila mixta: debe dar false
		assertFalse(SistemaReservaAsientos.estaCompleta(new boolean[] { true, false, true }), "Mixta debe dar false");

		// Fila 100% llena: debe dar true
		assertTrue(SistemaReservaAsientos.estaCompleta(new boolean[] { true, true, true }), "Llena debe dar true");
	}
}