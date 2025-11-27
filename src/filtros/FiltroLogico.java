package filtros;


import java.util.List;

public abstract class FiltroLogico implements FiltroBusqueda {
	List<FiltroBusqueda> filtros;
	
	public void añadirFiltro(FiltroBusqueda filtro) {
		filtros.add(filtro);
	}
	
	public void eliminarFiltro(FiltroBusqueda filtro) {
		 filtros.removeIf(f -> f.equals(filtro));
	}
	
	
	
}
