package gestionterrestre;

import java.time.LocalDateTime;

import warehouse.Carga;


public abstract class Orden {
	Carga carga;
	Camion camion;
	LocalDateTime turno = null;
	
	public Orden(Carga carga,Camion camion) {
		this.carga = carga;
		this.camion = camion;
		this.turno = null;
	}

	public abstract boolean cumpleHorario(LocalDateTime now);

	public Camion getCamion() {
		return camion;
	}
	
	public boolean esOrden(Orden orden) {
		// Dos ordenes pueden ser iguales unicamente si su fecha fue seteada 
		//por la GestionTerrestre, caso contrario dara false.
		
		return turno != null && turno.equals(orden.getTurno());
	}

	protected LocalDateTime getTurno() {
		return turno;
	}
	
	public void asignarTurno(LocalDateTime date) {
		turno = date;
	}
	
}
