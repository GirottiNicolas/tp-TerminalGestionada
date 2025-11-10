package gestionterrestretest;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gestionterrestre.Camion;
import gestionterrestre.Cliente;
import gestionterrestre.EmpresaTransportista;
import gestionterrestre.GestionTerrestre;
import gestionterrestre.OrdenDeExportacion;
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
	Camion camion;
	Viaje viaje;
	Ubicacion ubicacionDestino;
	
	
	@BeforeEach
	public void setUp() {
		camion = new Camion("AZ 132 TT", "Javier",null);
		ubicacion = new Ubicacion(1,2,1.0);
		ubicacionDestino = new Ubicacion(4,8,1.0);
		terminalDestino = new TerminalGestionada(ubicacion,null,null, null);
		cliente = new Cliente();
		gestion = new GestionTerrestre();
		terminalGestionada = new TerminalGestionada(ubicacionDestino, gestion, null, null);
		viaje = new Viaje(terminalGestionada);
		empresaCamionera = new EmpresaTransportista();
		ordenExportacion = new OrdenDeExportacion(viaje,null,camion);
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
		assertDoesNotThrow(() -> gestion.exportar(ordenExportacion, cliente,terminalGestionada));
	}
	
	@Test
	public void exportacionConErrorPorTerminalOrigen() {
		Viaje viaje = new Viaje(terminalDestino);
		OrdenDeExportacion orden = new OrdenDeExportacion(viaje, null, camion);
		gestion.agregarCliente(cliente);
		assertThrows(RuntimeException.class,() -> gestion.exportar(orden, cliente,terminalGestionada));
	}
	
	
	@Test
	public void ordenParteDeTerminalGestionada() {
		assertTrue(ordenExportacion.parteDeLaTerminal(terminalGestionada));
	}
	
	
	@Test
	public void camionEntregaCarga() {
		gestion.agregarCliente(cliente);
		gestion.exportar(ordenExportacion, cliente,terminalGestionada);
		assertDoesNotThrow(() -> gestion.recibirCargaDeTransporte(ordenExportacion,camion));
	
	}
	
	
	@Test
	public void esLaMismaOrden() {
		// Dos ordenes pueden ser iguales unicamente si fueron seteadas con fecha
		// caso contrario, dara false.
		gestion.agregarCliente(cliente);
		gestion.exportar(ordenExportacion, cliente,terminalGestionada);
		assertTrue(ordenExportacion.esOrden(ordenExportacion));
	}
	
	
}
