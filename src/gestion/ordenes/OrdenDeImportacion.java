package gestion.ordenes;

import java.time.LocalDateTime;

import gestion.terrestre.Camion;
import gestion.terrestre.Cliente;
import logistica.Viaje;
import terminalgestionada.TerminalGestionada;
import warehouse.Buque;
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
		this.fechaDeRetiro = fechaDeRetiro;
		
	}
	
	
}
