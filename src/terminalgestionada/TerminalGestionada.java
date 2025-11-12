package terminalgestionada;

import gestion.gestores.GestionTerrestre;
import gestion.ordenes.OrdenDeExportacion;
import gestion.ordenes.OrdenDeImportacion;
import gestion.terrestre.Cliente;
import gestion.terrestre.EmpresaTransportista;
import gestion.terrestre.Ubicacion;
import logistica.Circuito;
import logistica.Logistica;
import logistica.Naviera;
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
	
	@Override
	public boolean esLaTerminal(TerminalGestionada terminal) {
		return posicionGeografica.esLaUbicacion(terminal.getPosicionGeografica());
	}
	
	@Override
	public Ubicacion getPosicionGeografica() {
		return posicionGeografica;
	}

	
	@Override
	public Circuito mejorCircuito() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registrarNaviera(Naviera naviera) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void importar(OrdenDeImportacion orden) {
		gestionTerrestre.importar(orden, this);
		
	}

	@Override
	public void exportar(OrdenDeExportacion orden) {
		gestionTerrestre.exportar(orden, this);
		
	}

	@Override
	public void registrarEmpresaTransportista(EmpresaTransportista empresa) {
		gestionTerrestre.registrarEmpresaTransporte(empresa);
		
	}
	
	@Override
	public void agregarCliente(Cliente cliente) {
		gestionTerrestre.agregarCliente(cliente);
		
	}
	
	public void notificarArriboInminente(Buque buque) {
		gestionTerrestre.notificarConsignees(buque);
	}
	
	public void notificarPartida(Buque buque) {
        // La terminal se encarga de la lógica de enviar emails a los shippers cuando sale un buque con carga de la terminal
		gestionTerrestre.notificarShippers(buque);
	}
	
}
