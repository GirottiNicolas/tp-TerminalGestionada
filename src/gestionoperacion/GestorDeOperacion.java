package gestionoperacion;

import gestionterrestre.Camion;
import gestionterrestre.Orden;
import terminalgestionada.TerminalGestionada;
import warehouse.Warehouse;

public abstract class GestorDeOperacion {
	
	GestionTerrestre gestionTerrestre;
	Warehouse warehouse;
	
	public GestorDeOperacion(GestionTerrestre gestionTerrestre, Warehouse warehouse) {
		this.gestionTerrestre = gestionTerrestre;
		this.warehouse = warehouse;
	}
	
	public final void ejecutarOperacion(Orden orden, TerminalGestionada terminal) {
		if(!this.puedeRealizarOperacion(orden, terminal)) {
			this.errorDeTransaccion();
		}
		this.procesarOrden(orden);
	}
	
	
	
	protected boolean puedeRealizarOperacion(Orden orden, TerminalGestionada terminal) {
		return gestionTerrestre.esCliente(orden.getCliente()) && this.esUnaOrdenValida(orden,terminal);
	}
	
	protected void verificacionAdicional(Orden orden) {}

	protected abstract boolean esUnaOrdenValida(Orden orden, TerminalGestionada terminal);

	protected abstract void errorDeTransaccion();

	protected abstract void procesarOrden(Orden orden);

	protected void verificarTransporte(Camion camion, Orden orden) {
	    if (!camion.esCamionDesignado(orden.getCamion())) {
	        throw new RuntimeException("El camión no es el designado para esta orden de exportación.");
	    }
	}
	
	
}
