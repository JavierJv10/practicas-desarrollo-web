package Ejercicios_a_b_c;

public class CalculadoraDescuentos_b {

    public double aplicarDescuento(double precioBase, double porcentaje) {
        double precioConDescuento = precioBase * (1 - (porcentaje / 100));
        double precioRedondeado = Math.round(precioConDescuento * 100) / 100.0;
        return precioRedondeado;
    }
}