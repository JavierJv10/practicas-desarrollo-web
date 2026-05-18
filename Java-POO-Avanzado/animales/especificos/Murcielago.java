package animales.especificos;

import animales.capacidades.IDesplazamientoAereo;
import animales.familias.Mamifero;

public class Murcielago extends Mamifero implements IDesplazamientoAereo {

	@Override
	public int getPeriodoLactancia() {
		return 1;
	}

	@Override
	public String getSonido() {
		return "Tii-tii-tiii";
	}

	@Override
	public String getDieta() {
		return "Insectos-Carne";
	}

	@Override
	public int getPromedioAlturaVuelo() {
		return 800;
	}

	@Override
	public int getPromedioVelocidadVuelo() {
		return 80;
	}

}
