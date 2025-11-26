package logisticatest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import logistica.Circuito;
import logistica.Naviera;
import logistica.Tramo;
import logistica.Viaje;
import terminalgestionada.TerminalGestionada;
import warehouse.Buque;

public class NavieraTest {
	private Naviera naviera;
    private TerminalGestionada terminalA;
    private TerminalGestionada terminalB;
    private TerminalGestionada terminalC;

    private Tramo tramoAB;
    private Tramo tramoBC;
    private Tramo tramoCA;

    private Circuito circuito;
    private Buque buque;
    private LocalDate fechaPartida;
    private Viaje viaje;
    
    @BeforeEach
    public void setUp() {
        naviera = new Naviera();

        terminalA = Mockito.mock(TerminalGestionada.class);
        terminalB = Mockito.mock(TerminalGestionada.class);
        terminalC = Mockito.mock(TerminalGestionada.class);
        Mockito.when(terminalA.esLaTerminal(terminalA)).thenReturn(true);
        tramoAB = new Tramo(terminalA, terminalB, 100, 2);
        tramoBC = new Tramo(terminalB, terminalC, 50.2f, 6);
        tramoCA = new Tramo(terminalC, terminalA, 48, 10);

        circuito = new Circuito(List.of(tramoAB, tramoBC, tramoCA));
        buque = new Buque(null, terminalA, null);
        fechaPartida = LocalDate.of(2025, 11, 1);

        naviera.agregarCircuito(circuito);
        viaje = new Viaje(buque, circuito, fechaPartida);
        naviera.agregarViaje(viaje);
    }
    
    @Test
    public void testAgregarCircuito() {
        assertTrue(naviera.getCircuitos().contains(circuito));
    }

    @Test
    public void testAgregarViajeConCircuitoValido() {
        assertTrue(naviera.getViajes().contains(viaje));
    }

    @Test
    public void testAgregarViajeConCircuitoInvalidoDebeFallar() {
        Tramo tramoBA = new Tramo(terminalB, terminalA, 100, 2);
		Circuito otroCircuito = new Circuito(List.of(tramoAB,tramoBA));
        Viaje viajeInvalido = new Viaje(buque, otroCircuito, fechaPartida);
        assertThrows(IllegalArgumentException.class, () -> naviera.agregarViaje(viajeInvalido));
    }

    @Test
    public void testPublicarViaje() {
        naviera.publicarViaje(circuito, buque, fechaPartida.plusDays(1));
        assertEquals(2, naviera.getViajes().size());
    }

    @Test
    public void testViajesPorCircuito() {
        List<Viaje> viajes = naviera.viajesPorCircuito(circuito);
        assertEquals(1, viajes.size());
    }

    @Test
    public void testViajesDesdeTerminal() {
        List<Viaje> viajes = naviera.viajesDesde(terminalA);
        assertEquals(1, viajes.size());
    }

    @Test
    public void testTerminalesVisitadas() {
        List<TerminalGestionada> terminales = naviera.terminalesVisitadas();
        assertEquals(List.of(terminalA, terminalB, terminalC), terminales);
    }

    @Test
    public void testFechaDeViajeA() {
        LocalDate fechaEsperada = fechaPartida.plusDays(8); // A→B→C (2+6)
        assertEquals(fechaEsperada, naviera.fechaDeViajeA(terminalC));
    }

    @Test
    public void testFechaDeViajeADestinoInexistenteDebeFallar() {
        TerminalGestionada terminalX = Mockito.mock(TerminalGestionada.class);
        assertThrows(IllegalArgumentException.class, () -> naviera.fechaDeViajeA(terminalX));
    }

    public void testTiempoDeRecorrido() {
        Mockito.when(circuito.getTerminales()).thenReturn(List.of(terminalA, terminalC));
        // El circuito real tiene: A->B (2 días) y B->C (6 días). Total A->C = 8 días.

        int tiempo = naviera.tiempoDeRecorrido(terminalA, terminalC);
        
        assertEquals(8, tiempo);
    }
    
    @Test
    public void testTiempoDeRecorridoFallaSiNoHayConexion() {
        TerminalGestionada terminalDesconectada = Mockito.mock(TerminalGestionada.class);
        
        assertThrows(IllegalArgumentException.class, () -> {
            naviera.tiempoDeRecorrido(terminalA, terminalDesconectada);
        });
    }
    
}

