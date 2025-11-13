package gestion.terrestre;

import warehouse.Carga;

public class Camion {
	String patente;
	String chofer;
	Carga carga;
	
	public Camion(String patente, String chofer, Carga carga) {
		this.patente = patente;
		this.setChofer(chofer);
		this.asignarCarga(carga);
	}

	public void setChofer(String chofer) {
		this.chofer = chofer;
	}
	
	public String getPatente() {
		return patente;
	}
	
	public boolean esCamionDesignado(Camion c) {
		return this.getPatente().equals(c.getPatente()) && this.getChofer().equals(c.getChofer());
	}

	public String getChofer() {
		return chofer;
	}

	public Carga getCarga() {
		return carga;
	}
	

	
	public void asignarCarga(Carga carga) {
		this.carga = carga;
	}
		
}
