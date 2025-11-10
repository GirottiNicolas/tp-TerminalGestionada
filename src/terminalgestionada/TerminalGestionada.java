package terminalgestionada;

import gestionterrestre.GestionTerrestre;
import gestionterrestre.Ubicacion;
import logistica.Logistica;
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

	
}
