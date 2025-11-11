package warehousetest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import gestion.terrestre.Ubicacion;
import terminalgestionada.TerminalGestionada;
import warehouse.Arrived;
import warehouse.Buque;
import warehouse.Departing;
import warehouse.Inbound;
import warehouse.Outbound;
import warehouse.Working;

public class BuqueTest {

	private Buque buque;
	private TerminalGestionada terminal;
	private Ubicacion posicionTerminal;

	@BeforeEach
    public void setUp() {
        // 1. Definimos la posición de la terminal
        // (Usamos 1.0 como métrica para KM)
        this.posicionTerminal = new Ubicacion(0, 0); 
        
        // 2. Creamos el Mock de la terminal
        this.terminal = Mockito.mock(TerminalGestionada.class);
        
        // 3. Programamos el Mock (Stubbing)
        // Le decimos al mock que responda con la posición de la terminal
        Mockito.when(terminal.getPosicionGeografica()).thenReturn(posicionTerminal);
        
        // 4. Creamos el Buque (empieza lejos, a 100km)
        Ubicacion posicionInicialLejana = new Ubicacion(100, 0); 
        this.buque = new Buque(posicionInicialLejana, terminal);
	}

    @Test
    public void test01_UnBuqueNuevoSeCreaEnFaseOutbound() {
        // Verificamos que la fase inicial del buque es Outbound
        assertEquals(Outbound.class, buque.getFase().getClass());
    }
    
    @Test
    public void test02_UnBuqueEnOutboundPasaAInboundSiEstaAMenosDe50Km() {
        Ubicacion posicionLejana = new Ubicacion(60, 0); 
        buque.actualizarPosicion(posicionLejana);

   
        assertEquals(Outbound.class, buque.getFase().getClass());

   
        Ubicacion posicionCercana = new Ubicacion(49, 0); 
        buque.actualizarPosicion(posicionCercana);

        // Verificamos que cambió a Inbound
        assertEquals(Inbound.class, buque.getFase().getClass());
    }
    
    @Test
    public void test03_UnBuqueEnInboundPasaAArrivedSiSusCoordenadasCoincidenConLaTerminal() {
        // 1. Setup: Forzamos el estado a Inbound (como en el test02)
        Ubicacion posicionCercana = new Ubicacion(49, 0);
        buque.actualizarPosicion(posicionCercana);
        assertEquals(Inbound.class, buque.getFase().getClass()); // Verificamos que está en Inbound

        // 2. Ejecución: El buque sigue avanzando y actualiza su posición
        // Ahora está un poco más cerca, pero sin llegar
        buque.actualizarPosicion(new Ubicacion(10, 0));
        assertEquals(Inbound.class, buque.getFase().getClass()); // Sigue en Inbound

        // 3. Ejecución: El buque llega al puerto
        // Le pasamos la posición exacta de la terminal (que definimos en el setUp)
        buque.actualizarPosicion(this.posicionTerminal);

        // 4. Verificación: El estado debe cambiar a Arrived
        assertEquals(Arrived.class, buque.getFase().getClass());
    }
    
    @Test
    public void test04_UnBuqueEnArrivedPasaAWorkingCuandoRecibeLaOrden() {
        // 1. SETUP: Llevamos el buque a Arrived
        buque.actualizarPosicion(new Ubicacion(49, 0)); // Pasa a Inbound
        buque.actualizarPosicion(this.posicionTerminal);     // Pasa a Arrived
        assertEquals(Arrived.class, buque.getFase().getClass()); // Verificamos

        // 2. EJECUCIÓN: Damos la orden de inicio de trabajo
        buque.iniciarTrabajo();

        // 3. VERIFICACIÓN: El estado debe cambiar a Working
        assertEquals(Working.class, buque.getFase().getClass());
    }

    @Test
    public void test05_UnBuqueEnInboundNoPuedePasarAWorking() {
        // 1. SETUP: Llevamos el buque a Inbound
        buque.actualizarPosicion(new Ubicacion(49, 0));
        assertEquals(Inbound.class, buque.getFase().getClass()); // Verificamos

        // 2. EJECUCIÓN: Intentamos dar la orden
        buque.iniciarTrabajo();

        // 3. VERIFICACIÓN: El estado NO debe cambiar
        assertEquals(Inbound.class, buque.getFase().getClass());
    }
    
    @Test
    public void test06_UnBuqueEnWorkingPasaADepartingCuandoRecibeLaOrden() {
        // 1. SETUP: Llevamos el buque a Working
        buque.actualizarPosicion(new Ubicacion(49, 0)); // Inbound
        buque.actualizarPosicion(this.posicionTerminal);     // Arrived
        buque.iniciarTrabajo();                              // Working
        assertEquals(Working.class, buque.getFase().getClass()); // Verificamos

        // 2. EJECUCIÓN: Damos la orden de "depart"
        buque.depart();

        // 3. VERIFICACIÓN: El estado debe cambiar a Departing
        assertEquals(Departing.class, buque.getFase().getClass());
    }

    @Test
    public void test07_UnBuqueEnArrivedNoPuedePasarADepartingDirectamente() {
        // 1. SETUP: Llevamos el buque a Arrived
        buque.actualizarPosicion(new Ubicacion(49, 0)); // Inbound
        buque.actualizarPosicion(this.posicionTerminal);     // Arrived
        assertEquals(Arrived.class, buque.getFase().getClass()); // Verificamos

        
        // 2. EJECUCIÓN: Intentamos dar la orden "depart"
        buque.depart();

        // 3. VERIFICACIÓN: El estado NO debe cambiar
        assertEquals(Arrived.class, buque.getFase().getClass());
    }
    
    @Test
    public void test08_UnBuqueEnDepartingPasaAOutboundSiEstaAMasDe1Km() {
        // 1. SETUP: Llevamos el buque a Departing
        buque.actualizarPosicion(new Ubicacion(49, 0)); // Inbound
        buque.actualizarPosicion(this.posicionTerminal);     // Arrived
        buque.iniciarTrabajo();                              // Working
        buque.depart();                                      // Departing
        assertEquals(Departing.class, buque.getFase().getClass()); // Verificamos
        buque.actualizarPosicion(new Ubicacion(1, 0));
        assertEquals(Departing.class, buque.getFase().getClass()); // Sigue en Departing

        // 3. EJECUCIÓN: El buque se aleja a 2km
        buque.actualizarPosicion(new Ubicacion(2, 0)); // 2km de distancia

        // 4. VERIFICACIÓN: El estado debe volver a Outbound
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
        // 1. SETUP: Llevamos el buque a Departing
        buque.actualizarPosicion(new Ubicacion(49, 0)); // Inbound
        buque.actualizarPosicion(this.posicionTerminal);     // Arrived
        buque.iniciarTrabajo();                              // Working
        buque.depart();                                      // Departing
        assertEquals(Departing.class, buque.getFase().getClass());

        // 2. EJECUCIÓN: El buque se aleja a > 1km
        buque.actualizarPosicion(new Ubicacion(2, 0));
        assertEquals(Outbound.class, buque.getFase().getClass()); // Verificamos

        // 3. VERIFICACIÓN (¡NUEVO!):
        // Verificamos que se llamó al método 'notificarPartida' 
        Mockito.verify(terminal, Mockito.times(1)).notificarPartida(buque);
    }
    
}
