package terminaltest;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import gestion.gestores.GestionTerrestre;
import gestion.gestores.exportacion.OrdenDeExportacion;
import gestion.gestores.importacion.OrdenDeImportacion;
import gestion.terrestre.Camion;
import gestion.terrestre.Cliente;
import gestion.terrestre.EmpresaTransportista;
import gestion.terrestre.Ubicacion;
import logistica.Circuito;
import logistica.EstrategiaCircuitoCorto;
import logistica.EstrategiaDeBusqueda;
import logistica.EstrategiaMenorTiempo;
import logistica.Logistica;
import logistica.Naviera;
import logistica.Tramo;
import logistica.Viaje;
import terminalgestionada.TerminalGestionada;
import warehouse.Buque;
import warehouse.Carga;
import warehouse.Dry;
import warehouse.IServicio;
import warehouse.Warehouse;

public class TerminalGestionadaTest {
	OrdenDeImportacion ordenDeImportacion;
	OrdenDeExportacion ordenExportacion;
	TerminalGestionada terminal;
	Ubicacion ubicacion;
	TerminalGestionada terminalA;
	TerminalGestionada terminalB;
	TerminalGestionada terminalC;
	Tramo tramoAB;
	Tramo tramoBC;
	Tramo tramoCA;
	Circuito circuito1;
	Ubicacion posicionA;
	Ubicacion posicionB;
	Ubicacion posicionC;
	EstrategiaDeBusqueda estrategiaCircuitoCorto;
	EstrategiaDeBusqueda estrategiaMenorTiempo;
	Circuito circuito2;
	Logistica logisticaA;
	Logistica logisticaC;
	Cliente cliente;
	GestionTerrestre gestionA;
	GestionTerrestre gestionC;
	Warehouse warehouseA;
	Warehouse warehouseC;
	Viaje viaje;
	Buque buque;
	Carga carga;
	Camion camion;
	EmpresaTransportista empresaTransporte;
	
	@BeforeEach
	public void setUp() {
		empresaTransporte = new EmpresaTransportista("Perez hermanos");
		carga = new Dry(1.0,1.0,1.0,1.0,null, null);
		cliente = new Cliente("Homero@gmail");
		estrategiaMenorTiempo = new EstrategiaMenorTiempo();
		logisticaA = new Logistica(estrategiaMenorTiempo);
		estrategiaCircuitoCorto =	new EstrategiaCircuitoCorto();
		ubicacion = new Ubicacion(1,2);
		posicionA = new Ubicacion(0, 0);
    	posicionB = new Ubicacion(3, 1);
    	posicionC = new Ubicacion(5, 2);
    	warehouseA = new Warehouse();
    	warehouseC = new Warehouse();
    	gestionA = new GestionTerrestre(warehouseA);
    	gestionC = new GestionTerrestre(warehouseC);
    	terminalA = new TerminalGestionada(posicionA, gestionA, logisticaA, warehouseA);
    	terminalB = new TerminalGestionada(posicionB, null, null, null);
    	terminalC = new TerminalGestionada(posicionC, gestionC, logisticaC, warehouseC);
    	tramoAB = new Tramo(terminalA, terminalB, 100, 2);
    	tramoBC = new Tramo(terminalB, terminalC, 50.2f, 6);
    	tramoCA = new Tramo(terminalC, terminalA, 48, 10);
        circuito1 = new Circuito(List.of(tramoAB, tramoBC,tramoCA));
        circuito2 = new Circuito(List.of(tramoAB, tramoCA));
        buque = new Buque(ubicacion, null, null);
        viaje = new Viaje(buque,circuito1 ,LocalDate.now());
        camion = new Camion("AAA 222 EE", "Juan",carga);
        ordenDeImportacion = new OrdenDeImportacion(viaje,carga,camion,cliente,null);
		ordenExportacion = new OrdenDeExportacion(viaje,carga,camion,cliente);
		
        
	}
	
	@Test
	public void esLaTerminal() {
		assertTrue(terminalA.esLaTerminal(terminalA));
	}
	
	@Test
	public void posicionGeograficaDeTerminal() {
		assertTrue(terminalA.getPosicionGeografica().esLaUbicacion(posicionA));
	}
	
	@Test
	public void setMejorEstrategiaParaCircuito() {
		assertDoesNotThrow(()->terminalA.setMejorEstrategiaParaCircuito(estrategiaMenorTiempo));
	}
	
