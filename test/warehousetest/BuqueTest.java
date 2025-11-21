package warehousetest;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import gestion.terrestre.Ubicacion;
import terminalgestionada.TerminalGestionada;
import warehouse.Arrived;
import warehouse.Buque;
import warehouse.Carga;
import warehouse.Departing;
import warehouse.Inbound;
import warehouse.Outbound;
import warehouse.Working;
import warehouse.IVisitorReporte;

public class BuqueTest {

	private Buque buque;
	private TerminalGestionada terminal;
	private Ubicacion posicionTerminal;

	@BeforeEach
    public void setUp() {
        this.posicionTerminal = new Ubicacion(0, 0); 
        
        this.terminal = Mockito.mock(TerminalGestionada.class);
        
        Mockito.when(terminal.getPosicionGeografica()).thenReturn(posicionTerminal);
        
        Ubicacion posicionInicialLejana = new Ubicacion(100, 0); 
        this.buque = new Buque(posicionInicialLejana, terminal, "BarcoDePrueba");
	}

    @Test
    public void test01_UnBuqueNuevoSeCreaEnFaseOutbound() {
        assertEquals(Outbound.class, buque.getFase().getClass());
    }
    
    @Test
    public void test02_UnBuqueEnOutboundPasaAInboundSiEstaAMenosDe50Km() {
        Ubicacion posicionLejana = new Ubicacion(60, 0); 
        buque.actualizarPosicion(posicionLejana);
        assertEquals(Outbound.class, buque.getFase().getClass());
   
        Ubicacion posicionCercana = new Ubicacion(49, 0); 
        buque.actualizarPosicion(posicionCercana);

        assertEquals(Inbound.class, buque.getFase().getClass());
    }
    
    @Test
    public void test03_UnBuqueEnInboundPasaAArrivedSiSusCoordenadasCoincidenConLaTerminal() {
        Ubicacion posicionCercana = new Ubicacion(49, 0);
        buque.actualizarPosicion(posicionCercana);
        assertEquals(Inbound.class, buque.getFase().getClass()); // Verificamos que está en Inbound

        buque.actualizarPosicion(new Ubicacion(10, 0));
        assertEquals(Inbound.class, buque.getFase().getClass()); // Sigue en Inbound

        buque.actualizarPosicion(this.posicionTerminal);

        assertEquals(Arrived.class, buque.getFase().getClass());
    }
    
    @Test
    public void test04_UnBuqueEnArrivedPasaAWorkingCuandoRecibeLaOrden() {
        buque.actualizarPosicion(new Ubicacion(49, 0)); // Pasa a Inbound
        buque.actualizarPosicion(this.posicionTerminal);     // Pasa a Arrived
        assertEquals(Arrived.class, buque.getFase().getClass()); // Verificamos

        buque.iniciarTrabajo();

        assertEquals(Working.class, buque.getFase().getClass());
    }

    @Test
    public void test05_UnBuqueEnInboundNoPuedePasarAWorking() {
        buque.actualizarPosicion(new Ubicacion(49, 0));
        assertEquals(Inbound.class, buque.getFase().getClass()); // Verificamos

        buque.iniciarTrabajo();

        assertEquals(Inbound.class, buque.getFase().getClass());
    }
    
    @Test
    public void test06_UnBuqueEnWorkingPasaADepartingCuandoRecibeLaOrden() {
        buque.actualizarPosicion(new Ubicacion(49, 0)); // Inbound
        buque.actualizarPosicion(this.posicionTerminal);     // Arrived
        buque.iniciarTrabajo();                              // Working
        assertEquals(Working.class, buque.getFase().getClass()); // Verificamos
        buque.depart();

        assertEquals(Departing.class, buque.getFase().getClass());
    }

    @Test
    public void test07_UnBuqueEnArrivedNoPuedePasarADepartingDirectamente() {
        buque.actualizarPosicion(new Ubicacion(49, 0)); // Inbound
        buque.actualizarPosicion(this.posicionTerminal);     // Arrived
        assertEquals(Arrived.class, buque.getFase().getClass()); // Verificamos

        buque.depart();

        assertEquals(Arrived.class, buque.getFase().getClass());
    }
    
    @Test
    public void test08_UnBuqueEnDepartingPasaAOutboundSiEstaAMasDe1Km() {
        buque.actualizarPosicion(new Ubicacion(49, 0)); // Inbound
        buque.actualizarPosicion(this.posicionTerminal);     // Arrived
        buque.iniciarTrabajo();                              // Working
        buque.depart();                                      // Departing
        assertEquals(Departing.class, buque.getFase().getClass()); 
        buque.actualizarPosicion(new Ubicacion(1, 0));
        assertEquals(Departing.class, buque.getFase().getClass()); 

        buque.actualizarPosicion(new Ubicacion(2, 0)); // 2km de distancia

        assertEquals(Outbound.class, buque.getFase().getClass());
    }
    
    @Test
    public void test09_BuqueNotificaALaTerminalCuandoEntraEnInbound() {
        assertEquals(Outbound.class, buque.getFase().getClass());
        buque.actualizarPosicion(new Ubicacion(49, 0));
        assertEquals(Inbound.class, buque.getFase().getClass()); 

        Mockito.verify(terminal, Mockito.times(1)).notificarArriboInminente(buque);
    }
	
    @Test
    public void test10_BuqueNotificaALaTerminalCuandoPasaDeDepartingAOutbound() {
        buque.actualizarPosicion(new Ubicacion(49, 0)); // Inbound
        buque.actualizarPosicion(this.posicionTerminal);     // Arrived
        buque.iniciarTrabajo();                              // Working
        buque.depart();                                      // Departing
        assertEquals(Departing.class, buque.getFase().getClass());
        buque.actualizarPosicion(new Ubicacion(2, 0));
        assertEquals(Outbound.class, buque.getFase().getClass()); 

        Mockito.verify(terminal, Mockito.times(1)).notificarPartida(buque);
    }
    
    @Test
    public void test11_BuquePuedeAceptarUnVisitor() {
        IVisitorReporte visitorMock = Mockito.mock(IVisitorReporte.class);
        buque.accept(visitorMock);
        
        Mockito.verify(visitorMock, Mockito.times(1)).visitBuque(buque);
    }
    
    @Test
    public void test12_BuqueAlmacenaSusCargasYFechasParaReportes() {
        Carga cargaImp = Mockito.mock(Carga.class);
        Carga cargaExp = Mockito.mock(Carga.class);
        LocalDateTime fechaTest = LocalDateTime.now();

        buque.setFechaArribo(fechaTest);
        buque.setFechaPartida(fechaTest);
        buque.registrarCargaImportacion(cargaImp); 
        buque.registrarCargaExportacion(cargaExp); 

        assertEquals(fechaTest, buque.getFechaArribo());
        assertEquals(fechaTest, buque.getFechaPartida());
        assertTrue(buque.getContainersDescargados().contains(cargaImp));
        assertTrue(buque.getContainersCargados().contains(cargaExp));
    }
    
    @Test
    public void test13_UnBuqueTieneUnNombre() {
        assertEquals("BarcoDePrueba", buque.getNombre());
    }
    
}
