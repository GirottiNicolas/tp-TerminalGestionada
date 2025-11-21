package filtros;

import logistica.Viaje;
import terminalgestionada.TerminalGestionada;

public class FiltroPuertoDestino implements FiltroBusqueda{

	private TerminalGestionada puertoDestino;

    public FiltroPuertoDestino(TerminalGestionada puertoDestino) {
        this.puertoDestino = puertoDestino;
    }

    @Override
    public boolean cumple(Viaje viaje) {
        return viaje.getCircuito().getTerminalDestino().equals(this.puertoDestino);
    }
	
}