	@Test
	public void agregarCliente() {
		assertFalse(gestionA.esCliente(cliente));
		assertDoesNotThrow(()-> terminalA.agregarCliente(cliente));
		assertTrue(gestionA.esCliente(cliente));
		
	}
	
	@Test 
	public void importar() {
		terminalC.agregarCliente(cliente);
		assertDoesNotThrow(()-> terminalC.importar(ordenDeImportacion));
	}
	

	
	@Test
	public void exportar() {
		terminalA.agregarCliente(cliente);
		assertDoesNotThrow(()-> terminalA.exportar(ordenExportacion));
	}
	
	@Test
	public void registrarEmpresaTransportista() {
		terminalA.registrarEmpresaTransportista(empresaTransporte);
		assertTrue(gestionA.getTransportistas().contains(empresaTransporte));
	}
	
	@Test
	public void registrarCamion() {
		terminalA.registrarEmpresaTransportista(empresaTransporte);
		terminalA.agregarCamion(empresaTransporte,camion);
		assertTrue(empresaTransporte.tieneCamion(camion));
	}
	
	@Test
	public void noPuederegistrarCamion() {
		assertThrows(IllegalArgumentException.class,() -> terminalA.agregarCamion(empresaTransporte,camion));
	}
	
	
	
	@Test
	public void mejorCircuito() {
		Naviera naviera = new Naviera();
        naviera.agregarCircuito(circuito1);
        naviera.agregarCircuito(circuito2);
        naviera.agregarViaje(viaje);

        terminalA.registrarNaviera(naviera);
		Circuito mejor = terminalA.mejorCircuito(terminalC);

        assertEquals(circuito1, mejor);
	}
	
	@Test 
	public void proximaFechaDePartida() {
		Naviera naviera = new Naviera();
        naviera.agregarCircuito(circuito1);
        naviera.agregarCircuito(circuito2);
        naviera.agregarViaje(viaje);

        terminalA.registrarNaviera(naviera);
		 LocalDate fecha = terminalA.proximaFechaDePartida(buque, terminalC);
	        assertEquals(viaje.getCronograma().get(terminalC), fecha);
	}
	
	@Test
    public void testTerminalDelegaLaAplicacionDeServicioAlWarehouse() {
        // 1. SETUP LOCAL
        Warehouse warehouseMock = Mockito.mock(Warehouse.class);
        Logistica logisticaMock = Mockito.mock(Logistica.class);
        GestionTerrestre gestionTerrestreMock = Mockito.mock(GestionTerrestre.class);
        Ubicacion ubicacionMock = Mockito.mock(Ubicacion.class);
        
        Carga cargaMock = Mockito.mock(Carga.class);
        IServicio servicioMock = Mockito.mock(IServicio.class);
        

        TerminalGestionada terminal = new TerminalGestionada(ubicacionMock, gestionTerrestreMock, logisticaMock, warehouseMock);
        
        when(warehouseMock.contieneCarga(cargaMock)).thenReturn(true);

        terminal.solicitarServicio(servicioMock, cargaMock);

        verify(warehouseMock, times(1)).aplicarServicio(servicioMock, cargaMock);
    }
	
	@Test
	public void testTerminalNoDelegaSiWarehouseNoTieneLaCarga() {
		// 1. SETUP LOCAL
        Warehouse warehouseMock = Mockito.mock(Warehouse.class);
        Logistica logisticaMock = Mockito.mock(Logistica.class);
        GestionTerrestre gestionTerrestreMock = Mockito.mock(GestionTerrestre.class);
        Ubicacion ubicacionMock = Mockito.mock(Ubicacion.class);
        
        Carga cargaMock = Mockito.mock(Carga.class);
        IServicio servicioMock = Mockito.mock(IServicio.class);
        
        // Creamos la Terminal con los mocks
        TerminalGestionada terminal = new TerminalGestionada(ubicacionMock, gestionTerrestreMock, logisticaMock, warehouseMock);
        
	    when(warehouseMock.contieneCarga(cargaMock)).thenReturn(false);

	    assertThrows(IllegalArgumentException.class, () -> {
	        terminal.solicitarServicio(servicioMock, cargaMock);
	    });


	    verify(warehouseMock, never()).aplicarServicio(servicioMock, cargaMock);
	}
	
	@Test
    void TiempoDeNavieraHasta() {
		Naviera naviera = new Naviera();
        naviera.agregarCircuito(circuito1);
        naviera.agregarCircuito(circuito2);
        naviera.agregarViaje(viaje);
        int tiempo = terminalA.tiempoDeNavieraHasta(naviera, terminalC);
        assertEquals(8, tiempo);
    }
}
