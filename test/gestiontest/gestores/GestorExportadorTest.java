package gestiontest.gestores;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gestion.gestores.GestionTerrestre;
import gestion.gestores.GestorDeExportacion;
import gestion.gestores.GestorDeImportacion;
import gestion.ordenes.OrdenDeExportacion;
import gestion.ordenes.OrdenDeImportacion;
import gestion.terrestre.Camion;
import gestion.terrestre.Cliente;
import gestion.terrestre.EmpresaTransportista;
import gestion.terrestre.Ubicacion;
import gestion.terrestre.dummies.Viaje;
import terminalgestionada.TerminalGestionada;
import warehouse.Buque;
import warehouse.Carga;
import warehouse.Dry;
import warehouse.Warehouse;

public class GestorExportadorTest {
	
	
	Ubicacion ubicacion;
	TerminalGestionada terminalOrigen;
	TerminalGestionada terminalDestino;
	GestionTerrestre gestion;
	Cliente cliente;
	OrdenDeExportacion ordenExportacion;
	Camion camion1;
	Camion camion2;
	Viaje viaje;
	Viaje viajeImportacion;
	Ubicacion ubicacionDestino;
	GestorDeExportacion gestorExportador;
	Warehouse warehouse;
	Carga carga;
	Buque buque;
	
	
	@BeforeEach
	public void setUp() {
		carga = new Dry(0.0,0.0,0.0,0.0,null);
		warehouse = new Warehouse();
		camion1 = new Camion("AZ 132 TT", "Javier",null);
		camion2 = new Camion("AZ 132 TT", "Sofia",null);
		ubicacion = new Ubicacion(1,2);
		ubicacionDestino = new Ubicacion(4,8);
		cliente = new Cliente("nico@gmail.com");
		gestion = new GestionTerrestre(warehouse);
		terminalDestino = new TerminalGestionada(ubicacion,gestion,null, warehouse);
		buque = new Buque(new Ubicacion(2,3), terminalDestino);
		gestorExportador = new GestorDeExportacion(gestion, warehouse);
		terminalOrigen = new TerminalGestionada(ubicacionDestino, gestion, null, warehouse);
		viaje = new Viaje(terminalOrigen, terminalDestino,buque);
		ordenExportacion = new OrdenDeExportacion(viaje,null,camion1, cliente);
		
	}
	
	@Test
	public void esUnaOrdenValida() {
		// Tiene como origen a la terminal dada
		assertTrue(gestorExportador.esUnaOrdenValida(ordenExportacion,terminalOrigen));
	}
	
	@Test
	public void procesaOrdenDeExportacion() {
		// Estado previo de que el gestor procese la orden
		assertFalse(ordenExportacion.tieneTurnoAsignado());
		assertFalse(gestion.tieneOrden(ordenExportacion));
		
		// Estado posterior de orden de exportacion
		gestorExportador.procesarOrden(ordenExportacion);
		assertTrue(ordenExportacion.tieneTurnoAsignado());
		assertTrue(gestion.tieneOrden(ordenExportacion));
	}
	
	public void noPuederecibirCargaDeTransporte() {
		gestorExportador.procesarOrden(ordenExportacion);
		assertThrows(RuntimeException.class,() -> gestorExportador.recibirCargaDeTransporte(ordenExportacion,camion2));
		assertFalse(warehouse.getCargasAlmacenadas().contains(ordenExportacion.getCarga()));
	}
	
	
	public void verificarTurnoConError() {
		LocalDateTime dia = LocalDateTime.now();
		gestorExportador.procesarOrden(ordenExportacion);
		// El camion llega 4 horas despues de la hora, por lo cual su turno NO es valido
		assertThrows(RuntimeException.class,() -> gestorExportador.verificarTurno(ordenExportacion, dia.plusHours(4)));
	}
	
	public void verificarTurnoExitoso() {
		LocalDateTime dia = LocalDateTime.now();
		gestorExportador.procesarOrden(ordenExportacion);
		// El camion llega 2 horas despues de la hora, por lo cual su turno es valido
		assertDoesNotThrow(() -> gestorExportador.verificarTurno(ordenExportacion, dia.plusHours(2)));
	}
	
	
	public void recibirCargaDeTransporte() {
		gestorExportador.procesarOrden(ordenExportacion);
		assertDoesNotThrow(() -> gestorExportador.recibirCargaDeTransporte(ordenExportacion,camion1));
		assertTrue(warehouse.getCargasAlmacenadas().contains(ordenExportacion.getCarga()));
	}
	
	
	
	
	
}