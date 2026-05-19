package Ejercicio3;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Pruebas de Caja Negra - Sistema de Reservas")
class SistemaReservasCajaNegraTest {

	// ---------------------------------------------------------------
	// PARTICIONES EQUIVALENTES - Estructura del array
	// ---------------------------------------------------------------
	@Test
	@DisplayName("Caja Negra: Particiones de estructura del array")
	void testParticionesEstructura() {
		// Partición: Array nulo
		assertEquals(0, SistemaReservaAsientos.contarLibres(null), "Nulo devuelve 0 libres");
		assertEquals(0, SistemaReservaAsientos.contarOcupados(null), "Nulo devuelve 0 ocupados");
		assertFalse(SistemaReservaAsientos.estaCompleta(null), "Nulo devuelve false en estaCompleta");

		// Partición: Array vacío
		boolean[] vacio = {};
		assertEquals(0, SistemaReservaAsientos.contarLibres(vacio), "Vacío devuelve 0 libres");
		assertEquals(0, SistemaReservaAsientos.contarOcupados(vacio), "Vacío devuelve 0 ocupados");
		assertFalse(SistemaReservaAsientos.estaCompleta(vacio), "Vacío devuelve false en estaCompleta");

		// Partición: Todos libres
		boolean[] todosLibres = { false, false, false };
		assertEquals(3, SistemaReservaAsientos.contarLibres(todosLibres), "Todos libres: 3 libres");
		assertEquals(0, SistemaReservaAsientos.contarOcupados(todosLibres), "Todos libres: 0 ocupados");
		assertFalse(SistemaReservaAsientos.estaCompleta(todosLibres), "Todos libres no está completa");
	}

	// ---------------------------------------------------------------
	// PARTICIONES EQUIVALENTES - Estados de la fila
	// ---------------------------------------------------------------
	@Test
	@DisplayName("Caja Negra: Particiones de estados de la fila")
	void testParticionesFila() {
		// Partición: Fila mixta (algunos libres, algunos ocupados)
		boolean[] filaMix = { false, true, false };
		assertEquals(2, SistemaReservaAsientos.contarLibres(filaMix), "Fila mixta: 2 libres");
		assertEquals(1, SistemaReservaAsientos.contarOcupados(filaMix), "Fila mixta: 1 ocupado");
		assertFalse(SistemaReservaAsientos.estaCompleta(filaMix), "Fila mixta no está completa");

		// Partición: Todos ocupados
		boolean[] filaLlena = { true, true, true };
		assertEquals(0, SistemaReservaAsientos.contarLibres(filaLlena), "Llena: 0 libres");
		assertEquals(3, SistemaReservaAsientos.contarOcupados(filaLlena), "Llena: 3 ocupados");
		assertTrue(SistemaReservaAsientos.estaCompleta(filaLlena), "Todos ocupados está completa");
	}

	// ---------------------------------------------------------------
	// VALORES LÍMITE - Índices críticos
	// ---------------------------------------------------------------
	@Test
	@DisplayName("Caja Negra: Valores Límite en índices")
	void testLimitesIndices() {
		boolean[] fila = { false, false, false }; // longitud 3

		// Límite inferior inválido: -1
		SistemaReservaAsientos.reservarAsiento(fila, -1);
		assertArrayEquals(new boolean[] { false, false, false }, fila, "Índice -1 no debe modificar");

		// Límite inferior válido: 0 (primer índice)
		SistemaReservaAsientos.reservarAsiento(fila, 0);
		assertTrue(fila[0], "Índice 0 debe reservar correctamente");

		// Límite superior válido: length-1 (índice 2)
		SistemaReservaAsientos.reservarAsiento(fila, 2);
		assertTrue(fila[2], "Índice length-1 debe reservar correctamente");

		// Límite superior inválido: length (índice 3)
		SistemaReservaAsientos.cancelarReserva(fila, 3);
		assertTrue(fila[0], "Índice igual a length no debe modificar");
		assertTrue(fila[2], "Índice igual a length no debe modificar");

		// Límite superior inválido: length+1 (índice 4)
		SistemaReservaAsientos.cancelarReserva(fila, 4);
		assertTrue(fila[0], "Índice length+1 no debe modificar");
		assertTrue(fila[2], "Índice length+1 no debe modificar");
	}

	// ---------------------------------------------------------------
	// CASOS DE USO TÍPICOS - Flujo de reservas y cancelaciones
	// ---------------------------------------------------------------
	@Test
	@DisplayName("Caja Negra: Casos de uso típicos")
	void testCasosDeUso() {
		boolean[] fila = { false, true }; // [0] libre, [1] ocupado

		// Caso: Reservar asiento libre -> cambia a ocupado
		SistemaReservaAsientos.reservarAsiento(fila, 0);
		assertArrayEquals(new boolean[] { true, true }, fila, "Asiento libre debe quedar ocupado");

		// Caso: Intentar reservar uno ya ocupado -> no cambia nada
		SistemaReservaAsientos.reservarAsiento(fila, 1);
		assertArrayEquals(new boolean[] { true, true }, fila, "Asiento ocupado no debe cambiar");

		// Caso: Cancelar asiento ocupado -> cambia a libre
		SistemaReservaAsientos.cancelarReserva(fila, 0);
		assertArrayEquals(new boolean[] { false, true }, fila, "Asiento ocupado debe quedar libre");

		// Caso: Cancelar asiento ya libre -> no cambia nada
		SistemaReservaAsientos.cancelarReserva(fila, 0);
		assertArrayEquals(new boolean[] { false, true }, fila, "Asiento libre no debe cambiar");
	}
}