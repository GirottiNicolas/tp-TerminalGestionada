package gestionoperacion;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gestionterrestre.Camion;
import gestionterrestre.Cliente;
import gestionterrestre.EmpresaTransportista;
import gestionterrestre.Orden;
import gestionterrestre.OrdenDeExportacion;
import gestionterrestre.OrdenDeImportacion;
import terminalgestionada.TerminalGestionada;
import warehouse.Warehouse;



public class GestionTerrestre {

	List<Cliente> clientes;
	List<EmpresaTransportista> transportistas;
	List<OrdenDeExportacion> exportaciones;
	List<OrdenDeImportacion> importaciones;
	Warehouse warehouse;
	GestorDeExportacion gestorExportador;
	
	public GestionTerrestre() {
		this.clientes = new ArrayList<Cliente>();
		this.exportaciones = new ArrayList<OrdenDeExportacion>();
		this.transportistas = new ArrayList<EmpresaTransportista>();
		this.gestorExportador = new GestorDeExportacion(this);
	}
	
	public void exportar(Orden ordenExportacion, TerminalGestionada terminalGestionada) {
		gestorExportador.ejecutarOperacion(ordenExportacion,terminalGestionada);
	}
	
		
	
	public void importar(OrdenDeImportacion ordenImportacion,TerminalGestionada terminal) {
		if (!(this.puedeRealizarImportacion(ordenImportacion, terminal))) {
			throw new RuntimeException("No puedes importar!");
		}
		else {
			
		}
		
	}
	
	protected void agregarAExportaciones(OrdenDeExportacion orden) {
		exportaciones.add(orden);
	}
	
	
	private boolean puedeRealizarImportacion(OrdenDeImportacion ordenImportacion, TerminalGestionada terminal) {
		return this.esCliente(ordenImportacion.getCliente()) && terminal.esLaTerminal(ordenImportacion.getDestinoDeImportacion())  ;
	}


	private void verificarHorarioDeRetiro(Orden orden) {
		if(!orden.cumpleHorario(LocalDateTime.now(), 24)) {
			//warehouse.aplicarServicio("Almacenamiento Excedente", orden.getCarga());
		}
	}
	
	
	protected void verificarTurno(OrdenDeExportacion orden, LocalDateTime horarioDelCamion) {
		if (!orden.cumpleHorario(LocalDateTime.now(), 3)) {
	        throw new RuntimeException("El camión no cumple con el horario permitido.");
	    }
	}

	protected void verificarTransporte(Camion camion, Orden orden) {
		gestorExportador.verificarTransporte(camion,orden);
	}
	
	public boolean esCliente(Cliente cliente) {
		return clientes.contains(cliente);
	}
	
	public void agregarCliente(Cliente cliente) {
		clientes.add(cliente);
		
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void registrarEmpresaTransporte(EmpresaTransportista empresaCamionera) {
		transportistas.add(empresaCamionera);
		
	}

	public List<EmpresaTransportista> getTransportistas() {
		return transportistas;
	}

	public void eliminarCliente(Cliente cliente) {
		clientes.remove(cliente);
	}
	
	
	
	

}
