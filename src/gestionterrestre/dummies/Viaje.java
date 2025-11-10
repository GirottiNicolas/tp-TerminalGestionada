package gestionterrestre.dummies;

import terminalgestionada.TerminalGestionada;

public class Viaje {
	TerminalGestionada origen;
	
	
	public Viaje(TerminalGestionada origen) {
		this.origen = origen;
	}
	
	
	public TerminalGestionada getOrigenViaje() {
		return origen;
	}
	
}
