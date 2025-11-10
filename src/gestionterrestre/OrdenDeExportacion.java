package gestionterrestre;

import java.util.Date;

import gestionterrestre.dummies.Carga;
import gestionterrestre.dummies.Viaje;
import terminalgestionada.TerminalGestionada;

public class OrdenDeExportacion {
	
	Viaje viaje;
	Carga carga;
	Camion camion;
	
	
	public OrdenDeExportacion(Viaje viaje, Carga carga, Camion camion) {
		this.viaje = viaje;
		this.carga = carga;
		this.camion = camion;
	}
	
	public boolean parteDeLaTerminal(TerminalGestionada terminalGestionada) {
		return terminalGestionada.esLaTerminal(viaje.getOrigenViaje());
	}

	public void asignarTurno(Date date) {
		// TODO Auto-generated method stub
		
	}

}
