package gestionterrestretest;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gestionoperacion.GestionTerrestre;
import gestionterrestre.Camion;
import gestionterrestre.Cliente;
import gestionterrestre.EmpresaTransportista;
import gestionterrestre.OrdenDeExportacion;
import gestionterrestre.OrdenDeImportacion;
import gestionterrestre.Ubicacion;
import gestionterrestre.dummies.Viaje;
import terminalgestionada.TerminalGestionada;



public class GestionTerrestreTest {
	
	
	Ubicacion ubicacion;
	TerminalGestionada terminalGestionada;
	TerminalGestionada terminalDestino;
	GestionTerrestre gestion;
	Cliente cliente;
	EmpresaTransportista empresaCamionera;
	OrdenDeExportacion ordenExportacion;
	Camion camion1;
	Camion camion2;
	Viaje viaje;
	Ubicacion ubicacionDestino;
	OrdenDeImportacion ordenDeImportacion;
	
	
	@BeforeEach
	public void setUp() {
		camion1 = new Camion("AZ 132 TT", "Javier",null);
		camion2 = new Camion("AZ 132 TT", "Sofia",null);
		ubicacion = new Ubicacion(1,2,1.0);
		ubicacionDestino = new Ubicacion(4,8,1.0);
		terminalDestino = new TerminalGestionada(ubicacion,null,null, null);
		cliente = new Cliente("nico@gmail.com");
		gestion = new GestionTerrestre();
		terminalGestionada = new TerminalGestionada(ubicacionDestino, gestion, null, null);
		viaje = new Viaje(terminalGestionada, terminalDestino);
		empresaCamionera = new EmpresaTransportista();
		ordenExportacion = new OrdenDeExportacion(viaje,null,camion1, cliente);
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
		assertDoesNotThrow(() -> gestion.recibirCargaDeTransporte(ordenExportacion,camion1));
	
	}
	
	
	@Test
	public void camionNoPuedeEntregarCarga() {
		gestion.agregarCliente(cliente);
		gestion.exportar(ordenExportacion,terminalGestionada);
		assertThrows(RuntimeException.class,() -> gestion.recibirCargaDeTransporte(ordenExportacion,camion2));
	
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
		//gestion.importar(ordenDeImportacion, camion1);
	}
	
	
}
