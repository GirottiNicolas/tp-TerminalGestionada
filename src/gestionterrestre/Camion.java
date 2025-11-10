package gestionterrestre;

public class Camion {
	String patente;
	String chofer;
	
	public Camion(String patente, String chofer) {
		this.patente = patente;
		this.setChofer(chofer);
	}

	public void setChofer(String chofer) {
		this.chofer = chofer;
	}
	
	public String getPatente() {
		return patente;
	}
	
	public boolean esCamion(Camion c) {
		return this.getPatente().equals(c.getPatente());
	}
}
