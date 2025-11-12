package gestiontest.gestores;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

public class GestorImportadorTest {
			
		
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
		GestorDeImportacion gestorImportador;
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
			terminalGestionada = new TerminalGestionada(ubicacionDestino, gestion, null, warehouse);
			viaje = new Viaje(terminalGestionada, terminalDestino,buque);
			viajeImportacion = new Viaje(terminalDestino, terminalGestionada,buque);
			empresaCamionera = new EmpresaTransportista();
			ordenExportacion = new OrdenDeExportacion(viaje,null,camion1, cliente);
			ordenDeImportacion = new OrdenDeImportacion(viajeImportacion, carga,camion1,cliente,null);
			gestorImportador = new GestorDeImportacion(gestion,warehouse);
			
		}
		
		
		@Test
		public void esOrdenValida() {
			assertTrue(gestorImportador.esUnaOrdenValida(ordenDeImportacion, terminalGestionada));
		}
		
		
		@Test
		public void procesarImportacion() {
			gestion.agregarCliente(cliente);
			assertDoesNotThrow(() -> gestion.importar(ordenDeImportacion, terminalGestionada));
			assertTrue(gestion.tieneOrden(ordenDeImportacion));
		}
		
		
		@Test
		public void importacionFallidaYaQueNoEsCliente() {
			assertThrows(RuntimeException.class, () -> gestion.importar(ordenDeImportacion, terminalGestionada));	
		}
		
		
		@Test
		public void retiroDeCargaFallidaPorFaltaDeTurno() {
			gestion.agregarCliente(cliente);
			gestion.importar(ordenDeImportacion, terminalGestionada);
			
			// No puede retirar la carga ya que no fue notificado con un turno
			assertThrows(RuntimeException.class,() -> gestion.retirarCargaDeImportador(camion1, ordenDeImportacion));
		}
		
		@Test
		public void retiroDeCargaExitosa() {
			gestion.agregarCliente(cliente);
			gestion.importar(ordenDeImportacion, terminalGestionada);
			gestion.notificarShippers(buque);
			assertDoesNotThrow(() -> gestion.retirarCargaDeImportador(camion1, ordenDeImportacion));
		}
		
		
		
		
}
