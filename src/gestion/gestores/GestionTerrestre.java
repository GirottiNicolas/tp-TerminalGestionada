package gestion.gestores;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import gestion.gestores.exportacion.GestorDeExportacion;
import gestion.gestores.exportacion.OrdenDeExportacion;
import gestion.gestores.importacion.GestorDeImportacion;
import gestion.gestores.importacion.OrdenDeImportacion;
import gestion.gestores.ordenes.Orden;
import gestion.terrestre.Camion;
import gestion.terrestre.Cliente;
import gestion.terrestre.EmpresaTransportista;
import terminalgestionada.TerminalGestionada;
import warehouse.Buque;
import warehouse.Warehouse;



public class GestionTerrestre {

	List<Cliente> clientes;
	List<EmpresaTransportista> transportistas;
	List<Orden> exportaciones;
	List<Orden> importaciones;
	Warehouse warehouse;
	GestorDeExportacion gestorExportador;
	GestorDeImportacion gestorImportador;
	
	public GestionTerrestre(Warehouse warehouse) {
		this.clientes = new ArrayList<Cliente>();
		this.exportaciones = new ArrayList<Orden>();
		this.importaciones = new ArrayList<Orden>();
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

	public void agregarAImportaciones(OrdenDeImportacion orden) {
		importaciones.add(orden);
		
	}
	
	public void agregarAExportaciones(OrdenDeExportacion orden) {
		exportaciones.add(orden);
	}
	
	private List<Orden> ordenesDeComercioExterior() {
		return Stream.concat(exportaciones.stream(), importaciones.stream())
		          .toList();
	}
	
	public boolean tieneOrden(Orden orden) {
		return this.ordenesDeComercioExterior().contains(orden);
	}

	
	
	private void enviarMail(Cliente cliente) {
		System.out.println("Destinatario: "+ cliente.getEmail() + "Asunto: Ya tienes un turno, para tu orden de comercio" );
		
	}

	public void notificarConsignees(Buque buque) {
		this.notificar(exportaciones, buque);
	}

	

	public void notificarShippers(Buque buque) {
		this.notificar(importaciones, buque);	
	}
	
	private void notificar(List<Orden> ordenes, Buque buque) {
		ordenes.stream().
				filter(orden -> buque.esElBuque(orden.getBuque()))
				.forEach(orden -> {
					orden.setFechaDeNotificacion(LocalDateTime.now());
					this.enviarMail( orden.getCliente());
					orden.asignarTurno(LocalDateTime.now()); // No se profundiza en la gestion del turno, por lo tanto, la fecha es estimada.
				});
		
	}

	public void agregarCamion(EmpresaTransportista empresa, Camion camion) {
		this.verificarEmpresa(empresa);
		empresa.agregarCamion(camion);
		
	}
	
	private void verificarEmpresa(EmpresaTransportista empresaARegistrar) {
		if(!transportistas.contains(empresaARegistrar)) {
			throw new IllegalArgumentException("No existe la empresa dada");
		}
	}
	

}
