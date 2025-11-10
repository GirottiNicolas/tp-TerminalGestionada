package warehouse;

import gestionterrestre.Ubicacion;
import terminalgestionada.TerminalGestionada;

public class Outbound implements EstadoBuque{
	
	@Override
    public void actualizarPosicion(Buque buque, Ubicacion nuevaPosicion) {
        
        TerminalGestionada terminal = buque.getTerminal();
        double distancia = nuevaPosicion.distanciaA(terminal.getPosicionGeografica());
        if (distancia < 50) {
            buque.setFase(new Inbound());
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
