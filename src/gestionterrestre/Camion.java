package gestionterrestre;

import gestionterrestre.dummies.CargaTemporal;

public class Camion {
	String patente;
	String chofer;
	CargaTemporal carga;
	
	public Camion(String patente, String chofer, CargaTemporal carga) {
		this.patente = patente;
		this.setChofer(chofer);
		this.setCargaEnCamion(carga);
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

	private String getChofer() {
		return chofer;
	}

	public CargaTemporal getCarga() {
		return carga;
	}
	
	public void setCargaEnCamion(CargaTemporal carga) {
		this.carga = carga;
	}
}
