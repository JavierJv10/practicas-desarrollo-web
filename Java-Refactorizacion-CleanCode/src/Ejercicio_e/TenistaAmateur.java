package Ejercicio_e;

public class TenistaAmateur extends Tenista {

    @Override
    public double calcularCuotaMensual() {
        return 20.0;
    }

    @Override
    public String descripcion() {
        return "Tenista amateur";
    }
}