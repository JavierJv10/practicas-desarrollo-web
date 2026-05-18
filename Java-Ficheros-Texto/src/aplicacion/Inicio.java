package aplicacion;

import java.util.ArrayList;
import java.util.Scanner;

import configuraciones.MiConfiguracion;

/**
 * Clase principal de la aplicación.
 *
 * <p>Esta clase contiene el método {@code main}, que actúa como punto de entrada
 * del programa.</p>
 *
 * <p>Desde aquí se realizan pruebas de lectura y escritura de ficheros
 * utilizando las clases {@link EscribirFichero} y {@link LeerFichero}.</p>
 *
 * <p>Los parámetros de configuración (ruta del fichero, modo de escritura
 * y salto de línea) se obtienen desde la clase {@link MiConfiguracion},
 * que lee dichos valores del fichero {@code config.properties}.</p>
 *
 * <p>Las clases de servicios del programa utilizan el <b>patrón Singleton</b>,
 * por lo que sus objetos se obtienen mediante métodos como:</p>
 *
 * <pre>
 * MiConfiguracion.getInstance()
 * LeerFichero.getInstancia()
 * EscribirFichero.getInstancia()
 * </pre>
 */
public class Inicio {

	/**
	 * Ruta del fichero que se utilizará para las operaciones de lectura
	 * y escritura.
	 *
	 * <p>Este valor se obtiene desde el fichero de configuración.</p>
	 */
	private static String RUTA = null;

	/**
	 * Indica si el contenido debe añadirse al final del fichero
	 * o sobrescribirse.
	 */
	private static boolean AGREGAR = false;

	/**
	 * Punto de entrada del programa.
	 *
	 * <p>Realiza las siguientes operaciones:</p>
	 * <ul>
	 *   <li>Carga la configuración de la aplicación.</li>
	 *   <li>Muestra por consola los valores de configuración.</li>
	 *   <li>Ejecuta una prueba de escritura en fichero.</li>
	 *   <li>Ejecuta una prueba de lectura del fichero.</li>
	 * </ul>
	 *
	 * @param args argumentos de línea de comandos (no utilizados en este programa)
	 */
	public static void main(String[] args) {

		/**
		 * Obtenemos la instancia única de configuración.
		 */
		MiConfiguracion mc = MiConfiguracion.getInstance();

		RUTA = mc.getRutaFichero();
		AGREGAR = mc.getAgregar();
		String saltoLinea = mc.getSaltoLinea();

		// Mostramos los valores de configuración
		System.out.println(RUTA);
		System.out.println(AGREGAR);
		System.out.println(saltoLinea);

		/**
		 * Volvemos a solicitar la configuración para demostrar
		 * que el patrón Singleton devuelve siempre la misma instancia.
		 */
		MiConfiguracion mc2 = MiConfiguracion.getInstance();

		// Ejecutamos pruebas de escritura y lectura
		testEscribir();
		testLeer();
	}

	/**
	 * Método de prueba que escribe varias líneas en un fichero.
	 *
	 * <p>El método:</p>
	 * <ul>
	 *   <li>Crea una lista de líneas de texto.</li>
	 *   <li>Solicita dos líneas adicionales al usuario mediante teclado.</li>
	 *   <li>Escribe todas las líneas en el fichero utilizando
	 *   {@link EscribirFichero}.</li>
	 * </ul>
	 */
	private static void testEscribir() {

		/**
		 * Obtenemos la instancia única del servicio de escritura.
		 */
		EscribirFichero escribirFichero = EscribirFichero.getInstancia();

		/**
		 * Lista donde se almacenarán las líneas que se escribirán
		 * en el fichero.
		 */
		ArrayList<String> lineas = new ArrayList<String>();

		lineas.add("En un lugar de la Mancha");
		lineas.add("de cuyo lugar no quiero acordarme,");
		lineas.add("no hace mucho que vivía ...");

		/**
		 * Lectura de líneas introducidas por el usuario.
		 */
		Scanner scanner = new Scanner(System.in);

		System.out.println("Introduce una línea:");
		lineas.add(scanner.nextLine());

		System.out.println("Introduce una línea:");
		lineas.add(scanner.nextLine());

		scanner.close();

		// Escribimos las líneas en el fichero
		escribirFichero.escribir(RUTA, AGREGAR, lineas);
	}

	/**
	 * Método de prueba que lee el contenido del fichero
	 * y lo muestra por consola.
	 *
	 * <p>Utiliza la clase {@link LeerFichero} para leer todo el
	 * contenido del archivo indicado por la variable {@code RUTA}.</p>
	 */
	private static void testLeer() {

		/**
		 * Obtenemos la instancia única del servicio de lectura.
		 */
		LeerFichero leerFichero = LeerFichero.getInstancia();

		// Leemos el contenido del fichero
		String contenido = leerFichero.leer(RUTA);

		// Mostramos el contenido por consola
		System.out.println(contenido);
	}
}