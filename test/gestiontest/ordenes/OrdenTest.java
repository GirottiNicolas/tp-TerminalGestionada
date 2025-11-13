package gestiontest.ordenes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import gestion.ordenes.OrdenDeExportacion;
import gestion.ordenes.OrdenDeImportacion;
import gestion.terrestre.Camion;
import gestion.terrestre.Cliente;
import gestion.terrestre.Ubicacion;
import logistica.Circuito;
import logistica.Tramo;
import logistica.Viaje;
import terminalgestionada.TerminalGestionada;
import warehouse.Buque;
import warehouse.Carga;
import warehouse.Dry;


public class OrdenTest {
	
	OrdenDeImportacion ordenDeImportacion;
	OrdenDeExportacion ordenExportacion;
	Carga carga;
	Camion camion;
	Cliente cliente;
	Viaje viaje;
	Ubicacion ubicacion;
	Ubicacion ubicacionDestino;
	TerminalGestionada terminalOrigen;
	TerminalGestionada terminalDestino;
	Circuito circuito1;
	Circuito circuito2;
	Tramo tramo1;
	Tramo tramo2;
	Buque buque;
	
	@BeforeEach
	public void setUp() {
		ubicacion = new Ubicacion(1,2);
		ubicacionDestino = new Ubicacion(4,8);
		cliente = new Cliente("nico@gmail.com");
		carga = new Dry(1.0,1.0,1.0,1.0,null, null);
		camion = new Camion("AAA 222 EE", "Juan",carga);
		cliente = new Cliente("nico@gmail.com");
		terminalOrigen = new TerminalGestionada(ubicacion,null,null, null);
		terminalDestino = new TerminalGestionada(ubicacionDestino,null,null, null);
		tramo1 = new Tramo(terminalOrigen,terminalDestino,2,2);
		tramo2 = new Tramo(terminalDestino,terminalOrigen,3,3);
		List<Tramo> tramos1 = List.of(tramo1,tramo2);
		List<Tramo> tramos2 = List.of(tramo2,tramo1);
		circuito1 = new Circuito(tramos1);
		circuito2 = new Circuito(tramos2);
		viaje = new Viaje(buque,circuito1 ,LocalDate.now());
		ordenDeImportacion = new OrdenDeImportacion(viaje,carga,camion,cliente,null);
		ordenExportacion = new OrdenDeExportacion(viaje,carga,camion,cliente);
		
	}
	
	// Los siguientes tests cubren tanto a las ordenes de exportacion 
	// como tambien de importacion, debido a que implementan la misma interface
	
	@Test
	public void ordenDeImportacionConErrorPorNoTenerTurno() {
		assertThrows(RuntimeException.class,() -> ordenDeImportacion.verificarTurno());;
	}
	
	@Test
	public void ordenDeImportacionConTurnoAsignado() {
		ordenDeImportacion.asignarTurno(LocalDateTime.now());
		assertDoesNotThrow(() -> ordenDeImportacion.verificarTurno());;
	}
	
	@Test
	public void ordenTieneTurnoAsignado() {
		ordenDeImportacion.asignarTurno(LocalDateTime.now());
		assertTrue(ordenDeImportacion.tieneTurnoAsignado());;
	}
	
	@Test
	public void esOrdenDaFalso() {
		assertFalse(ordenDeImportacion.esOrden(ordenExportacion));
	}
	
	@Test
	public void esLaMismaOrden() {
		ordenDeImportacion.asignarTurno(LocalDateTime.now());
		assertTrue(ordenDeImportacion.esOrden(ordenDeImportacion));
	}
	
	@Test
	public void ordenSinTurnoDaraFalse() {
		// Dos ordenes pueden ser iguales unicamente si su fecha fue seteada 
		assertFalse(ordenDeImportacion.esOrden(ordenDeImportacion));
	}
	
	@Test
	public void fechaDeNotificacion() {
		LocalDateTime fechaDeNotificacion = LocalDateTime.now();
		ordenDeImportacion.setFechaDeNotificacion(fechaDeNotificacion);
		assertTrue(ordenDeImportacion.getFechaLlegadaNotificada().equals(fechaDeNotificacion));
	}
	
	
	// Sobre la orden de exportacion
	@Test 
	public void parteDeLaTerminalGestionada() {
		assertTrue(ordenExportacion.parteDeLaTerminal(terminalOrigen));
	}
	
	@Test 
	public void noParteDeLaTerminalGestionada() {
		assertFalse(ordenExportacion.parteDeLaTerminal(terminalDestino));
	}
	
	// Sobre la orden de importacion
	@Test
	public void fechaDeRetiro() {
		LocalDateTime fechaDeRetiro = LocalDateTime.now();
		ordenDeImportacion.setFechaRetiroEfectivo(fechaDeRetiro);
		assertTrue(ordenDeImportacion.getFechaRetiroEfectivo().equals(fechaDeRetiro));
	}
}
