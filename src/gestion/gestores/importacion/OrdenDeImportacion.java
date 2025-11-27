package gestion.gestores.importacion;

import java.time.LocalDateTime;

import gestion.gestores.ordenes.Orden;
import gestion.terrestre.Camion;
import gestion.terrestre.Cliente;
import logistica.Viaje;
import terminalgestionada.TerminalGestionada;

import warehouse.Carga;

public class OrdenDeImportacion extends Orden {
	
	LocalDateTime turno;
	
	LocalDateTime fechaDeRetiro;
	
	public OrdenDeImportacion(Viaje viaje,Carga carga, Camion camion, Cliente cliente,LocalDateTime turno) {
		super(viaje, carga, camion,cliente);
		this.turno = turno;
	}


	public TerminalGestionada getDestinoDeImportacion() {
		return viaje.getDestinoViaje();
	}
	
	
	
    public LocalDateTime getFechaRetiroEfectivo() {
        return fechaDeRetiro;
    }


	public void setFechaRetiroEfectivo(LocalDateTime fechaDeRetiro) {
		// Este metodo deberia ser protected, pero por cuestiones de testeo y simplicidad se mantuvo de esta manera.
		this.fechaDeRetiro = fechaDeRetiro;
		
	}
	
	
}
