package gestionterrestre;

import java.time.LocalDateTime;

import gestionterrestre.dummies.Viaje;
import terminalgestionada.TerminalGestionada;
import warehouse.Carga;


public abstract class Orden {
	Carga carga;
	Camion camion;
	Cliente cliente;
	LocalDateTime turno = null;
	Viaje viaje;
	
	public Orden(Viaje viaje,Carga carga,Camion camion, Cliente cliente) {
		this.carga = carga;
		this.camion = camion;
		this.cliente = cliente;
		this.turno = null;
		this.viaje = viaje;
		
	}

	public abstract boolean cumpleHorario(LocalDateTime now, int limiteDeTiempo);

	public Camion getCamion() {
		return camion;
	}
	
	public Cliente getCliente() {
		return cliente;
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
	
	public Viaje getViaje() {
		return viaje;
	}
	
	public TerminalGestionada origen() {
		return viaje.getOrigenViaje();
	}
	
	public TerminalGestionada destino() {
		return viaje.getDestinoViaje();
	}
	
}
