package filtros;

import java.util.List;

import logistica.Viaje;

public class FiltroOR implements FiltroBusqueda{

    List<FiltroBusqueda> filtros;

    public FiltroOR(List<FiltroBusqueda> filtros) {
        this.filtros = filtros; 
    }

    @Override
    public boolean cumple(Viaje viaje) {
        // Cumple si ALGUNO cumple
        return filtros.stream().anyMatch(filtro -> filtro.cumple(viaje));
    }
    
	
}
