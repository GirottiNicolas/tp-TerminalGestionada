package gestionoperacion;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import gestionterrestre.Camion;
import gestionterrestre.Cliente;
import gestionterrestre.EmpresaTransportista;
import gestionterrestre.Orden;
import gestionterrestre.OrdenDeExportacion;
import gestionterrestre.OrdenDeImportacion;
import terminalgestionada.TerminalGestionada;
import warehouse.Buque;
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
	
	private List<Orden> ordenesDeComercioExterior() {
		return Stream.concat(exportaciones.stream(), importaciones.stream())
		          .toList();
	}
	
	public boolean tieneOrden(Orden orden) {
		return this.ordenesDeComercioExterior().contains(orden);
	}

	public void notificarClientes(Buque buque) {
		this.ordenesDeComercioExterior().stream().
							// Filtrar aquellas ordenes interesadas por el buque dado
							filter(orden -> buque.esElBuque(orden.getBuqueDeViaje()))
							// Realizar la notificacion a cada uno de los clientes de dichas ordenes
							.forEach(orden -> {
									this.enviarMail( orden.getCliente());
									orden.asignarTurno(LocalDateTime.now()); // Cambiar por el tiempo calculado del viaje
							});
		
	}

	private void enviarMail(Cliente cliente) {
		System.out.println("Destinatario: "+ cliente.getEmail() + "Asunto: Ya tienes un turno, para tu orden de comercio" );
		
	}
	
	

}
