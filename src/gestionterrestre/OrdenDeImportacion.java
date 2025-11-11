package gestionterrestre;

import java.time.LocalDateTime;

import gestionterrestre.dummies.Viaje;
import terminalgestionada.TerminalGestionada;
import warehouse.Carga;

public class OrdenDeImportacion extends Orden {
	
	LocalDateTime turno;
	
	
	public OrdenDeImportacion(Viaje viaje,Carga carga, Camion camion, Cliente cliente,LocalDateTime turno) {
		super(viaje, carga, camion,cliente);
		this.turno = turno;
	}


	@Override
	public boolean cumpleHorario(LocalDateTime now, int limiteDeTiempo) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public TerminalGestionada getDestinoDeImportacion() {
		return viaje.getDestinoViaje();
	}
	
	
}
