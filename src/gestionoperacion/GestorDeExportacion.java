package gestionoperacion;

import java.time.LocalDateTime;

import gestionterrestre.ordenes.Orden;
import gestionterrestre.ordenes.OrdenDeExportacion;
import terminalgestionada.TerminalGestionada;
import transporte.Camion;
import warehouse.Warehouse;

public class GestorDeExportacion extends GestorDeOperacion{

	public GestorDeExportacion(GestionComercioExterior gestorComercio, Warehouse warehouse) {
		super(gestorComercio,warehouse);
	
	}

	@Override
	protected void errorDeTransaccion() {
		throw new RuntimeException("No puedes exportar");
		
	}

	@Override
	protected void procesarOrden(Orden orden) {
		orden.asignarTurno(LocalDateTime.now());
		gestorComercio.agregarAExportaciones((OrdenDeExportacion) orden);
		
	}

	@Override
	protected boolean esUnaOrdenValida(Orden orden, TerminalGestionada terminal) {
		return terminal.esLaTerminal(orden.origen());
	}
	
	
	public void recibirCargaDeTransporte(OrdenDeExportacion ordenExportacion, Camion camion) {
		this.verificarTransporte(camion, ordenExportacion);
		this.verificarTurno(ordenExportacion, LocalDateTime.now());
		warehouse.registrarCarga(camion.getCarga());
	}
	
	protected void verificarTurno(OrdenDeExportacion orden, LocalDateTime horarioDelCamion) {
		if (!orden.cumpleHorario(LocalDateTime.now(), 3)) {
	        throw new RuntimeException("El camión no cumple con el horario permitido.");
	    }
	}
	
}
