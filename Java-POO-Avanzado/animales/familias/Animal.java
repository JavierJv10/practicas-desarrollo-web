package animales.familias;

public abstract class Animal {

	private int codigoIdentificativo;
	private String nombre;

	public int getCodigoIdentificativo() {
		return codigoIdentificativo;
	}

	public void setCodigoIdentificativo(int codigoIdentificativo) {
		this.codigoIdentificativo = codigoIdentificativo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public abstract String getSonido();
	
	
	public abstract String getDieta();
	
	
	
//	public abstract int getPromedioAlturaVuelo();
//	
//	public abstract int getPromedioVelocidadVuelo();
//
//	public abstract int getPromedioProfundidadNado();
//
//	public abstract int getPromedioVelocidadNado();
//	
//	public abstract int getPromedioVelocidadCorriendo();

	
	
	
	

}
