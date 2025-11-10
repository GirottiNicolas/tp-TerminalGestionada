package gestionterrestre;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import terminalgestionada.TerminalGestionada;

public class GestionTerrestre {

	List<Cliente> clientes;
	List<EmpresaTransportista> transportistas;
	List<OrdenDeExportacion> exportaciones;
	
	public GestionTerrestre() {
		this.clientes = new ArrayList<Cliente>();
		this.transportistas = new ArrayList<EmpresaTransportista>();
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

	public void exportar(OrdenDeExportacion ordenExportacion, Cliente cliente, TerminalGestionada terminalGestionada) {
		if (!(this.puedeRealizarExportacion(cliente, ordenExportacion, terminalGestionada))) {
			throw new RuntimeException("No puedes exportar!");
		}
		else {
			ordenExportacion.asignarTurno(new Date());
			exportaciones.add(ordenExportacion);
		}
		
	}
	
	public boolean puedeRealizarExportacion(Cliente cliente, OrdenDeExportacion orden, TerminalGestionada terminalGestionada) {
		return this.esCliente(cliente) && orden.parteDeLaTerminal(terminalGestionada);
	}
	

	private boolean esCliente(Cliente cliente) {
		return clientes.contains(cliente);
	}
	
	
	
	

}
