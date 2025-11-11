package warehouse;

import transporte.Ubicacion;

public interface EstadoBuque {
	public void actualizarPosicion(Buque buque, Ubicacion nuevaPosicion);
	
	void iniciarTrabajo(Buque buque);
	
	void depart(Buque buque);
}
