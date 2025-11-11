package gestionoperacion;

import java.time.LocalDateTime;

import gestionterrestre.ordenes.Orden;
import gestionterrestre.ordenes.OrdenDeImportacion;
import terminalgestionada.TerminalGestionada;
import transporte.Camion;
import warehouse.ServicioAlmacenamiento;
import warehouse.Warehouse;

public class GestorDeImportacion extends GestorDeOperacion{

	public GestorDeImportacion(GestionComercioExterior gestorComercio, Warehouse warehouse) {
		super(gestorComercio, warehouse);
		
	}

	@Override
	protected boolean esUnaOrdenValida(Orden orden, TerminalGestionada terminal) {
		// Verifica que la importacion tenga como destino a la terminal
		return terminal.esLaTerminal(orden.destino());
		
	}

	@Override
	protected void errorDeTransaccion() {
		throw new RuntimeException("No puedes importar!");
		
	}

	@Override
	protected void procesarOrden(Orden orden) {
		// El turno de la orden se setea al recibir la notificacion del buque
		gestorComercio.agregarAImportaciones((OrdenDeImportacion) orden);
	}
	
	public void retiroDeCarga(OrdenDeImportacion orden, Camion camion) {
		this.verificarTransporte(camion, orden);
		this.verificarHorarioDeRetiro(orden);
		orden.setFechaRetiroEfectivo(LocalDateTime.now());
		camion.asignarCarga(orden.getCarga());
		warehouse.retirarCarga(orden.getCarga());
	}
	
	
	private void verificarHorarioDeRetiro(Orden orden) {
		if(!orden.cumpleHorario(LocalDateTime.now(), 24)) {
			warehouse.aplicarServicio(new ServicioAlmacenamiento((OrdenDeImportacion) orden, 300.0), orden.getCarga());
		}
	}

}
