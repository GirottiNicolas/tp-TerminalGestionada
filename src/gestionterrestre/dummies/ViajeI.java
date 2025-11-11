package gestionterrestre.dummies;

import terminalgestionada.TerminalGestionada;
import warehouse.Buque;

public interface ViajeI {
	public Viaje getViaje();
	
	public TerminalGestionada origen() ;
	
	public TerminalGestionada destino();
	
	public Buque getBuqueDeViaje() ;
}
