package filtros;

import logistica.Viaje;
import terminalgestionada.TerminalGestionada;

import java.time.LocalDate;

public class FiltroFechaLlegada implements FiltroBusqueda{

	private LocalDate fechaBuscada;
    private TerminalGestionada puertoDestino;

    public FiltroFechaLlegada(LocalDate fechaBuscada, TerminalGestionada puertoDestino) {
        this.fechaBuscada = fechaBuscada;
        this.puertoDestino = puertoDestino;
    }

    @Override
    public boolean cumple(Viaje viaje) {
        LocalDate fechaRealDeLlegada = viaje.getFechaLlegadaA(this.puertoDestino);
        if (fechaRealDeLlegada == null) {
            return false;
        }
        return fechaRealDeLlegada.isEqual(this.fechaBuscada);
    }
	
}
