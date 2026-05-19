package Ejercicio3;

public class SistemaReservaAsientos {

	public static void main(String[] args) {
		boolean[] fila = { false, true, false, false, true };

		System.out.println("Asientos libres: " + contarLibres(fila));
		System.out.println("Asientos ocupados: " + contarOcupados(fila));
		System.out.println("Fila completa: " + estaCompleta(fila));

		reservarAsiento(fila, 2);
		cancelarReserva(fila, 1);
		reservarAsiento(fila, 10); // índice inválido

		System.out.println("Estado final fila: " + java.util.Arrays.toString(fila));
	}

	public static int contarLibres(boolean[] fila) {
		if (fila == null) {
			return 0;
		}

		int libres = 0;
		for (int i = 0; i < fila.length; i++) {
			if (!fila[i]) {
				libres++;
			}
		}
		return libres;
	}

	public static int contarOcupados(boolean[] fila) {
		if (fila == null) {
			return 0;
		}
		int ocupados = 0;
		for (int i = 0; i < fila.length; i++) {
			if (fila[i]) {
				ocupados++;
			}
		}
		return ocupados;
	}

	public static boolean estaCompleta(boolean[] fila) {
		if (fila == null || fila.length == 0) {
			return false;
		}
		for (int i = 0; i < fila.length; i++) {
			if (!fila[i]) {
				return false;
			}
		}
		return true;
	}

	public static void reservarAsiento(boolean[] fila, int indice) {
		if (fila == null) {
			return;
		}
		if (indice >= 0 && indice < fila.length) {
			if (!fila[indice]) {
				fila[indice] = true;
			}
		}
	}

	public static void cancelarReserva(boolean[] fila, int indice) {
		if (fila == null) {
			return;
		}
		if (indice >= 0 && indice < fila.length) {
			if (fila[indice]) {
				fila[indice] = false;
			}
		}
	}
}