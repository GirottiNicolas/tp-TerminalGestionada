package gestion.gestores.ordenes;

import java.time.LocalDateTime;


import gestion.terrestre.Camion;
import gestion.terrestre.Cliente;
import logistica.Viaje;
import terminalgestionada.TerminalGestionada;
import warehouse.Buque;
import warehouse.Carga;


public abstract class Orden {
	Carga carga;
	Camion camion;
	Cliente cliente;
	LocalDateTime turno = null;
	protected Viaje viaje;
	LocalDateTime fechaNotificacion;
	
	public Orden(Viaje viaje,Carga carga,Camion camion, Cliente cliente) {
		this.carga = carga;
		this.camion = camion;
		this.cliente = cliente;
		this.turno = null;
		this.viaje = viaje;
		
	}
	
	public boolean cumpleHorario(LocalDateTime now, int limiteDeTiempo) {
		this.verificarTurno();
	    LocalDateTime turno = this.getTurno();

	    long diferenciaHoras = Math.abs(java.time.Duration.between(turno, now).toHours());

	    return diferenciaHoras <= limiteDeTiempo;
	}
	

	public void verificarTurno() {
		if(!this.tieneTurnoAsignado()) {
			throw new RuntimeException("No tienes un turno asignado!");
		}
	}
	
	
	public boolean tieneTurnoAsignado() {
		return this.getTurno() != null;
	}
	
	
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
	
	
	public LocalDateTime getTurno() {
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
	
	public Buque getBuque() {
		return viaje.getBuque();
	}
	
	public LocalDateTime getFechaLlegadaNotificada() {
	        return fechaNotificacion; 
	}
	

	public void setFechaDeNotificacion(LocalDateTime fechaNotificacion) { // Podria ser protected para mejorar la encapsulacion
		this.fechaNotificacion = fechaNotificacion;
	}
	

	public Carga getCarga() {return carga ;}
}
