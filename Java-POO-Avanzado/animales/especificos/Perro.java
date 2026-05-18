package animales.especificos;

import animales.capacidades.IDesplazamientoTerrestre;
import animales.capacidades.IEntrenable;
import animales.familias.Mamifero;

public class Perro extends Mamifero implements IDesplazamientoTerrestre, IEntrenable {

	@Override
	public String getHabilidades() {
		return "Guía personas invidentes";
	}
	@Override
	public int getPeriodoLactancia() {
		return 2;
	}

	@Override
	public String getSonido() {
		return "Guau";
	}

	@Override
	public String getDieta() {
		return "Carne";
	}

	@Override
	public int getPromedioVelocidadCorriendo() {
		return 50;
	}

	


}
