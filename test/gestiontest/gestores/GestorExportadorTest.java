package gestiontest.gestores;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
import logistica.Circuito;
import logistica.Tramo;
import logistica.Viaje;
import terminalgestionada.TerminalGestionada;
import warehouse.Buque;
import warehouse.Carga;
import warehouse.Dry;
import warehouse.Warehouse;

public class GestorExportadorTest {
	
	TerminalGestionada terminalA;
    TerminalGestionada terminalB;
    TerminalGestionada terminalC;
    Tramo tramoAB;
    Tramo tramoBC;
    Tramo tramoCA;
    Circuito circuito;
    Ubicacion posicionA;
    Ubicacion posicionB;
    Ubicacion posicionC;
	GestionTerrestre gestion;
	Cliente cliente;
	OrdenDeExportacion ordenExportacion;
	Camion camion1;
	Camion camion2;
	Viaje viaje;
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
		ubicacionDestino = new Ubicacion(4,8);
		cliente = new Cliente("nico@gmail.com");
		gestion = new GestionTerrestre(warehouse);
		posicionA = new Ubicacion(0, 0);
    	posicionB = new Ubicacion(3, 1);
    	posicionC = new Ubicacion(5, 2);
    	terminalA = new TerminalGestionada(posicionA, null, null, null);
    	terminalB = new TerminalGestionada(posicionB, null, null, null);
    	terminalC = new TerminalGestionada(posicionC, null, null, null);
    	tramoAB = new Tramo(terminalA, terminalB, 100, 2);
    	tramoBC = new Tramo(terminalB, terminalC, 50.2f, 6);
    	tramoCA = new Tramo(terminalC, terminalA, 48, 10);
        circuito = new Circuito(List.of(tramoAB, tramoBC,tramoCA));
		
		viaje = new Viaje(buque, circuito, LocalDate.now());	
		buque = new Buque(new Ubicacion(2,3), terminalA);
		gestorExportador = new GestorDeExportacion(gestion, warehouse);
		
		ordenExportacion = new OrdenDeExportacion(viaje,null,camion1, cliente);
		
	}
	
	@Test
	public void esUnaOrdenValida() {
		// Tiene como origen a la terminal dada
		assertTrue(gestorExportador.esUnaOrdenValida(ordenExportacion,terminalA));
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
	
	@Test
	public void noPuederecibirCargaDeTransporte() {
		assertDoesNotThrow(()->gestorExportador.procesarOrden(ordenExportacion));
		assertThrows(RuntimeException.class,() -> gestorExportador.recibirCargaDeTransporte(ordenExportacion,camion2));
		assertFalse(warehouse.getCargasAlmacenadas().contains(ordenExportacion.getCarga()));
	}
	
	@Test
	public void verificarTurnoConError() {
		LocalDateTime dia = LocalDateTime.now();
		gestorExportador.procesarOrden(ordenExportacion);
		// El camion llega 4 horas despues de la hora, por lo cual su turno NO es valido
		assertThrows(RuntimeException.class,() -> gestorExportador.verificarTurno(ordenExportacion, dia.plusHours(8)));
	}
	
	@Test
	public void verificarTurnoExitoso() {
		LocalDateTime dia = LocalDateTime.now();
		gestorExportador.procesarOrden(ordenExportacion);
		// El camion llega 2 horas despues de la hora, por lo cual su turno es valido
		assertDoesNotThrow(() -> gestorExportador.verificarTurno(ordenExportacion, dia.plusHours(2)));
	}
	
	@Test
	public void recibirCargaDeTransporte() {
		gestorExportador.procesarOrden(ordenExportacion);
		assertDoesNotThrow(() -> gestorExportador.recibirCargaDeTransporte(ordenExportacion,camion1));
		assertTrue(warehouse.getCargasAlmacenadas().contains(ordenExportacion.getCarga()));
	}
	
	
	
	
	
}