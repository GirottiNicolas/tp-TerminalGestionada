package gestiontest.gestores;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
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

public class GestorImportadorTest {
		
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
		EmpresaTransportista empresaCamionera;
		OrdenDeExportacion ordenExportacion;
		Camion camion1;
		Camion camion2;
		
		Viaje viaje;
		
		
		OrdenDeImportacion ordenDeImportacion;
		GestorDeExportacion gestorExportador;
		GestorDeImportacion gestorImportador;
		Warehouse warehouse;
		Carga carga;	
		Buque buque;
		
		
		@BeforeEach
		public void setUp() {
			
			carga = new Dry(0.0,0.0,0.0,0.0,null, null);
			warehouse = new Warehouse();
			camion1 = new Camion("AZ 132 TT", "Javier",null);
			camion2 = new Camion("AZ 132 TT", "Sofia",null);
			cliente = new Cliente("nico@gmail.com");
			gestion = new GestionTerrestre(warehouse);
			buque = new Buque(new Ubicacion(2,3), terminalA, null);
			gestorExportador = new GestorDeExportacion(gestion, warehouse);
			
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
			
			
			empresaCamionera = new EmpresaTransportista();
			ordenExportacion = new OrdenDeExportacion(viaje,null,camion1, cliente);
			ordenDeImportacion = new OrdenDeImportacion(viaje, carga,camion1,cliente,null);
			gestorImportador = new GestorDeImportacion(gestion,warehouse);
			
		}
		
		
		@Test
		public void esOrdenValida() {
			
			assertTrue(gestorImportador.esUnaOrdenValida(ordenDeImportacion, terminalC));
		}
		
		
		@Test
		public void procesarImportacion() {
			gestion.agregarCliente(cliente);
			assertDoesNotThrow(() -> gestion.importar(ordenDeImportacion, terminalC));
			assertTrue(gestion.tieneOrden(ordenDeImportacion));
		}
		
		
		@Test
		public void importacionFallidaYaQueNoEsCliente() {
			assertThrows(RuntimeException.class, () -> gestion.importar(ordenDeImportacion, terminalB));	
		}
		
		
		@Test
		public void retiroDeCargaFallidaPorFaltaDeTurno() {
			gestion.agregarCliente(cliente);
			gestion.importar(ordenDeImportacion, terminalC);
			
			// No puede retirar la carga ya que no fue notificado con un turno
			assertThrows(RuntimeException.class,() -> gestion.retirarCargaDeImportador(camion1, ordenDeImportacion));
		}
		
		@Test
		public void retiroDeCargaExitosa() {
			gestion.agregarCliente(cliente);
			gestion.importar(ordenDeImportacion, terminalC);
			gestion.notificarShippers(buque);
			assertDoesNotThrow(() -> gestion.retirarCargaDeImportador(camion1, ordenDeImportacion));
		}
		
		
		
		
}
