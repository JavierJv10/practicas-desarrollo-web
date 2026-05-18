package configuraciones;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Clase encargada de cargar y gestionar la configuración de la aplicación.
 *
 * <p>La configuración se lee desde un fichero llamado {@code config.properties}.
 * Este fichero contiene pares clave-valor que permiten modificar el comportamiento
 * del programa sin necesidad de cambiar el código fuente.</p>
 *
 * <p>Ejemplo de archivo {@code config.properties}:</p>
 *
 * <pre>
 * ruta=C:/datos/fichero.txt
 * agregar=1
 * salto_linea=\n
 * </pre>
 *
 * <p>Esta clase utiliza la clase {@link Properties} para almacenar
 * y acceder a los valores de configuración.</p>
 *
 * <p>La clase implementa el <b>patrón de diseño Singleton</b>, lo que significa
 * que solo puede existir una única instancia de {@code MiConfiguracion}
 * durante toda la ejecución de la aplicación.</p>
 *
 * <p>Esto se consigue mediante:</p>
 * <ul>
 *   <li>Un constructor privado que impide crear objetos desde fuera.</li>
 *   <li>Un atributo estático que guarda la única instancia.</li>
 *   <li>Un método {@link #getInstance()} que devuelve dicha instancia.</li>
 * </ul>
 *
 * @author
 * Alumnado DAM1
 */
public class MiConfiguracion {

    /**
     * Objeto que almacena todas las propiedades leídas desde
     * el fichero de configuración.
     */
    private static Properties properties = null;

    /**
     * Instancia única de la clase (patrón Singleton).
     */
    private static MiConfiguracion instance = null;

    /**
     * Constructor privado.
     *
     * <p>Evita que otras clases puedan crear objetos usando
     * {@code new MiConfiguracion()}.</p>
     *
     * <p>Cuando se crea la instancia, se cargan automáticamente
     * las propiedades del fichero de configuración.</p>
     */
    private MiConfiguracion() {
        cargarPropiedades();
    }

    /**
     * Devuelve la única instancia de {@code MiConfiguracion}.
     *
     * <p>Si todavía no existe ninguna instancia, el método la crea.
     * Si ya existe, simplemente devuelve la instancia existente.</p>
     *
     * @return instancia única de {@code MiConfiguracion}
     */
    public static MiConfiguracion getInstance() {
        if (instance == null) {
            instance = new MiConfiguracion();
        }
        return instance;
    }

    /**
     * Carga las propiedades desde el fichero {@code config.properties}.
     *
     * <p>Si las propiedades ya están cargadas, el método no vuelve
     * a leer el fichero.</p>
     *
     * <p>Utiliza la clase {@link Files} de la API NIO para abrir
     * el fichero y {@link Properties#load(java.io.Reader)}
     * para cargar los valores.</p>
     */
    private void cargarPropiedades() {

        if (properties == null) {

            Path ruta = Path.of("config.properties");

            try {

                BufferedReader bufferedReader = Files.newBufferedReader(ruta);

                properties = new Properties();
                properties.load(bufferedReader);

            } catch (IOException e) {

                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Obtiene la ruta del fichero configurado.
     *
     * <p>Si la propiedad {@code ruta} no existe en el fichero
     * de configuración, se devuelve el valor por defecto
     * {@code "C:/inexistente.txt"}.</p>
     *
     * @return ruta del fichero que utilizará la aplicación
     */
    public String getRutaFichero() {
        return properties.getProperty("ruta", "C:/inexistente.txt");
    }

    /**
     * Indica si el programa debe añadir contenido al fichero
     * o sobrescribirlo.
     *
     * <p>En el fichero de configuración se utiliza:</p>
     *
     * <ul>
     *   <li>{@code agregar=1} → devuelve {@code true}</li>
     *   <li>{@code agregar=0} → devuelve {@code false}</li>
     * </ul>
     *
     * @return {@code true} si se debe agregar contenido al fichero,
     *         {@code false} si se debe sobrescribir
     */
    public boolean getAgregar() {

        String agregar = properties.getProperty("agregar", "0");

        if (agregar.equals("1"))
            return true;
        else
            return false;
    }

    /**
     * Devuelve el carácter de salto de línea configurado.
     *
     * <p>Si la propiedad {@code salto_linea} no existe,
     * se utiliza el valor por defecto {@code "\n"}.</p>
     *
     * @return salto de línea configurado
     */
    public String getSaltoLinea() {
        return properties.getProperty("salto_linea", "\n");
    }
}