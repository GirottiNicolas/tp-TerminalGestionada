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
import warehouse.ServicioAlmacenamiento;
import warehouse.Warehouse;



public class GestionTerrestre {

	List<Cliente> clientes;
	List<EmpresaTransportista> transportistas;
	List<OrdenDeExportacion> exportaciones;
	List<OrdenDeImportacion> importaciones;
	Warehouse warehouse;
	GestorDeExportacion gestorExportador;
	GestorDeImportacion gestorImportador;
	
	public GestionTerrestre() {
		this.clientes = new ArrayList<Cliente>();
		this.exportaciones = new ArrayList<OrdenDeExportacion>();
		this.transportistas = new ArrayList<EmpresaTransportista>();
		this.gestorExportador = new GestorDeExportacion(this, warehouse);
		this.gestorImportador = new GestorDeImportacion(this,warehouse);
	}
	
	public void exportar(Orden ordenExportacion, TerminalGestionada terminalGestionada) {
		gestorExportador.ejecutarOperacion(ordenExportacion,terminalGestionada);
	}
	
	public void importar(Orden ordenImportacion,TerminalGestionada terminal) {
		gestorImportador.ejecutarOperacion(ordenImportacion, terminal);
		
	}
	
	
	
	
	public void retirarCargaDeImportador(Camion camion, Orden orden) {
		gestorImportador.retiroDeCarga(orden, camion);
	}
	

	protected void verificarHorarioDeRetiro(Orden orden) {
		if(!orden.cumpleHorario(LocalDateTime.now(), 24)) {
			warehouse.aplicarServicio(new ServicioAlmacenamiento((OrdenDeImportacion) orden, 300.0), orden.getCarga());
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

	protected void agregarAImportaciones(OrdenDeImportacion orden) {
		importaciones.add(orden);
		
	}
	
	protected void agregarAExportaciones(OrdenDeExportacion orden) {
		exportaciones.add(orden);
	}
	
	
	

}
