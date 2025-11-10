package warehouse;

import gestionterrestre.Ubicacion;

public class Arrived implements EstadoBuque{

	@Override
	public void actualizarPosicion(Buque buque, Ubicacion nuevaPosicion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void iniciarTrabajo(Buque buque) {
		buque.setFase(new Working());
	}

	@Override
	public void depart(Buque buque) {
		// TODO Auto-generated method stub
		
	}
	
}
