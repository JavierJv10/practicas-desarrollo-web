package Ejercicio3;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Pruebas de Caja Blanca - Sistema de Reservas")
class SistemaReservasCajaBlancaTest {

	// ---------------------------------------------------------------
	// COBERTURA DE SENTENCIAS: Ramas de guarda (null)
	// Caminos: if(fila == null) -> return 0 / return false / return
	// ---------------------------------------------------------------
	@Test
	@DisplayName("Caja Blanca: Cobertura de sentencias nulas")
	void testSentenciasNulas() {
		// Rama null de contarLibres -> return 0
		assertEquals(0, SistemaReservaAsientos.contarLibres(null), "contarLibres: nulo devuelve 0");

		// Rama null de contarOcupados -> return 0
		assertEquals(0, SistemaReservaAsientos.contarOcupados(null), "contarOcupados: nulo devuelve 0");

		// Rama null de estaCompleta -> return false
		assertFalse(SistemaReservaAsientos.estaCompleta(null), "estaCompleta: nulo devuelve false");

		// Rama null de reservarAsiento -> return inmediato
		assertDoesNotThrow(() -> SistemaReservaAsientos.reservarAsiento(null, 0),
				"reservarAsiento: nulo no lanza excepción");

		// Rama null de cancelarReserva -> return inmediato
		assertDoesNotThrow(() -> SistemaReservaAsientos.cancelarReserva(null, 0),
				"cancelarReserva: nulo no lanza excepción");
	}

	// ---------------------------------------------------------------
	// COBERTURA DE BUCLES: 0, 1 y N iteraciones
	// ---------------------------------------------------------------
	@Test
	@DisplayName("Caja Blanca: Cobertura de bucles (0, 1 y N iteraciones)")
	void testBucles() {
		// Caso 0 iteraciones: Array vacío
		assertFalse(SistemaReservaAsientos.estaCompleta(new boolean[] {}), "Vacío devuelve false");
		assertEquals(0, SistemaReservaAsientos.contarLibres(new boolean[] {}), "Vacío: 0 libres");
		assertEquals(0, SistemaReservaAsientos.contarOcupados(new boolean[] {}), "Vacío: 0 ocupados");

		// Caso 1 iteración: Un solo elemento
		boolean[] unaLibre = { false };
		assertEquals(1, SistemaReservaAsientos.contarLibres(unaLibre), "Un elemento libre");

		boolean[] unaOcupada = { true };
		assertEquals(1, SistemaReservaAsientos.contarOcupados(unaOcupada), "Un elemento ocupado");

		// Caso N iteraciones: Varios elementos
		boolean[] filaLlena = { true, true, true };
		assertTrue(SistemaReservaAsientos.estaCompleta(filaLlena), "Fila llena devuelve true");

		boolean[] filaMix = { false, true, false, true };
		assertEquals(2, SistemaReservaAsientos.contarLibres(filaMix), "Varios: 2 libres");
		assertEquals(2, SistemaReservaAsientos.contarOcupados(filaMix), "Varios: 2 ocupados");
	}

	// ---------------------------------------------------------------
	// COBERTURA DE RAMAS: Índices y estados de asiento
	// ---------------------------------------------------------------
	@Test
	@DisplayName("Caja Blanca: Cobertura de ramas de índices y estados")
	void testRamasYEstados() {
		boolean[] fila = { false, true }; // [0]=Libre, [1]=Ocupado

		// Rama: índice negativo -> condición (indice>=0) false, no hace nada
		SistemaReservaAsientos.reservarAsiento(fila, -1);
		assertFalse(fila[0], "Índice negativo no debe modificar el array");

		// Rama: índice fuera de rango superior -> condición false, no hace nada
		SistemaReservaAsientos.reservarAsiento(fila, 5);
		assertFalse(fila[0], "Índice fuera de rango no debe modificar el array");

		// Rama: asiento ya ocupado -> if(!fila[indice]) false, no hace nada
		SistemaReservaAsientos.reservarAsiento(fila, 1);
		assertTrue(fila[1], "Asiento ocupado debe seguir ocupado");

		// Rama: asiento ya libre -> if(fila[indice]) false, no hace nada
		SistemaReservaAsientos.cancelarReserva(fila, 0);
		assertFalse(fila[0], "Asiento libre debe seguir libre");

		// Rama POSITIVA: asiento libre -> if(!fila[indice]) true, se reserva
		SistemaReservaAsientos.reservarAsiento(fila, 0);
		assertTrue(fila[0], "Asiento libre debe quedar ocupado tras reservar");

		// Rama POSITIVA: asiento ocupado -> if(fila[indice]) true, se cancela
		SistemaReservaAsientos.cancelarReserva(fila, 1);
		assertFalse(fila[1], "Asiento ocupado debe quedar libre tras cancelar");
	}
}