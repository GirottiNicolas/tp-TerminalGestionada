package gestionterrestre;

import java.time.LocalDateTime;

import gestionterrestre.dummies.Viaje;
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
	
	// Agregado por nico
	public LocalDateTime getFechaLlegadaNotificada() {
        return null; 
    }

	
    public LocalDateTime getFechaRetiroEfectivo() {
        return fechaDeRetiro;
    }


	public void setFechaRetiroEfectivo(LocalDateTime fechaDeRetiro) {
		this.fechaDeRetiro = fechaDeRetiro;
		
	}

	
	
}
