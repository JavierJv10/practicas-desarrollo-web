package Ejercicios_a_b_c;

public class InformeVentas_a {

    public void imprimirInforme(String producto, int unidades, double precioUnidad, double descuento) {
        imprimirCabecera(producto, unidades, precioUnidad);
        double total = calcularTotal(unidades, precioUnidad, descuento);
        imprimirResultado(total, descuento);
        imprimirPie();
    }

    private void imprimirCabecera(String producto, int unidades, double precioUnidad) {
        System.out.println("=== INFORME DE VENTA ===");
        System.out.println("Producto: " + producto);
        System.out.println("Unidades: " + unidades);
        System.out.println("Precio unidad: " + precioUnidad);
    }

    private double calcularTotal(int unidades, double precioUnidad, double descuento) {
        double importeBruto = unidades * precioUnidad;
        if (descuento > 0) {
            double importeConDescuento = importeBruto - (importeBruto * descuento);
            return importeConDescuento;
        }
        return importeBruto;
    }

    private void imprimirResultado(double total, double descuento) {
        if (descuento > 0) {
            double porcentajeDescuento = descuento * 100;
            System.out.println("Descuento aplicado: " + porcentajeDescuento + "%");
        }
        System.out.println("TOTAL A PAGAR: " + total);
    }

    private void imprimirPie() {
        System.out.println("Gracias por su compra.");
    }
}