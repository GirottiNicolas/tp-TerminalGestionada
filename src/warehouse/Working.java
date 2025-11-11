package warehouse;

import transporte.Ubicacion;

public class Working implements EstadoBuque{

	@Override
	public void actualizarPosicion(Buque buque, Ubicacion nuevaPosicion) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    public void iniciarTrabajo(Buque buque) {
        // Ya está trabajando, no hace nada.
    }

	@Override
	public void depart(Buque buque) {
		buque.setFase(new Departing());
	}
	
}
