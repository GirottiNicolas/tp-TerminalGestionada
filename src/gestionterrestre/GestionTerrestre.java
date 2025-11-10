package gestionterrestre;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import terminalgestionada.TerminalGestionada;
import warehouse.Warehouse;



public class GestionTerrestre {

	List<Cliente> clientes;
	List<EmpresaTransportista> transportistas;
	List<OrdenDeExportacion> exportaciones;
	Warehouse warehouse;
	
	public GestionTerrestre() {
		this.clientes = new ArrayList<Cliente>();
		this.exportaciones = new ArrayList<OrdenDeExportacion>();
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
			ordenExportacion.asignarTurno(LocalDateTime.now());
			exportaciones.add(ordenExportacion);
		}
		
	}
	
	public boolean puedeRealizarExportacion(Cliente cliente, OrdenDeExportacion orden, TerminalGestionada terminalGestionada) {
		return this.esCliente(cliente) && orden.parteDeLaTerminal(terminalGestionada);
	}
	

	private boolean esCliente(Cliente cliente) {
		return clientes.contains(cliente);
	}

	public void recibirCargaDeTransporte(OrdenDeExportacion ordenExportacion, Camion camion) {
		this.verificarTransporte(camion, ordenExportacion);
		this.verificarTurno(ordenExportacion, LocalDateTime.now());
		
		// warehouse.registrarCarga(camion.getCarga());
		
	}
	
	private boolean verificarTurno(OrdenDeExportacion orden, LocalDateTime horarioDelCamion) {
		return orden.getTurno().equals(horarioDelCamion);
	}

	private boolean verificarTransporte(Camion camion, OrdenDeExportacion ordenExportacion) {
		return camion.esCamionDesignado(ordenExportacion.getCamion()) && ordenExportacion.cumpleHorario(LocalDateTime.now());
	}
	
	
	
	

}
