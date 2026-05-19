package Ejercicio2;

public class GestionPedidosTienda {

	public static void main(String[] args) {
		double[] precios = { 12.5, 20.0, 5.0, 60.0 };

		double total = calcularTotalPedido(precios);
		System.out.println("Total sin descuento: " + total);
		System.out.println("Total con descuento: " + aplicarDescuento(total));
		System.out.println("Productos > 10 euros: " + contarProductosCaros(precios, 10));

		actualizarPrecio(precios, 3, -5);
		System.out.println("Precios actualizados: " + java.util.Arrays.toString(precios));
	}

	public static double calcularTotalPedido(double[] precios) {
		if (precios == null) {
			return 0;
		}
		double total = 0;
		for (int i = 0; i < precios.length; i++) {
			total += precios[i];
		}
		return total;
	}

	public static double aplicarDescuento(double total) {
		if (total < 0) {
			return 0;
		}
		double descuento = 0;
		if (total >= 100) {
			descuento = 0.10;
		} else if (total >= 50) {
			descuento = 0.05;
		}
		return total - (total * descuento);
	}

	public static int contarProductosCaros(double[] precios, double umbral) {
		if (precios == null) {
			return 0;
		}
		int contador = 0;
		for (int i = 0; i < precios.length; i++) {
			if (precios[i] > umbral) {
				contador++;
			}
		}
		return contador;
	}

	public static void actualizarPrecio(double[] precios, int indice, double nuevoPrecio) {
		if (precios == null) {
			return;
		}
		if (indice >= 0 && indice < precios.length) {

			if (nuevoPrecio < 0) {
				precios[indice] = 0;
			} else {
				precios[indice] = nuevoPrecio;
			}
		}
	}
}