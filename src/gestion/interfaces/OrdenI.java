package gestion.interfaces;

import gestion.terrestre.Camion;
import gestion.terrestre.Cliente;
import gestion.terrestre.Orden;
import warehouse.Carga;

public interface OrdenI {
	public Carga getCarga() ;
	public Camion getCamion();
	public Cliente getCliente();
	public boolean esOrden(Orden orden) ;
}
