package gestiontest.gestores;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gestion.gestores.GestionTerrestre;
import gestion.gestores.GestorDeExportacion;
import gestion.ordenes.OrdenDeExportacion;
import gestion.ordenes.OrdenDeImportacion;
import gestion.terrestre.Camion;
import gestion.terrestre.Cliente;
import gestion.terrestre.EmpresaTransportista;
import gestion.terrestre.Ubicacion;
import logistica.Circuito;
import logistica.Tramo;
import logistica.Viaje;
import terminalgestionada.TerminalGestionada;
import warehouse.Buque;
import warehouse.Carga;
import warehouse.Dry;
import warehouse.Warehouse;



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
	Viaje viajeImportacion;
	Ubicacion ubicacionDestino;
	OrdenDeImportacion ordenDeImportacion;
	GestorDeExportacion gestorExportador;
	Warehouse warehouse;
	Carga carga;
	Circuito circuito1;
	Circuito circuito2;
	Tramo tramo1;
	Tramo tramo2;
	Buque buque;
	
	@BeforeEach
	public void setUp() {
		carga = new Dry(0.0,0.0,0.0,0.0,null, null);
		warehouse = new Warehouse();
		camion1 = new Camion("AZ 132 TT", "Javier",null);
		camion2 = new Camion("AZ 132 TT", "Sofia",null);
		ubicacion = new Ubicacion(1,2);
		ubicacionDestino = new Ubicacion(4,8);
		buque = new Buque(ubicacion, terminalGestionada, null);
		cliente = new Cliente("nico@gmail.com");
		gestion = new GestionTerrestre(warehouse);
		terminalDestino = new TerminalGestionada(ubicacion,gestion,null, warehouse);
		gestorExportador = new GestorDeExportacion(gestion, warehouse);
		terminalGestionada = new TerminalGestionada(ubicacionDestino, gestion, null, warehouse);
		tramo1 = new Tramo(terminalGestionada,terminalDestino,2,2);
		tramo2 = new Tramo(terminalDestino,terminalGestionada,3,3);
		List<Tramo> tramos1 = List.of(tramo1,tramo2);
		List<Tramo> tramos2 = List.of(tramo2,tramo1);
		circuito1 = new Circuito(tramos1);
		circuito2 = new Circuito(tramos2);
		viaje = new Viaje(buque,circuito1 ,LocalDate.now());
		viajeImportacion = new Viaje(buque, circuito2,LocalDate.now());
		empresaCamionera = new EmpresaTransportista("Don atilio");
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
	
	@Test
	public void registroDeCamionConExcepcion() {
		assertThrows(IllegalArgumentException.class,() -> gestion.agregarCamion(empresaCamionera,camion1));
	}
	
	
	
	
	
}
