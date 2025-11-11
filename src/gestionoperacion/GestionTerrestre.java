package gestionoperacion;


import java.util.ArrayList;
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
	GestorDeImportacion gestorImportador;
	
	public GestionTerrestre() {
		this.clientes = new ArrayList<Cliente>();
		this.exportaciones = new ArrayList<OrdenDeExportacion>();
		this.importaciones = new ArrayList<OrdenDeImportacion>();
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
		
	public void retirarCargaDeImportador(Camion camion, OrdenDeImportacion orden) {
		gestorImportador.retiroDeCarga(orden, camion);
	}
	

	protected void recibirCarga(Camion camion, OrdenDeExportacion orden) {
		gestorExportador.recibirCargaDeTransporte(orden,camion);
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
