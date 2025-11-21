package filtros;

import logistica.Viaje;
import java.time.LocalDateTime;

public class FiltroFechaSalida implements FiltroBusqueda{

	private LocalDateTime fecha;

    public FiltroFechaSalida(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean cumple(Viaje viaje) {
        // Comparamos fechas exactas (o podés hacer > o < si se requiere)
        return viaje.getFechaPartida().isEqual(this.fecha.toLocalDate());
    }
	
}
