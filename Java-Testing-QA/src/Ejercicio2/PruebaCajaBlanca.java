package Ejercicio2;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Pruebas de Caja Blanca - Gestión de Pedidos")
class GestionPedidosCajaBlancaTest {

	// ---------------------------------------------------------------
	// PRUEBAS DE CAMINOS Y RAMAS (aplicarDescuento)
	// Caminos: if(total<0) | if(>=100) | else if(>=50) | sin descuento
	// ---------------------------------------------------------------
	@Test
	@DisplayName("Caja Blanca: Probar todos los tramos de descuento (Ramas)")
	void testRamasDescuento() {
		// Camino 1: total < 0 -> rama de seguridad, return 0
		assertEquals(0, GestionPedidosTienda.aplicarDescuento(-10.0), "Debe retornar 0 para totales negativos");

		// Camino 2: total >= 100 -> rama 10%, descuento = 0.10
		// 100 - (100 * 0.10) = 90
		assertEquals(90.0, GestionPedidosTienda.aplicarDescuento(100.0), 0.001, "Descuento del 10%");

		// Camino 3: total >= 50 -> rama 5%, descuento = 0.05
		// 80 - (80 * 0.05) = 76
		assertEquals(76.0, GestionPedidosTienda.aplicarDescuento(80.0), 0.001, "Descuento del 5%");

		// Camino 4: total entre 0 y 49.99 -> descuento = 0 (else implícito)
		assertEquals(30.0, GestionPedidosTienda.aplicarDescuento(30.0), 0.001, "Sin descuento");
	}

	// ---------------------------------------------------------------
	// PRUEBAS DE BUCLES (calcularTotalPedido y contarProductosCaros)
	// Cobertura: 0 iteraciones, 1 iteración, N iteraciones
	// ---------------------------------------------------------------
	@Test
	@DisplayName("Caja Blanca: Cobertura de bucles (0, 1 y N iteraciones)")
	void testCoberturaBucles() {
		// Rama: Array nulo -> return 0 inmediato (sin entrar al bucle)
		assertEquals(0, GestionPedidosTienda.calcularTotalPedido(null), "Nulo devuelve 0");
		assertEquals(0, GestionPedidosTienda.contarProductosCaros(null, 10.0), "Nulo devuelve 0");

		// Caso 0 iteraciones: Array vacío
		double[] vacio = {};
		assertEquals(0, GestionPedidosTienda.calcularTotalPedido(vacio), "Vacío devuelve 0");
		assertEquals(0, GestionPedidosTienda.contarProductosCaros(vacio, 10.0), "Vacío devuelve 0");

		// Caso 1 iteración: Un solo elemento
		double[] uno = { 25.0 };
		assertEquals(25.0, GestionPedidosTienda.calcularTotalPedido(uno), 0.001, "Un elemento");
		assertEquals(1, GestionPedidosTienda.contarProductosCaros(uno, 10.0), "Un elemento caro");

		// Caso N iteraciones: Varios elementos
		double[] varios = { 10.0, 20.0, 30.0 };
		assertEquals(60.0, GestionPedidosTienda.calcularTotalPedido(varios), 0.001, "Suma de varios");
	}

	// ---------------------------------------------------------------
	// PRUEBAS DE SENTENCIAS Y ROBUSTEZ (actualizarPrecio)
	// Caminos: if(null) | if(índice válido) | if(precio<0) | else
	// ---------------------------------------------------------------
	@Test
	@DisplayName("Caja Blanca: Cobertura de sentencias en actualización")
	void testActualizarPrecioCajaBlanca() {
		double[] precios = { 10.0, 20.0 };

		// Rama: precios == null -> return inmediato (sentencia de guarda)
		assertDoesNotThrow(() -> GestionPedidosTienda.actualizarPrecio(null, 0, 50.0),
				"Array nulo no debe lanzar excepción");

		// Rama: nuevoPrecio < 0 -> sentencia que fija precio en 0
		GestionPedidosTienda.actualizarPrecio(precios, 0, -5.0);
		assertEquals(0.0, precios[0], "Debe ejecutar la línea que fija el precio en 0");

		// Rama: else -> sentencia de asignación normal
		GestionPedidosTienda.actualizarPrecio(precios, 1, 25.0);
		assertEquals(25.0, precios[1], "Debe ejecutar la línea de asignación de nuevoPrecio");
	}

	// ---------------------------------------------------------------
	// PRUEBAS DE RAMA: ÍNDICES INVÁLIDOS (actualizarPrecio)
	// Forzar que (indice >= 0 && indice < precios.length) sea FALSA
	// ---------------------------------------------------------------
	@Test
	@DisplayName("Caja Blanca: Cobertura de rama para índices inválidos")
	void testIndicesInvalidos() {
		double[] precios = { 10.0 };

		// Índice negativo -> condición false, no modifica
		GestionPedidosTienda.actualizarPrecio(precios, -1, 5.0);
		assertEquals(10.0, precios[0], "Índice negativo no debe modificar el array");

		// Índice igual a length -> condición false, no modifica
		GestionPedidosTienda.actualizarPrecio(precios, 1, 5.0);
		assertEquals(10.0, precios[0], "Índice igual a length no debe modificar el array");

		// Índice muy grande -> condición false, no modifica
		GestionPedidosTienda.actualizarPrecio(precios, 99, 5.0);
		assertEquals(10.0, precios[0], "Índice muy grande no debe modificar el array");
	}
}