package gestion.terrestre.dummies;


import terminalgestionada.TerminalGestionada;
import warehouse.Buque;

public class Viaje {
	TerminalGestionada origen;
	TerminalGestionada destino;
	Buque buque;
	
	
	public Viaje(TerminalGestionada origen, TerminalGestionada destino, Buque buque) {
		this.origen = origen;
		this.destino = destino;
		this.buque = buque;
	}
	
	
	public TerminalGestionada getOrigenViaje() {
		return origen;
	}
	
	public TerminalGestionada getDestinoViaje() {
		return destino;
	}


	public Buque getBuque() {
		return buque;
	}
	
}
