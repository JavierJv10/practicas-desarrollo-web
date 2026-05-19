package Ejercicios_a_b_c;

public class FiltroIdioma_c {

    private static final String CODIGO_ESPANOL = "ESP";
    private static final String CODIGO_INGLES  = "ENG";
    private static final String CODIGO_FRANCES = "FRA";

    private static final int NIVEL_MINIMO_ESPANOL = 1;
    private static final int NIVEL_MINIMO_INGLES  = 2;
    private static final int NIVEL_MINIMO_FRANCES = 3;

    public void mostrarMensajes(String idioma, int nivel) {
        String idiomaEnMayusculas = idioma.toUpperCase();

        boolean esEspanol = idiomaEnMayusculas.contains(CODIGO_ESPANOL);
        boolean esIngles  = idiomaEnMayusculas.contains(CODIGO_INGLES);
        boolean esFrances = idiomaEnMayusculas.contains(CODIGO_FRANCES);

        boolean nivelSuficienteEspanol = nivel >= NIVEL_MINIMO_ESPANOL;
        boolean nivelSuficienteIngles  = nivel >= NIVEL_MINIMO_INGLES;
        boolean nivelSuficienteFrances = nivel >= NIVEL_MINIMO_FRANCES;

        boolean mostrarAvanzados = (esEspanol && nivelSuficienteEspanol)
                                || (esIngles  && nivelSuficienteIngles)
                                || (esFrances && nivelSuficienteFrances);

        if (mostrarAvanzados) {
            System.out.println("Mensajes avanzados activados");
        } else {
            System.out.println("Mensajes básicos activados");
        }
    }
}