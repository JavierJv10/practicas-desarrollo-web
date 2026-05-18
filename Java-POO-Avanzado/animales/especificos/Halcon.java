package animales.especificos;

import animales.capacidades.IDesplazamientoAereo;
import animales.capacidades.IEntrenable;
import animales.familias.Ave;

public class Halcon extends Ave implements IDesplazamientoAereo, IEntrenable {

	@Override
	public String getHabilidades() {
		return "buscar presas";
	}

	@Override
	public int getPeriodoIncubacion() {
		return 1;
	}
	
	@Override
	public String getSonido() {
		return "Sonido del halcón";
	}

	@Override
	public String getDieta() {
		return "Carne";
	}

	@Override
	public int getPromedioAlturaVuelo() {
		return 2500;
	}

	@Override
	public int getPromedioVelocidadVuelo() {
		return 70;
	}

	
	

}
