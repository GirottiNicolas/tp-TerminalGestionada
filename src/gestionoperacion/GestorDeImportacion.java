package gestionoperacion;

import java.time.LocalDateTime;

import gestionterrestre.Camion;
import gestionterrestre.Orden;
import gestionterrestre.OrdenDeImportacion;
import terminalgestionada.TerminalGestionada;

public class GestorDeImportacion extends GestorDeOperacion{

	public GestorDeImportacion(GestionTerrestre gestionTerrestre) {
		super(gestionTerrestre);
		
	}

	@Override
	protected boolean esUnaOrdenValida(Orden orden, TerminalGestionada terminal) {
		return terminal.esLaTerminal(orden.destino())  ;
		
	}

	@Override
	protected void errorDeTransaccion() {
		throw new RuntimeException("No puedes importar!");
		
	}

	@Override
	protected void procesarOrden(Orden orden) {
		// TODO Auto-generated method stub
	}
	
	public void retiroDeCarga(Orden orden, Camion camion) {
		this.verificarTransporte(camion, orden);
		gestionTerrestre.verificarHorarioDeRetiro(orden);
	}

}
