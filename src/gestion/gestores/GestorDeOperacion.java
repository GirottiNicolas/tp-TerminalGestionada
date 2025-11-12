package gestion.gestores;

import gestion.ordenes.Orden;
import gestion.terrestre.Camion;
import terminalgestionada.TerminalGestionada;
import warehouse.Warehouse;

public abstract class GestorDeOperacion {
	
	GestionTerrestre gestionTerrestre;
	Warehouse warehouse;
	
	public GestorDeOperacion(GestionTerrestre gestionTerrestre, Warehouse warehouse) {
		this.gestionTerrestre = gestionTerrestre;
		this.warehouse = warehouse;
	}
	
	
	// Metodo plantilla
	public final void ejecutarOperacion(Orden orden, TerminalGestionada terminal) {
		if(!this.puedeRealizarOperacion(orden, terminal)) {
			this.errorDeTransaccion();
		}
		this.procesarOrden(orden);
	}
	
	protected void verificarTransporte(Camion camion, Orden orden) {
	    if (!camion.esCamionDesignado(orden.getCamion())) {
	        throw new RuntimeException("El camión no es el designado para esta orden de exportación.");
	    }
	}
	
	protected boolean puedeRealizarOperacion(Orden orden, TerminalGestionada terminal) {
		return gestionTerrestre.esCliente(orden.getCliente()) && this.esUnaOrdenValida(orden,terminal);
	}
	
	// Operaciones primitivas
	
	protected void verificacionAdicional(Orden orden) {} // Posible hook, eliminar si no es necesario

	public abstract boolean esUnaOrdenValida(Orden orden, TerminalGestionada terminal);

	public abstract void errorDeTransaccion();

	public abstract void procesarOrden(Orden orden);

	
	
	
}
