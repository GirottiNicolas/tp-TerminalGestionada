package gestionterrestre;

import gestionterrestre.dummies.Viaje;
import terminalgestionada.TerminalGestionada;
import warehouse.Carga;



public class OrdenDeExportacion extends Orden {
	
	// Las ordenes de exportaciones poseen un LocalDateTime unico.
	// que las identifica de forma univoca
	
	public OrdenDeExportacion(Viaje viaje, Carga carga, Camion camion, Cliente cliente) {
		super(viaje,carga, camion, cliente);
		
	}
		
	public boolean parteDeLaTerminal(TerminalGestionada terminalGestionada) {
		return terminalGestionada.esLaTerminal(viaje.getOrigenViaje());
	}
	
}
