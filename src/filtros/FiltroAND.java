package filtros;

import java.util.List;

import logistica.Viaje;

public class FiltroAND implements FiltroBusqueda{

	 List<FiltroBusqueda> filtros;

	    public FiltroAND(List<FiltroBusqueda> filtros) {
	        this.filtros = filtros; 
	    }

	    @Override
	    public boolean cumple(Viaje viaje) {
	        // Cumple si ALGUNO cumple
	        return filtros.stream().allMatch(filtro -> filtro.cumple(viaje));
	    }
	// (cumple1 && cumple2) || cumple3
}
