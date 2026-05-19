package Ejercicio_e;

public class TenistaProfesional extends Tenista {

    @Override
    public double calcularCuotaMensual() {
        return 50.0;
    }

    @Override
    public String descripcion() {
        return "Tenista profesional";
    }
}
