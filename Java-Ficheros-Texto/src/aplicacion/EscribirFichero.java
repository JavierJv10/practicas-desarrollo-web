package aplicacion;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase encargada de escribir contenido en un fichero de texto.
 *
 * <p>Utiliza la API NIO de Java ({@code java.nio.file}), que proporciona una forma
 * moderna y flexible de trabajar con archivos.</p>
 *
 * <p>Esta clase implementa el <b>patrón de diseño Singleton</b>, lo que significa
 * que sólo puede existir una única instancia de {@code EscribirFichero} en toda
 * la aplicación.</p>
 *
 * <p>Esto se consigue mediante:</p>
 * <ul>
 *   <li>Un atributo estático que guarda la única instancia.</li>
 *   <li>Un constructor privado que impide crear objetos desde fuera.</li>
 *   <li>Un método estático {@link #getInstancia()} que devuelve la instancia.</li>
 * </ul>
 *
 * <p>La clase permite:</p>
 * <ul>
 *   <li>Crear el fichero si no existe.</li>
 *   <li>Sobrescribir el contenido existente.</li>
 *   <li>Añadir contenido al final del fichero.</li>
 * </ul>
 *
 * <p>El contenido a escribir se recibe como una colección de líneas
 * almacenadas en un {@link ArrayList} de {@link String}.</p>
 */
public class EscribirFichero {

    /**
     * Única instancia de la clase (patrón Singleton).
     */
    private static EscribirFichero instancia = null;

    /**
     * Constructor privado para impedir que otras clases creen
     * objetos utilizando {@code new EscribirFichero()}.
     */
    private EscribirFichero() {
    }

    /**
     * Devuelve la única instancia de {@code EscribirFichero}.
     *
     * <p>Si la instancia todavía no existe, se crea. Si ya existe,
     * se devuelve la misma instancia previamente creada.</p>
     *
     * @return instancia única de {@code EscribirFichero}
     */
    public static EscribirFichero getInstancia() {
        if (instancia == null) {
            instancia = new EscribirFichero();
        }
        return instancia;
    }

    /**
     * Escribe un conjunto de líneas en un fichero de texto.
     *
     * <p>El comportamiento depende del parámetro {@code agregar}:</p>
     * <ul>
     *   <li>{@code true}: el contenido se añade al final del fichero.</li>
     *   <li>{@code false}: el contenido existente se elimina y se escribe uno nuevo.</li>
     * </ul>
     *
     * <p>Si el fichero no existe, se crea automáticamente mediante
     * {@link StandardOpenOption#CREATE}.</p>
     *
     * @param rutaFichero ruta del fichero donde se escribirá el contenido
     * @param agregar indica si se debe añadir contenido al final del fichero
     *                ({@code true}) o sobrescribirlo ({@code false})
     * @param contenido lista de líneas que se escribirán en el fichero
     */
    void escribir(String rutaFichero, boolean agregar, ArrayList<String> contenido) {

        /**
         * {@link Path} representa la ruta del fichero en el sistema de archivos.
         */
        Path path = Paths.get(rutaFichero);

        /**
         * Modo de apertura del fichero.
         */
        StandardOpenOption modoApertura;

        // Si agregar = true → añadir contenido al final del fichero
        if (agregar) {
            modoApertura = StandardOpenOption.APPEND;
        }
        // Si agregar = false → sobrescribir contenido existente
        else {
            modoApertura = StandardOpenOption.TRUNCATE_EXISTING;
        }

        /*
         * Files.newBufferedWriter crea un BufferedWriter utilizando la API NIO.
         *
         * Parámetros:
         * - path → ruta del fichero
         * - StandardCharsets.UTF_8 → codificación de caracteres
         * - StandardOpenOption.CREATE → crea el fichero si no existe
         * - modoApertura → modo de escritura
         */
        try {

            BufferedWriter bufferedWriter = Files.newBufferedWriter(
                    path,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    modoApertura);

            // Comprobamos que la lista no sea nula ni esté vacía
            if (contenido != null && contenido.size() > 0) {

                // Recorremos cada línea de texto
                for (String linea : contenido) {

                    bufferedWriter.write(linea);
                    bufferedWriter.newLine();
                }
            }

            // Cerramos el flujo de escritura
            bufferedWriter.close();

        } catch (IOException e) {

            /**
             * Si ocurre un error durante la escritura del fichero,
             * se muestra el mensaje de error en la consola.
             */
            System.err.println(e.getMessage());
        }
    }
}