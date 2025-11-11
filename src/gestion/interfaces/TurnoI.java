package gestion.interfaces;

import java.time.LocalDateTime;

	public interface TurnoI {
		
		public boolean cumpleHorario(LocalDateTime now, int limiteDeTiempo);
		
		public void verificarTurno() ;
		
		public boolean tieneTurnoAsignado();
		
		public void asignarTurno(LocalDateTime date) ;
		
		public LocalDateTime getTurno();
}
