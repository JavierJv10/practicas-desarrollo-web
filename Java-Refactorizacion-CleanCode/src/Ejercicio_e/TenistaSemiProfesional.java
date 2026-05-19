package Ejercicio_e;

public class TenistaSemiProfesional extends Tenista {

    @Override
    public double calcularCuotaMensual() {
        return 35.0;
    }

    @Override
    public String descripcion() {
        return "Tenista semiprofesional";
    }
}
