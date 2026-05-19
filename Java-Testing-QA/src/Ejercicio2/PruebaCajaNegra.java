package Ejercicio2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Pruebas de Caja Negra - Requisitos y Límites")
class GestionPedidosCajaNegraTest {

	// ---------------------------------------------------------------
	// PARTICIONES EQUIVALENTES - Estructura del array
	// ---------------------------------------------------------------
	@Test
	@DisplayName("Caja Negra: Particiones de estructura del array")
	void testParticionesArray() {
		// Partición: Array nulo
		assertEquals(0, GestionPedidosTienda.calcularTotalPedido(null), "Nulo devuelve 0");
		assertEquals(0, GestionPedidosTienda.contarProductosCaros(null, 10.0), "Nulo devuelve 0");

		// Partición: Array vacío
		assertEquals(0, GestionPedidosTienda.calcularTotalPedido(new double[] {}), "Vacío devuelve 0");
		assertEquals(0, GestionPedidosTienda.contarProductosCaros(new double[] {}, 10.0), "Vacío devuelve 0");

		// Partición: Varios elementos
		double[] precios = { 10.0, 20.0, 30.0 };
		assertEquals(60.0, GestionPedidosTienda.calcularTotalPedido(precios), 0.001, "Suma correcta");
		assertEquals(3, GestionPedidosTienda.contarProductosCaros(precios, 5.0), "Todos son caros");
	}

	// ---------------------------------------------------------------
	// PARTICIONES EQUIVALENTES - Tramos de descuento
	// ---------------------------------------------------------------
	@Test
	@DisplayName("Caja Negra: Particiones Equivalentes de Descuento")
	void testParticionesDescuento() {
		// Partición: Total negativo -> devuelve 0
		assertEquals(0, GestionPedidosTienda.aplicarDescuento(-50.0), "Negativo devuelve 0");

		// Partición: Sin descuento (representante: 10.0)
		assertEquals(10.0, GestionPedidosTienda.aplicarDescuento(10.0), 0.001, "Sin descuento");

		// Partición: Tramo 5% (representante: 75.0) -> 75 - (75 * 0.05) = 71.25
		assertEquals(71.25, GestionPedidosTienda.aplicarDescuento(75.0), 0.001, "Descuento 5%");

		// Partición: Tramo 10% (representante: 150.0) -> 150 - (150 * 0.10) = 135.0
		assertEquals(135.0, GestionPedidosTienda.aplicarDescuento(150.0), 0.001, "Descuento 10%");
	}

	// ---------------------------------------------------------------
	// VALORES LÍMITE - Umbrales de descuento
	// ---------------------------------------------------------------
	@Test
	@DisplayName("Caja Negra: Valores Límite en Umbrales de Descuento")
	void testValoresLimiteDescuento() {
		// Alrededor del umbral 50
		assertEquals(49.99, GestionPedidosTienda.aplicarDescuento(49.99), 0.001, "Justo antes del 5%");
		assertEquals(47.5, GestionPedidosTienda.aplicarDescuento(50.0), 0.001, "Frontera exacta del 5%");
		assertEquals(47.5095, GestionPedidosTienda.aplicarDescuento(50.01), 0.0001, "Justo después del 5%");

		// Alrededor del umbral 100
		assertEquals(94.9905, GestionPedidosTienda.aplicarDescuento(99.99), 0.0001, "Justo antes del 10%");
		assertEquals(90.0, GestionPedidosTienda.aplicarDescuento(100.0), 0.001, "Frontera exacta del 10%");
		assertEquals(90.009, GestionPedidosTienda.aplicarDescuento(100.01), 0.0001, "Justo después del 10%");
	}

	// ---------------------------------------------------------------
	// VALORES LÍMITE - Índices y precios en actualizarPrecio
	// ---------------------------------------------------------------
	@Test
	@DisplayName("Caja Negra: Índices válidos e inválidos en actualizarPrecio")
	void testIndicesActualizacion() {
		double[] precios = { 10.0, 20.0 }; // length = 2

		// Array nulo -> no lanza excepción
		assertDoesNotThrow(() -> GestionPedidosTienda.actualizarPrecio(null, 0, 5.0),
				"Array nulo no debe lanzar excepción");

		// Índice válido (0, primer elemento)
		GestionPedidosTienda.actualizarPrecio(precios, 0, 6.0);
		assertEquals(6.0, precios[0], "Índice 0 debe modificar correctamente");

		// Índice válido (length-1, último elemento)
		GestionPedidosTienda.actualizarPrecio(precios, 1, 25.0);
		assertEquals(25.0, precios[1], "Índice length-1 debe modificar correctamente");

		// Índice inválido: -1 (justo por debajo del mínimo)
		GestionPedidosTienda.actualizarPrecio(precios, -1, 5.0);
		assertEquals(6.0, precios[0], "Índice -1 no debe modificar el array");

		// Índice inválido: igual a length (justo fuera de rango)
		GestionPedidosTienda.actualizarPrecio(precios, 2, 5.0);
		assertEquals(25.0, precios[1], "Índice igual a length no debe modificar el array");

		// Precio negativo -> satura en 0
		GestionPedidosTienda.actualizarPrecio(precios, 0, -99.0);
		assertEquals(0.0, precios[0], "Precio negativo debe saturar en 0");
	}
}