package terminalgestionada;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import filtros.FiltroBusqueda;
import gestion.gestores.GestionTerrestre;
import gestion.gestores.exportacion.OrdenDeExportacion;
import gestion.gestores.importacion.OrdenDeImportacion;
import gestion.terrestre.Camion;
import gestion.terrestre.Cliente;
import gestion.terrestre.EmpresaTransportista;
import gestion.terrestre.Ubicacion;
import logistica.Circuito;
import logistica.EstrategiaDeBusqueda;
import logistica.Logistica;
import logistica.Naviera;
import logistica.Viaje;
import warehouse.Buque;
import warehouse.Warehouse;
import warehouse.Carga;
import warehouse.IServicio;

public class TerminalGestionada {
	
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
	

	public boolean esLaTerminal(TerminalGestionada terminal) {
		return posicionGeografica.esLaUbicacion(terminal.getPosicionGeografica());
	}

	public Ubicacion getPosicionGeografica() {
		return posicionGeografica;
	}


	public Circuito mejorCircuito(TerminalGestionada terminal) {
		return logistica.mejorCircuito(this, terminal);
	}


	public void registrarNaviera(Naviera naviera) {
		logistica.registrarNaviera(naviera);
		
	}


	public void importar(OrdenDeImportacion orden) {
		gestionTerrestre.importar(orden, this);
		
	}

	public void exportar(OrdenDeExportacion orden) {
		gestionTerrestre.exportar(orden, this);
		
	}


	public void registrarEmpresaTransportista(EmpresaTransportista empresa) {
		gestionTerrestre.registrarEmpresaTransporte(empresa);
		
	}

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


	public void setMejorEstrategiaParaCircuito(EstrategiaDeBusqueda estrategia) {
		logistica.setMejorCircuito(estrategia);
		
	}


	public LocalDate proximaFechaDePartida(Buque buque, TerminalGestionada destino) {
		return logistica.primeraFechaBuque(LocalDate.now(), buque, this, destino);
	}

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
	
	public int tiempoDeNavieraHasta(Naviera naviera, TerminalGestionada destino) {
		return logistica.tiempoDeNavieraEntre(naviera, this, destino);
	}
	
	
	public List<Viaje> buscarViajes(FiltroBusqueda filtro) {
        List<Viaje> todosLosViajes = this.logistica.getViajes();
        
        return todosLosViajes.stream()
                .filter(viaje -> filtro.cumple(viaje))
                .collect(Collectors.toList());
    }
	

	public int tiempoDeNavieraEntre(Naviera naviera, TerminalGestionada origen, TerminalGestionada destino) {
		return logistica.tiempoDeNavieraEntre(naviera, this, destino);
	}
	
	
}
