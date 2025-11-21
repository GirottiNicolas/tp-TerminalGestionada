package filtros;

import logistica.Viaje;

public class FiltroAND implements FiltroBusqueda{

	private FiltroBusqueda filtro1;
    private FiltroBusqueda filtro2;

    public FiltroAND(FiltroBusqueda f1, FiltroBusqueda f2) {
        this.filtro1 = f1;
        this.filtro2 = f2;
    }

    @Override
    public boolean cumple(Viaje viaje) {
        // Cumple si AMBOS cumplen
        return filtro1.cumple(viaje) && filtro2.cumple(viaje);
    }
	
}
