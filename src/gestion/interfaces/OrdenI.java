package gestion.interfaces;

import gestion.ordenes.Orden;
import gestion.terrestre.Camion;
import gestion.terrestre.Cliente;
import warehouse.Carga;

public interface OrdenI {
	public Carga getCarga() ;
	public Camion getCamion();
	public Cliente getCliente();
	public boolean esOrden(Orden orden) ;
}
