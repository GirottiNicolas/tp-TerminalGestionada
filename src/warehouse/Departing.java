package warehouse;

import transporte.Ubicacion;

public class Departing implements EstadoBuque{

	@Override
    public void actualizarPosicion(Buque buque, Ubicacion nuevaPosicion) {
        var terminal = buque.getTerminal();
        double distancia = nuevaPosicion.distanciaA(terminal.getPosicionGeografica());

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
	
}
