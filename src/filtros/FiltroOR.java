package filtros;

import logistica.Viaje;

public class FiltroOR implements FiltroBusqueda{

	private FiltroBusqueda filtro1;
    private FiltroBusqueda filtro2;

    public FiltroOR(FiltroBusqueda f1, FiltroBusqueda f2) {
        this.filtro1 = f1;
        this.filtro2 = f2;
    }

    @Override
    public boolean cumple(Viaje viaje) {
        // Cumple si ALGUNO cumple
        return filtro1.cumple(viaje) || filtro2.cumple(viaje);
    }
	
}
