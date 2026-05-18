package aplicacion;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		InterfazUsuario.TIPO_INTERFAZ = "C";
		int opcion = 0;
		do {
			String menu = "\n";
			menu = menu + " -- MENÚ CALCULADORA --";
			menu = menu + "\n";
			menu = menu + "1. Sumar";
			menu = menu + "\n";
			menu = menu + "2. Restar";
			menu = menu + "\n";
			menu = menu + "3. Multiplicar";
			menu = menu + "\n";
			menu = menu + "4. Dividir";
			menu = menu + "\n";
			menu = menu + "5. Módulo (obtener resto)";
			menu = menu + "\n";
			menu = menu + "0. Salir";
			
			InterfazUsuario.muestraMensaje(menu);
			
//			InterfazUsuario.muestraMensaje("");
//			InterfazUsuario.muestraMensaje(" -- MENÚ CALCULADORA --");
//			InterfazUsuario.muestraMensaje("1. Sumar");
//			InterfazUsuario.muestraMensaje("2. Restar");
//			InterfazUsuario.muestraMensaje("3. Multiplicar");
//			InterfazUsuario.muestraMensaje("4. Dividir");
//			InterfazUsuario.muestraMensaje("5. Módulo (obtener resto)");
//			InterfazUsuario.muestraMensaje("0. Salir");

			opcion = InterfazUsuario.getValorEntero("Introduzca una opción:");

			switch (opcion) {
			case 1:
				sumar();
				break;
			case 2:
				restar();
				break;
			case 3:
				multiplicar();
				break;
			case 4:
				dividir();
				break;
			case 5:
				modulo();
				break;
			case 0:
				break;
			default:
				InterfazUsuario.muestraMensaje("Opción no disponible");
			}

		} while (opcion != 0);

	}

	private static void sumar() {
		InterfazUsuario.muestraMensaje(" -- Suma --");
		double valor1 = InterfazUsuario.getValorDouble("Valor 1:");
		double valor2 = InterfazUsuario.getValorDouble("Valor 2:");
		double resultado = valor1 + valor2;
		InterfazUsuario.muestraMensaje("Resultado: " + resultado);
	}

	private static void restar() {
		InterfazUsuario.muestraMensaje(" -- Resta --");
		double valor1 = InterfazUsuario.getValorDouble("Valor 1:");
		double valor2 = InterfazUsuario.getValorDouble("Valor 2:");
		double resultado = valor1 - valor2;
		InterfazUsuario.muestraMensaje("Resultado: " + resultado);
	}

	private static void multiplicar() {
		InterfazUsuario.muestraMensaje(" -- Multiplicación --");
		double valor1 = InterfazUsuario.getValorDouble("Valor 1:");
		double valor2 = InterfazUsuario.getValorDouble("Valor 2:");
		double resultado = valor1 * valor2;
		InterfazUsuario.muestraMensaje("Resultado: " + resultado);
	}

	private static void dividir() {
		InterfazUsuario.muestraMensaje(" -- División --");
		double valor1 = InterfazUsuario.getValorDouble("Valor 1:");
		double valor2 = InterfazUsuario.getValorDouble("Valor 2:");
		double resultado = valor1 / valor2;
		InterfazUsuario.muestraMensaje("Resultado: " + resultado);
	}

	private static void modulo() {
		InterfazUsuario.muestraMensaje(" -- Módulo --");
		double valor1 = InterfazUsuario.getValorDouble("Valor 1:");
		double valor2 = InterfazUsuario.getValorDouble("Valor 2:");
		double resultado = valor1 % valor2;
		InterfazUsuario.muestraMensaje("Resultado: " + resultado);
	}

}
