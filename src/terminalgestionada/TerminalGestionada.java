package terminalgestionada;

import java.time.LocalDate;

import gestion.gestores.GestionTerrestre;
import gestion.ordenes.OrdenDeExportacion;
import gestion.ordenes.OrdenDeImportacion;
import gestion.terrestre.Camion;
import gestion.terrestre.Cliente;
import gestion.terrestre.EmpresaTransportista;
import gestion.terrestre.Ubicacion;
import logistica.Circuito;
import logistica.EstrategiaDeBusqueda;
import logistica.Logistica;
import logistica.Naviera;
import warehouse.Buque;
import warehouse.Warehouse;
import warehouse.Carga;
import warehouse.IServicio;

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
	public Circuito mejorCircuito(TerminalGestionada terminal) {
		return logistica.mejorCircuito(terminal);

	}

	@Override
	public void registrarNaviera(Naviera naviera) {
		logistica.registrarNaviera(naviera);
		
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

	@Override
	public void setMejorEstrategiaParaCircuito(EstrategiaDeBusqueda estrategia) {
		logistica.setMejorCircuito(estrategia);
		
	}

	@Override
	public LocalDate proximaFechaDePartida(Buque buque, TerminalGestionada destino) {
		return logistica.primeraFechaDeBuque(LocalDate.now(), buque, destino);
	}

	@Override
	public void agregarCamion(EmpresaTransportista empresa, Camion camion) {
		gestionTerrestre.agregarCamion(empresa, camion);
		
	}
	
	public void solicitarServicio(IServicio servicio, Carga carga) {

        if (this.warehouse.contieneCarga(carga)) {
            // Delego a Warehouse
            this.warehouse.aplicarServicio(servicio, carga);
        } else {
            throw new IllegalArgumentException("La carga no se encuentra en la terminal.");
        }
    }
	
}
