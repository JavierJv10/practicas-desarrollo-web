package aplicacion;

import animales.capacidades.IDesplazamientoAereo;

public class ControladorAereo {
    private IDesplazamientoAereo animal;  // ¡Interfaz, no clase concreta!
    
    // La dependencia se INYECTA desde fuera (constructor)
    public ControladorAereo(IDesplazamientoAereo animal) {
        this.animal = animal;
    }
    
    public void mostrarInfoVuelo() {
        System.out.println("Altura: " + animal.getPromedioAlturaVuelo());
        System.out.println("Velocidad: " + animal.getPromedioVelocidadVuelo());
    }
}