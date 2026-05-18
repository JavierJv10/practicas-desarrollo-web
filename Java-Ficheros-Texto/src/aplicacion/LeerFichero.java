package aplicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import configuraciones.MiConfiguracion;

/**
 * Clase encargada de leer el contenido de un fichero de texto.
 *
 * <p>Utiliza la API NIO de Java ({@code java.nio.file}), que proporciona
 * una forma moderna y eficiente de trabajar con ficheros.</p>
 *
 * <p>La clase implementa el <b>patrón de diseño Singleton</b>, lo que significa
 * que solo puede existir una única instancia de {@code LeerFichero} en toda
 * la aplicación.</p>
 *
 * <p>Esto se consigue mediante:</p>
 * <ul>
 *   <li>Un atributo estático que guarda la única instancia.</li>
 *   <li>Un constructor privado que impide crear objetos desde fuera.</li>
 *   <li>Un método estático {@link #getInstancia()} que devuelve la instancia.</li>
 * </ul>
 *
 * <p>El contenido del fichero se lee línea a línea mediante un
 * {@link BufferedReader} y se reconstruye en una única cadena de texto.</p>
 *
 * <p>El salto de línea utilizado al reconstruir el texto se obtiene
 * desde la clase {@link MiConfiguracion}.</p>
 */
public class LeerFichero {

    /**
     * Única instancia de la clase (patrón Singleton).
     */
    private static LeerFichero instancia = null;

    /**
     * Constructor privado para impedir que otras clases creen objetos
     * utilizando {@code new LeerFichero()}.
     */
    private LeerFichero() {
    }

    /**
     * Devuelve la única instancia de {@code LeerFichero}.
     *
     * <p>Si la instancia todavía no existe, se crea. Si ya existe,
     * se devuelve la misma instancia.</p>
     *
     * @return instancia única de {@code LeerFichero}
     */
    public static LeerFichero getInstancia() {
        if (instancia == null) {
            instancia = new LeerFichero();
        }
        return instancia;
    }

    /**
     * Objeto de configuración de la aplicación.
     *
     * <p>Permite acceder a parámetros definidos en el fichero de
     * configuración, como el salto de línea utilizado.</p>
     */
    MiConfiguracion mc = MiConfiguracion.getInstance();

    /**
     * Cadena que representa el salto de línea utilizado al
     * reconstruir el contenido del fichero leído.
     */
    String saltoLinea = mc.getSaltoLinea();

    /**
     * Lee un fichero de texto y devuelve su contenido completo
     * como una cadena de caracteres.
     *
     * <p>El fichero se lee línea a línea utilizando
     * {@link BufferedReader#readLine()} hasta llegar al final
     * del archivo.</p>
     *
     * <p>Las líneas leídas se almacenan temporalmente en un
     * {@link StringBuilder}, que posteriormente se convierte
     * en un {@link String}.</p>
     *
     * @param rutaFichero ruta del fichero que se desea leer
     * @return una cadena con todo el contenido del fichero leído
     */
    String leer(String rutaFichero) {

        String contenido = null;

        try {

            /**
             * {@link Path} representa la ruta del fichero en el
             * sistema de archivos.
             */
            Path path = Paths.get(rutaFichero);

            /**
             * Se crea un {@link BufferedReader} utilizando la clase
             * {@link Files} de la API NIO.
             *
             * <p>La codificación {@code UTF-8} se especifica de forma
             * explícita para garantizar la correcta lectura de caracteres.</p>
             */
            BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8);

            String linea = null;

            /**
             * {@link StringBuilder} se utiliza para construir cadenas
             * de texto de forma eficiente cuando se realizan muchas
             * concatenaciones.
             */
            StringBuilder sb = new StringBuilder();

            /**
             * Se leen las líneas del fichero hasta que {@code readLine()}
             * devuelve {@code null}, lo que indica que se ha llegado
             * al final del fichero.
             */
            while ((linea = bufferedReader.readLine()) != null) {

                sb.append(linea);
                sb.append(saltoLinea);
            }

            // Convertimos el contenido acumulado en un String
            contenido = sb.toString();

            // Cerramos el flujo de lectura
            bufferedReader.close();

        } catch (IOException e) {

            /**
             * Si ocurre un error durante la lectura del fichero,
             * se muestra el mensaje de error en la consola.
             */
            System.err.println(e.getMessage());
        }

        return contenido;
    }
}