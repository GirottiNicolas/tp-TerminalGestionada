package gestion.gestores.exportacion;

import java.time.LocalDateTime;

import gestion.gestores.GestionTerrestre;
import gestion.gestores.GestorDeOperacion;
import gestion.gestores.ordenes.Orden;
import gestion.terrestre.Camion;
import terminalgestionada.TerminalGestionada;
import warehouse.Warehouse;

public class GestorDeExportacion extends GestorDeOperacion{

	public GestorDeExportacion(GestionTerrestre gestionTerrestre, Warehouse warehouse) {
		super(gestionTerrestre,warehouse);
	
	}

	@Override
	public void errorDeTransaccion() {
		throw new RuntimeException("No puedes exportar");
		
	}

	@Override
	public void procesarOrden(Orden orden) {
		orden.asignarTurno(LocalDateTime.now());
		gestionTerrestre.agregarAExportaciones((OrdenDeExportacion) orden);
		
	}

	@Override
	public boolean esUnaOrdenValida(Orden orden, TerminalGestionada terminal) {
		return terminal.esLaTerminal(orden.origen());
	}
	
	
	public void recibirCargaDeTransporte(OrdenDeExportacion ordenExportacion, Camion camion) {
		this.verificarTransporte(camion, ordenExportacion);
		this.verificarTurno(ordenExportacion, LocalDateTime.now());
		warehouse.registrarCarga(camion.getCarga());
	}
	
	public void verificarTurno(OrdenDeExportacion orden, LocalDateTime horarioDelCamion) {
		if (!orden.cumpleHorario(horarioDelCamion, 3)) {
	        throw new RuntimeException("El camión no cumple con el horario permitido.");
	    }
	}
	
}
