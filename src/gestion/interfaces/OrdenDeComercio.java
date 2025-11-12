package gestion.interfaces;

import java.time.LocalDateTime;



public interface OrdenDeComercio extends OrdenI,TurnoI {
	public void setFechaDeNotificacion(LocalDateTime fechaNotificacion);
}
