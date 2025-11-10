package gestionterrestre;

import java.time.LocalDateTime;

import gestionterrestre.dummies.Carga;

public class OrdenDeImportacion extends Orden {
	
	LocalDateTime turno;
	
	
	public OrdenDeImportacion(Carga carga, Camion camion, LocalDateTime turno) {
		super(carga, camion);
		this.turno = turno;
	}


	@Override
	public boolean cumpleHorario(LocalDateTime now) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
