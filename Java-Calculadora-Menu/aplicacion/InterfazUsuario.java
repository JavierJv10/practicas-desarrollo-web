package aplicacion;

import java.util.Scanner;

import javax.swing.JOptionPane;

public class InterfazUsuario {

	// Valores: C: Consola, D:Desktop
	public static String TIPO_INTERFAZ = "C";

	public static void muestraMensaje(String mensaje) {
		if (TIPO_INTERFAZ == "C") {
			System.out.println(mensaje);
		} else if (TIPO_INTERFAZ == "D") {
			JOptionPane.showMessageDialog(null, mensaje, "Desktop", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	public static int getValorEntero(String mensaje) {
		int valor = 0;
		muestraMensaje(mensaje);
		Scanner scanner = new Scanner(System.in);
		valor = scanner.nextInt();
		return valor;
	}

	public static double getValorDouble(String mensaje) {
		double valor = 0;
		muestraMensaje(mensaje);
		Scanner scanner = new Scanner(System.in);
		valor = scanner.nextDouble();
		return valor;
	}
}
