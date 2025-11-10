package terminalgestionada;

import gestionterrestre.GestionTerrestre;
import logistica.Logistica;
import warehouse.Warehouse;

public class TerminalGestionada implements FachadaTerminal{
	GestionTerrestre gestionTerrestre;
	Logistica logistica;
	Warehouse warehouse;
	
	public TerminalGestionada(GestionTerrestre gestionTerrestre, Logistica logistica, Warehouse warehouse) {
		this.logistica = logistica;
		this.warehouse = warehouse;
		this.gestionTerrestre = gestionTerrestre;
	}
}
