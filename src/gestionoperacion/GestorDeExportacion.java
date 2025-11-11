package gestionoperacion;

import java.time.LocalDateTime;

import gestionterrestre.Camion;
import gestionterrestre.Orden;
import gestionterrestre.OrdenDeExportacion;
import terminalgestionada.TerminalGestionada;

public class GestorDeExportacion extends GestorDeOperacion{

	public GestorDeExportacion(GestionTerrestre gestionTerrestre) {
		super(gestionTerrestre);
	
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
		gestionTerrestre.verificarTurno(ordenExportacion, LocalDateTime.now());
		
		// warehouse.registrarCarga(camion.getCarga());
		
	}
	
	
	
}
