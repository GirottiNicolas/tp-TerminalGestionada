package gestionterrestre;



import java.time.LocalDateTime;
import gestionterrestre.dummies.Viaje;
import terminalgestionada.TerminalGestionada;
import warehouse.Carga;



public class OrdenDeExportacion extends Orden {
	
	// Las ordenes de exportaciones poseen un LocalDateTime unico.
	// que las identifica de forma univoca
	
	Viaje viaje;
	
	
	public OrdenDeExportacion(Viaje viaje, Carga carga, Camion camion) {
		super(carga, camion);
		this.viaje = viaje;
	}
	
	
	@Override
	public boolean cumpleHorario(LocalDateTime now) {
	    LocalDateTime turno = this.getTurno();

	    long diferenciaHoras = Math.abs(java.time.Duration.between(turno, now).toHours());

	    return diferenciaHoras <= 3;
	}
	
	
	public boolean parteDeLaTerminal(TerminalGestionada terminalGestionada) {
		return terminalGestionada.esLaTerminal(viaje.getOrigenViaje());
	}
	



}
