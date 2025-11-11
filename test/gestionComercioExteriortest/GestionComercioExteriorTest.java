package gestionComercioExteriortest;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gestionoperacion.GestionComercioExterior;
import gestionoperacion.GestorDeExportacion;
import gestionterrestre.dummies.Viaje;
import gestionterrestre.ordenes.OrdenDeExportacion;
import gestionterrestre.ordenes.OrdenDeImportacion;
import terminalgestionada.TerminalGestionada;
import transporte.Camion;
import transporte.Cliente;
import transporte.EmpresaTransportista;
import transporte.Ubicacion;
import warehouse.Carga;
import warehouse.Dry;
import warehouse.Warehouse;



public class GestionComercioExteriorTest {
	
	
	Ubicacion ubicacion;
	TerminalGestionada terminalGestionada;
	TerminalGestionada terminalDestino;
	GestionComercioExterior gestion;
	Cliente cliente;
	EmpresaTransportista empresaCamionera;
	OrdenDeExportacion ordenExportacion;
	Camion camion1;
	Camion camion2;
	Viaje viaje;
	Viaje viajeImportacion;
	Ubicacion ubicacionDestino;
	OrdenDeImportacion ordenDeImportacion;
	GestorDeExportacion gestorExportador;
	Warehouse warehouse;
	Carga carga;
	
	
	@BeforeEach
	public void setUp() {
		carga = new Dry(0.0,0.0,0.0,0.0,null);
		warehouse = new Warehouse();
		camion1 = new Camion("AZ 132 TT", "Javier",null);
		camion2 = new Camion("AZ 132 TT", "Sofia",null);
		ubicacion = new Ubicacion(1,2,1.0);
		ubicacionDestino = new Ubicacion(4,8,1.0);
		terminalDestino = new TerminalGestionada(ubicacion,null,null, null);
		cliente = new Cliente("nico@gmail.com");
		gestion = new GestionComercioExterior();
		gestorExportador = new GestorDeExportacion(gestion, warehouse);
		terminalGestionada = new TerminalGestionada(ubicacionDestino, gestion, null, null);
		viaje = new Viaje(terminalGestionada, terminalDestino);
		viajeImportacion = new Viaje(terminalDestino, terminalGestionada);
		empresaCamionera = new EmpresaTransportista();
		ordenExportacion = new OrdenDeExportacion(viaje,null,camion1, cliente);
		ordenDeImportacion = new OrdenDeImportacion(viajeImportacion, carga,camion1,cliente,null);
	}
	

	@Test
	public void clienteAgregado() {
		gestion.agregarCliente(cliente);
		assertTrue(gestion.getClientes().contains(cliente));
	}
	
	@Test
	public void clienteEliminado() {
		gestion.agregarCliente(cliente);
		assertTrue(gestion.getClientes().contains(cliente));
		gestion.eliminarCliente(cliente);
		assertFalse(gestion.getClientes().contains(cliente));
	}
	
	
	
	@Test
	public void empresaTransportistaAgregada() {
		gestion.registrarEmpresaTransporte(empresaCamionera);
		assertTrue(gestion.getTransportistas().contains(empresaCamionera));
	}
	
	@Test
	public void exportar() {
		gestion.agregarCliente(cliente);
		assertDoesNotThrow(() -> gestion.exportar(ordenExportacion,terminalGestionada));
	}
	
	@Test
	public void exportacionConErrorPorTerminalOrigen() {
		Viaje viaje = new Viaje(terminalDestino,terminalGestionada);
		OrdenDeExportacion orden = new OrdenDeExportacion(viaje, null, camion1,null);
		gestion.agregarCliente(cliente);
		assertThrows(RuntimeException.class,() -> gestion.exportar(orden, terminalGestionada));
	}
	
	
	@Test
	public void ordenParteDeTerminalGestionada() {
		assertTrue(ordenExportacion.parteDeLaTerminal(terminalGestionada));
	}
	
	
	@Test
	public void camionEntregaCarga() {
		gestion.agregarCliente(cliente);
		gestion.exportar(ordenExportacion,terminalGestionada);
		assertDoesNotThrow(() -> gestorExportador.recibirCargaDeTransporte(ordenExportacion,camion1));
	
	}
	
	
	@Test
	public void camionNoPuedeEntregarCarga() {
		gestion.agregarCliente(cliente);
		gestion.exportar(ordenExportacion,terminalGestionada);
		assertThrows(RuntimeException.class,() -> gestorExportador.recibirCargaDeTransporte(ordenExportacion,camion2));
	
	}
	
	
	@Test
	public void esLaMismaOrden() {
		// Dos ordenes pueden ser iguales unicamente si fueron seteadas con fecha
		// caso contrario, dara false.
		gestion.agregarCliente(cliente);
		gestion.exportar(ordenExportacion, terminalGestionada);
		assertTrue(ordenExportacion.esOrden(ordenExportacion));
	}
	
	
	@Test
	public void importar() {
		gestion.agregarCliente(cliente);
		assertDoesNotThrow(() -> gestion.importar(ordenDeImportacion, terminalGestionada));
	}
	
	
}
