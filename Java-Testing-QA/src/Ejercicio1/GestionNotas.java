package Ejercicio1;

public class GestionNotas {

	public static void main(String[] args) {
		double[] notas = { 4.5, 8.0, 6.5, 9.0, 3.0 };

		System.out.println("Nota media: " + calcularMedia(notas));
		System.out.println("Nota más alta: " + encontrarNotaMaxima(notas));
		System.out.println("Número de aprobados: " + contarAprobados(notas));

		actualizarNota(notas, 2, 7.5);
		System.out.println("Notas actualizadas: " + java.util.Arrays.toString(notas));
	}

	public static double calcularMedia(double[] notas) {
		if (notas == null || notas.length == 0) {
			return 0;
		}
		double suma = 0;
		for (int i = 0; i < notas.length; i++) {
			suma += notas[i];
		}
		return suma / notas.length;

	}

	public static double encontrarNotaMaxima(double[] notas) {
		if (notas == null || notas.length == 0) {
			return 0;
		}
		double max = notas[0];
		for (int i = 1; i < notas.length; i++) {
			if (notas[i] > max) {
				max = notas[i];
			}
		}
		return max;
	}

	public static int contarAprobados(double[] notas) {
		if (notas == null) {
			return 0;
		}
		int contador = 0;
		for (int i = 0; i < notas.length; i++) {
			if (notas[i] >= 5.0) {
				contador++;
			}
		}
		return contador;
	}

	public static void actualizarNota(double[] notas, int indice, double nuevaNota) {
		if (notas == null) {
			return;
		}
		if (indice >= 0 && indice < notas.length) {

			if (nuevaNota < 0) {
				notas[indice] = 0;
			} else if (nuevaNota > 10) {
				notas[indice] = 10;
			} else {
				notas[indice] = nuevaNota;
			}
		}
	}
}