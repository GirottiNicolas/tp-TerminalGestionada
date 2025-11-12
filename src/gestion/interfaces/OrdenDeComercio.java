package gestion.interfaces;

import java.time.LocalDateTime;

import gestion.terrestre.dummies.ViajeI;

public interface OrdenDeComercio extends OrdenI,TurnoI, ViajeI {
	public void setFechaDeNotificacion(LocalDateTime fechaNotificacion);
}
