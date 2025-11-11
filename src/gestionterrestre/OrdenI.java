package gestionterrestre;

import warehouse.Carga;

public interface OrdenI {
	public Carga getCarga() ;
	public Camion getCamion();
	public Cliente getCliente();
	public boolean esOrden(Orden orden) ;
}
