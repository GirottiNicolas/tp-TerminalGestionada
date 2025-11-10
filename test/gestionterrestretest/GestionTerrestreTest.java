package gestionterrestretest;

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
	
	
	@BeforeEach
	public void setUp() {
		ubicacion = new Ubicacion(1,2,1.0);
		terminalDestino = new TerminalGestionada(ubicacion,null,null, null);
		viaje = new Viaje(terminalDestino);
		cliente = new Cliente();
		gestion = new GestionTerrestre();
		terminalGestionada = new TerminalGestionada(ubicacion, gestion, null, null);
		empresaCamionera = new EmpresaTransportista();
		ordenExportacion = new OrdenDeExportacion(null,null,camion);
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
		gestion.exportar(ordenExportacion, cliente,terminalGestionada);
	}
	
	
	@Test
	public void esLaMismaPosicion() {
		assertTrue(terminalGestionada.esLaTerminal(terminalDestino));
	}
	
}
