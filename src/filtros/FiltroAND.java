package filtros;

import java.util.List;

import logistica.Viaje;

public class FiltroAND extends FiltroLogico{



	    public FiltroAND(List<FiltroBusqueda> filtros) {
	        this.filtros = filtros; 
	    }

	    @Override
	    public boolean cumple(Viaje viaje) {
	        // Cumple si TODOS cumplen
	        return filtros.stream().allMatch(filtro -> filtro.cumple(viaje));
	    }
	
}
