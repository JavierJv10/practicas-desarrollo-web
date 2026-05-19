package Ejercicio_d;

public class Equipo {

    private String nombre;
    private String[] jugadores = new String[11];


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getJugador(int posicion) {
        return jugadores[posicion];
    }

    public int getTotalJugadores() {
        return jugadores.length;
    }

    public String[] getJugadores() {
        return jugadores.clone();
    }


    public void agregarJugador(String jugador, int posicion) {
        jugadores[posicion] = jugador;
    }

    public void imprimirPlantilla() {
        System.out.println("Equipo: " + nombre);
        for (int i = 0; i < jugadores.length; i++) {
            System.out.println("Posición " + i + ": " + jugadores[i]);
        }
    }

    public void mostrarJugadoresConNombre() {
        for (int i = 0; i < jugadores.length; i++) {
            System.out.println(nombre + " - " + jugadores[i]);
        }
    }
}