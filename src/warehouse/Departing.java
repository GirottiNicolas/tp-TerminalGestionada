package warehouse;

import gestion.terrestre.Ubicacion;

public class Departing implements EstadoBuque{

	@Override
    public void actualizarPosicion(Buque buque, Ubicacion nuevaPosicion) {
        var terminal = buque.getTerminal();
        double distancia = nuevaPosicion.distanciaA(terminal.getPosicionGeografica(),1.0);

        if (distancia > 1) {
            buque.setFase(new Outbound());
            terminal.notificarPartida(buque);
        }
    }

	@Override
	public void iniciarTrabajo(Buque buque) {
		// TODO Auto-generated method stub
		
	}

	@Override
    public void depart(Buque buque) {
        // Ya está saliendo, no hace nada
    }
	
	// h
		// h
		
		// h
		// h
		// h
		// h
		// h
		// h
		// h
		// h
		// h
		// h
		// h
		// h
		// h
		// h
		// h
		// h
		// h
		// h
		// h
	
	
}
