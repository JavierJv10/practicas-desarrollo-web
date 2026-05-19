package Ejercicio2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

class GestionPedidosTest {

	// --- 1. PRUEBAS PARA calcularTotalPedido ---

	@Test
	@DisplayName("Total Pedido: Cobertura de bucles y nulos")
	void testCalcularTotalPedido() {
		// Caso: Array nulo
		assertEquals(0, GestionPedidosTienda.calcularTotalPedido(null), "Array nulo debe dar 0");

		// Caso: Array vacío (Bucle 0 iteraciones)
		assertEquals(0, GestionPedidosTienda.calcularTotalPedido(new double[] {}), "Array vacío debe dar 0");

		// Caso: Un solo elemento (Bucle 1 iteración)
		assertEquals(8.0, GestionPedidosTienda.calcularTotalPedido(new double[] { 8.0 }), 0.001,
				"Un elemento debe dar su propio valor");

		// Caso: Varios elementos
		double[] precios = { 10.5, 5.5, 4.0 };
		assertEquals(20.0, GestionPedidosTienda.calcularTotalPedido(precios), 0.001, "La suma debe ser exacta");
	}

	// --- 2. PRUEBAS PARA aplicarDescuento (VALORES LÍMITE Y RAMAS) ---

	@Test
	@DisplayName("Descuentos: Particiones y valores límite (0%, 5%, 10%)")
	void testAplicarDescuento() {
		// Partición: Total negativo
		assertEquals(0, GestionPedidosTienda.aplicarDescuento(-1.0), "Total negativo debe dar 0");

		// Partición: Sin descuento (0 a 49.99)
		assertEquals(49.99, GestionPedidosTienda.aplicarDescuento(49.99), 0.001, "Sin descuento");

		// Partición: Descuento 5% (Frontera 50.0) -> 50 - (50 * 0.05) = 47.5
		assertEquals(47.5, GestionPedidosTienda.aplicarDescuento(50.0), 0.001, "Descuento 5% en frontera");

		// Valor límite 50.01 -> 50.01 - (50.01 * 0.05) = 47.5095
		assertEquals(47.5095, GestionPedidosTienda.aplicarDescuento(50.01), 0.0001, "Descuento 5% justo por encima");

		// Partición: Límite superior tramo 5% (99.99)
		assertEquals(94.9905, GestionPedidosTienda.aplicarDescuento(99.99), 0.0001, "Máximo del tramo 5%");

		// Partición: Descuento 10% (Frontera 100.0) -> 100 - (100 * 0.10) = 90.0
		assertEquals(90.0, GestionPedidosTienda.aplicarDescuento(100.0), 0.001, "Descuento 10% en frontera");

		// Valor límite 100.01 -> 100.01 - (100.01 * 0.10) = 90.009
		assertEquals(90.009, GestionPedidosTienda.aplicarDescuento(100.01), 0.0001, "Descuento 10% justo por encima");
	}

	// --- 3. PRUEBAS PARA contarProductosCaros ---

	@Test
	@DisplayName("Productos caros: Cobertura de condiciones y nulos")
	void testContarProductosCaros() {
		// Caso: Array nulo
		assertEquals(0, GestionPedidosTienda.contarProductosCaros(null, 10.0), "Array nulo debe dar 0");

		// Caso: Array vacío (Bucle 0 iteraciones)
		assertEquals(0, GestionPedidosTienda.contarProductosCaros(new double[] {}, 10.0), "Array vacío debe dar 0");

		// Caso: Mezcla de productos -> Solo 15 y 20 son > 10.0
		double[] precios = { 5.0, 10.0, 15.0, 20.0 };
		assertEquals(2, GestionPedidosTienda.contarProductosCaros(precios, 10.0),
				"Solo los mayores que el umbral cuentan");
	}

	// --- 4. PRUEBAS PARA actualizarPrecio (ROBUSTEZ) ---

	@Test
	@DisplayName("Actualizar Precio: Índices válidos/inválidos y saturación")
	void testActualizarPrecio() {
		double[] precios = { 10.0, 20.0 };

		// Caso: Array nulo (no debe lanzar excepción)
		assertDoesNotThrow(() -> GestionPedidosTienda.actualizarPrecio(null, 0, 5.0),
				"Array nulo no debe lanzar excepción");

		// Caso: Índice inválido negativo
		GestionPedidosTienda.actualizarPrecio(precios, -1, 5.0);
		assertEquals(10.0, precios[0], "Índice negativo no debe modificar el array");

		// Caso: Índice inválido fuera de rango
		GestionPedidosTienda.actualizarPrecio(precios, 2, 5.0);
		assertEquals(20.0, precios[1], "Índice fuera de rango no debe modificar el array");

		// Caso: Precio negativo (saturación a 0)
		GestionPedidosTienda.actualizarPrecio(precios, 0, -5.0);
		assertEquals(0.0, precios[0], "Precio negativo debe fijarse en 0");

		// Caso: Actualización normal
		GestionPedidosTienda.actualizarPrecio(precios, 1, 15.0);
		assertEquals(15.0, precios[1], "Precio válido debe asignarse correctamente");
	}
}