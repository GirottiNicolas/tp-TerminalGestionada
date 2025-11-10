package gestionterrestre;



import java.time.LocalDateTime;
import gestionterrestre.dummies.Carga;
import gestionterrestre.dummies.Viaje;
import terminalgestionada.TerminalGestionada;



public class OrdenDeExportacion {
	
	// Las ordenes de exportaciones poseen un LocalDateTime unico.
	// que las identifica de forma univoca
	
	Viaje viaje;
	Carga carga;
	Camion camion;
	LocalDateTime turno = null;
	
	
	public OrdenDeExportacion(Viaje viaje, Carga carga, Camion camion) {
		this.viaje = viaje;
		this.carga = carga;
		this.camion = camion;
	}
	
	public boolean parteDeLaTerminal(TerminalGestionada terminalGestionada) {
		return terminalGestionada.esLaTerminal(viaje.getOrigenViaje());
	}

	public void asignarTurno(LocalDateTime date) {
		turno = date;
	}
	
	public LocalDateTime getTurno() {
		return turno;
	}
	
	public boolean esOrden(OrdenDeExportacion orden) {
		// Dos ordenes pueden ser iguales unicamente si su fecha fue seteada 
		//por la GestionTerrestre, caso contrario dara false.
		
		return turno != null && turno.equals(orden.getTurno());
	}

	public Camion getCamion() {
		return camion;
	}

	public boolean cumpleHorario(LocalDateTime now) {
	    LocalDateTime turno = this.getTurno();

	    long diferenciaHoras = Math.abs(java.time.Duration.between(turno, now).toHours());

	    return diferenciaHoras <= 3;
	}

}
