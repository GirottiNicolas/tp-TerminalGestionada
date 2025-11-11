package gestionoperacion;

import java.time.LocalDateTime;

import gestionterrestre.Camion;
import gestionterrestre.Orden;
import gestionterrestre.OrdenDeImportacion;
import terminalgestionada.TerminalGestionada;
import warehouse.Warehouse;

public class GestorDeImportacion extends GestorDeOperacion{

	public GestorDeImportacion(GestionTerrestre gestionTerrestre, Warehouse warehouse) {
		super(gestionTerrestre, warehouse);
		
	}

	@Override
	protected boolean esUnaOrdenValida(Orden orden, TerminalGestionada terminal) {
		// Verifica que la importacion tenga como destino a la terminal
		return terminal.esLaTerminal(orden.destino())  ;
		
	}

	@Override
	protected void errorDeTransaccion() {
		throw new RuntimeException("No puedes importar!");
		
	}

	@Override
	protected void procesarOrden(Orden orden) {
		// El turno de la orden se setea al recibir la notificacion del buque
		gestionTerrestre.agregarAImportaciones((OrdenDeImportacion) orden);
	}
	
	public void retiroDeCarga(Orden orden, Camion camion) {
		this.verificarTransporte(camion, orden);
		gestionTerrestre.verificarHorarioDeRetiro(orden);
	}

}
