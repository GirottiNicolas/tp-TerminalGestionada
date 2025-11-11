package warehouse;

import gestionterrestre.Ubicacion;

public class Inbound implements EstadoBuque{
	
	@Override
    public void actualizarPosicion(Buque buque, Ubicacion nuevaPosicion) {
		
		var terminal = buque.getTerminal();
        double distancia = nuevaPosicion.distanciaA(terminal.getPosicionGeografica(),1.0);
        if (distancia == 0) {
            buque.setFase(new Arrived());
        } else if (distancia >= 50) {
        	buque.setFase(new Outbound());
         	}
    }

	@Override
	public void iniciarTrabajo(Buque buque) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void depart(Buque buque) {
		// TODO Auto-generated method stub
		
	}
	
	
}
