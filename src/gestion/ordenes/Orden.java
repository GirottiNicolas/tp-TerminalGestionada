package gestion.ordenes;

import java.time.LocalDateTime;

import gestion.interfaces.OrdenDeComercio;
import gestion.terrestre.Camion;
import gestion.terrestre.Cliente;
import gestion.terrestre.dummies.Viaje;
import terminalgestionada.TerminalGestionada;
import warehouse.Buque;
import warehouse.Carga;


public abstract class Orden implements OrdenDeComercio {
	Carga carga;
	Camion camion;
	Cliente cliente;
	LocalDateTime turno = null;
	Viaje viaje;
	LocalDateTime fechaNotificacion;
	
	public Orden(Viaje viaje,Carga carga,Camion camion, Cliente cliente) {
		this.carga = carga;
		this.camion = camion;
		this.cliente = cliente;
		this.turno = null;
		this.viaje = viaje;
		
	}
	
	@Override
	public boolean cumpleHorario(LocalDateTime now, int limiteDeTiempo) {
		this.verificarTurno();
	    LocalDateTime turno = this.getTurno();

	    long diferenciaHoras = Math.abs(java.time.Duration.between(turno, now).toHours());

	    return diferenciaHoras <= limiteDeTiempo;
	}
	
	@Override
	public void verificarTurno() {
		if(!this.tieneTurnoAsignado()) {
			throw new RuntimeException("No tienes un turno asignado!");
		}
	}
	
	@Override
	public boolean tieneTurnoAsignado() {
		return this.getTurno() != null;
	}
	
	@Override
	public Camion getCamion() {
		return camion;
	}
	
	@Override
	public Cliente getCliente() {
		return cliente;
	}
	
	@Override
	public boolean esOrden(Orden orden) {
		// Dos ordenes pueden ser iguales unicamente si su fecha fue seteada 
		//por la GestionTerrestre, caso contrario dara false.
		
		return turno != null && turno.equals(orden.getTurno());
	}
	
	@Override
	public LocalDateTime getTurno() {
		return turno;
	}
	
	@Override
	public void asignarTurno(LocalDateTime date) {
		turno = date;
	}
	
	@Override
	public Viaje getViaje() {
		return viaje;
	}
	
	@Override
	public TerminalGestionada origen() {
		return viaje.getOrigenViaje();
	}
	
	@Override
	public TerminalGestionada destino() {
		return viaje.getDestinoViaje();
	}
	
	public Buque getBuqueDeViaje() {
		return viaje.getBuque();
	}
	
	// Agregado por nico
	public LocalDateTime getFechaLlegadaNotificada() {
	        return fechaNotificacion; 
	}
	
	@Override
	public void setFechaDeNotificacion(LocalDateTime fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}
	
	@Override
	public Carga getCarga() {return carga ;}
}
