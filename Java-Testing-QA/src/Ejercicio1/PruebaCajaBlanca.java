package Ejercicio1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class PruebaCajaBlanca {

	// ---------------------------------------------------------------
	// 1. Pruebas para calcularMedia
	// Caminos: if(nulo/vacío) -> return 0 | bucle -> return media
	// ---------------------------------------------------------------
	@Test
	public void testCalcularMedia() {
		// TC-01: Array Nulo -> rama if true, return 0
		assertEquals(0.0, GestionNotas.calcularMedia(null), 0.001, "Array nulo debe dar 0");

		// TC-02: Array Vacío -> rama if true, return 0
		assertEquals(0.0, GestionNotas.calcularMedia(new double[] {}), 0.001, "Array vacío debe dar 0");

		// TC-03: Un solo elemento -> bucle ejecuta 1 vez
		double[] notasUnico = { 8.0 };
		assertEquals(8.0, GestionNotas.calcularMedia(notasUnico), 0.001, "Media de un elemento es el elemento mismo");

		// TC-04: Varios elementos -> bucle ejecuta N veces (5+7+9=21/3=7)
		double[] notasVarios = { 5.0, 7.0, 9.0 };
		assertEquals(7.0, GestionNotas.calcularMedia(notasVarios), 0.001, "Cálculo de media estándar");
	}

	// ---------------------------------------------------------------
	// 2. Pruebas para encontrarNotaMaxima
	// Caminos: if(nulo/vacío) -> return 0 | if(notas[i]>max) true/false
	// ---------------------------------------------------------------
	@Test
	public void testEncontrarNotaMaxima() {
		// TC-05: Array Nulo -> rama if true, return 0
		assertEquals(0.0, GestionNotas.encontrarNotaMaxima(null), "Array nulo debe dar 0");

		// TC-06: Array Vacío -> rama if true, return 0
		assertEquals(0.0, GestionNotas.encontrarNotaMaxima(new double[] {}), "Array vacío debe dar 0");

		// TC-07: Un elemento -> bucle no ejecuta (i=1 >= length=1)
		assertEquals(6.5, GestionNotas.encontrarNotaMaxima(new double[] { 6.5 }), "Max de un elemento");

		// TC-08: Máximo al principio -> if(notas[i]>max) siempre false
		double[] maxInicio = { 9.0, 4.0, 5.0 };
		assertEquals(9.0, GestionNotas.encontrarNotaMaxima(maxInicio), "Máximo al principio");

		// TC-09: Máximo en el medio -> if(notas[i]>max) true al menos una vez
		double[] maxMedio = { 3.0, 9.0, 5.0 };
		assertEquals(9.0, GestionNotas.encontrarNotaMaxima(maxMedio), "Máximo en el medio");

		// TC-10: Máximo al final -> if(notas[i]>max) true en última iteración
		double[] maxFinal = { 3.0, 5.0, 10.0 };
		assertEquals(10.0, GestionNotas.encontrarNotaMaxima(maxFinal), "Máximo al final");
	}

	// ---------------------------------------------------------------
	// 3. Pruebas para contarAprobados
	// Caminos: if(nulo) -> return 0 | if(nota>=5) true/false
	// ---------------------------------------------------------------
	@Test
	public void testContarAprobados() {
		// TC-11: Array Nulo -> rama if true, return 0
		assertEquals(0, GestionNotas.contarAprobados(null), "Array nulo tiene 0 aprobados");

		// TC-12: Array Vacío -> bucle no ejecuta, return 0
		assertEquals(0, GestionNotas.contarAprobados(new double[] {}), "Array vacío tiene 0 aprobados");

		// TC-13: Todos suspensos -> if(nota>=5) siempre false
		double[] todosSuspensos = { 3.0, 4.9 };
		assertEquals(0, GestionNotas.contarAprobados(todosSuspensos), "Nadie aprueba");

		// TC-14: Límite exacto 5.0 -> if(nota>=5) true
		double[] borde = { 5.0 };
		assertEquals(1, GestionNotas.contarAprobados(borde), "5.0 debe contar como aprobado");

		// TC-15: Mixto -> if(nota>=5) true y false en el mismo array
		double[] mixto = { 4.0, 8.0, 2.5 };
		assertEquals(1, GestionNotas.contarAprobados(mixto), "Solo uno aprobado");
	}

	// ---------------------------------------------------------------
	// 4. Pruebas para actualizarNota
	// Caminos: if(nulo) | if(índice válido) | if(<0) | else if(>10) | else
	// ---------------------------------------------------------------
	@Test
	public void testActualizarNota() {
		double[] notas = { 2.0, 4.0, 6.0 };

		// TC-16: Array Nulo -> rama if(nulo) true, return inmediato
		assertDoesNotThrow(() -> GestionNotas.actualizarNota(null, 1, 5.0), "Array nulo no debe lanzar excepción");

		// TC-17: Índice Negativo -> if(índice válido) false, no modifica
		GestionNotas.actualizarNota(notas, -1, 5.0);
		assertEquals(2.0, notas[0], "Índice -1 no debe modificar nada");

		// TC-18: Índice Fuera de Rango -> if(índice válido) false, no modifica
		GestionNotas.actualizarNota(notas, 10, 5.0);
		assertEquals(6.0, notas[2], "Índice fuera de rango no debe modificar nada");

		// TC-19: Nota Negativa -> rama if(nuevaNota < 0) true, fija en 0
		GestionNotas.actualizarNota(notas, 1, -5.0);
		assertEquals(0.0, notas[1], "Nota negativa se convierte en 0");

		// TC-20: Nota Excesiva -> rama else if(nuevaNota > 10) true, fija en 10
		GestionNotas.actualizarNota(notas, 1, 15.0);
		assertEquals(10.0, notas[1], "Nota > 10 se convierte en 10");

		// TC-21: Nota Válida -> rama else, asigna directamente
		GestionNotas.actualizarNota(notas, 1, 8.5);
		assertEquals(8.5, notas[1], "Nota válida se asigna correctamente");
	}
}