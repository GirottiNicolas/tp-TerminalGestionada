package filtros;

import logistica.Viaje;
import java.time.LocalDate;


public class FiltroFechaSalida implements FiltroBusqueda{

	private LocalDate fecha;

    public FiltroFechaSalida(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean cumple(Viaje viaje) {

        return viaje.getFechaPartida().isEqual(this.fecha);
    }
	
}
