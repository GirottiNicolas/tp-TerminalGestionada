package gestion.gestores;

import java.time.LocalDateTime;

import gestion.ordenes.Orden;
import gestion.ordenes.OrdenDeExportacion;
import gestion.terrestre.Camion;
import terminalgestionada.TerminalGestionada;
import warehouse.Warehouse;

public class GestorDeExportacion extends GestorDeOperacion{

	public GestorDeExportacion(GestionTerrestre gestionTerrestre, Warehouse warehouse) {
		super(gestionTerrestre,warehouse);
	
	}

	@Override
	protected void errorDeTransaccion() {
		throw new RuntimeException("No puedes exportar");
		
	}

	@Override
	protected void procesarOrden(Orden orden) {
		orden.asignarTurno(LocalDateTime.now());
		gestionTerrestre.agregarAExportaciones((OrdenDeExportacion) orden);
		
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
