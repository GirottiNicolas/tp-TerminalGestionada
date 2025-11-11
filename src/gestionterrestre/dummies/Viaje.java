package gestionterrestre.dummies;

import terminalgestionada.TerminalGestionada;

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
	
}
