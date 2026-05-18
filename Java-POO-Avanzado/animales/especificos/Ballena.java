package animales.especificos;

import animales.capacidades.IDesplazamientoAcuatico;
import animales.familias.Mamifero;

public class Ballena extends Mamifero implements IDesplazamientoAcuatico {

	@Override
	public int getPeriodoLactancia() {
		return 24;
	}

	@Override
	public String getSonido() {
		return "Uuuuuuu"; 
	}

	@Override
	public String getDieta() {
		return "Pescado";
	}

	@Override
	public int getPromedioProfundidadNado() {
		return 3000;
	}

	@Override
	public int getPromedioVelocidadNado() {
		return 200;
	}


}
