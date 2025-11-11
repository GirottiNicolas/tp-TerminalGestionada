package gestionterrestre.dummies;


import terminalgestionada.TerminalGestionada;
import warehouse.Buque;

public class Viaje {
	TerminalGestionada origen;
	TerminalGestionada destino;
	
	
	public Viaje(TerminalGestionada origen, TerminalGestionada destino) {
		this.origen = origen;
		this.destino = destino;
	}
	
	
	public TerminalGestionada getOrigenViaje() {
		return origen;
	}
	
	public TerminalGestionada getDestinoViaje() {
		return destino;
	}


	public Buque getBuque() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
