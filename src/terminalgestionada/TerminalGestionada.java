package terminalgestionada;

import gestionterrestre.GestionTerrestre;
import gestionterrestre.Ubicacion;
import logistica.Logistica;
import warehouse.Buque;
import warehouse.Warehouse;

public class TerminalGestionada implements FachadaTerminal{
	
	Ubicacion posicionGeografica;
	GestionTerrestre gestionTerrestre;
	Logistica logistica;
	Warehouse warehouse;
	
	public TerminalGestionada(Ubicacion posicionGeografica,GestionTerrestre gestionTerrestre, Logistica logistica, Warehouse warehouse) {
		this.logistica = logistica;
		this.warehouse = warehouse;
		this.gestionTerrestre = gestionTerrestre;
		this.posicionGeografica = posicionGeografica;
	}

	public boolean esLaTerminal(TerminalGestionada origenViaje) {
		return posicionGeografica.esLaUbicacion(origenViaje.getPosicionGeografica());
	}

	public Ubicacion getPosicionGeografica() {
		return posicionGeografica;
	}

	public void notificarArriboInminente(Buque buque) {
        // La terminal se encarga de la lógica de enviar emails a los clientes cuando el buque esta por llegar
    }
	
	public void notificarPartida(Buque buque) {
        // La terminal se encarga de la lógica de enviar emails a los shippers cuando sale un buque con carga de la terminal
    }
	
}
