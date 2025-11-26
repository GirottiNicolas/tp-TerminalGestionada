package logisticatest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logistica.Circuito;
import logistica.EstrategiaCircuitoCorto;
import logistica.Logistica;
import logistica.Naviera;
import logistica.Tramo;
import logistica.Viaje;
import terminalgestionada.TerminalGestionada;
import warehouse.Buque;

public class LogisticaTest {

    private Logistica logistica;
    private Naviera navieraMock;
    private Circuito circuitoMock;
    private Viaje viajeMock;
    private Buque buqueMock;
    private Tramo tramoMock;
    private TerminalGestionada terminalOrigen;
    private TerminalGestionada terminalDestino;

    @BeforeEach
    void setUp() {
        logistica = new Logistica(new EstrategiaCircuitoCorto());
        navieraMock = mock(Naviera.class);
        circuitoMock = mock(Circuito.class);
        viajeMock = mock(Viaje.class);
        buqueMock = mock(Buque.class);
        terminalOrigen = mock(TerminalGestionada.class);
        terminalDestino = mock(TerminalGestionada.class);
        tramoMock = mock(Tramo.class);
        when(tramoMock.getOrigen()).thenReturn(terminalOrigen);
        when(tramoMock.getDestino()).thenReturn(terminalDestino);
    }

    @Test
    void testRegistrarNaviera() {
        logistica.registrarNaviera(navieraMock);
        assertTrue(logistica.getNavieras().contains(navieraMock));
    }

    @Test
    void testCircuitosQueIncluyenTerminal() {
        when(navieraMock.getCircuitos()).thenReturn(List.of(circuitoMock));
        when(circuitoMock.contieneTerminal(terminalOrigen)).thenReturn(true);

        logistica.registrarNaviera(navieraMock);

        List<Circuito> resultado = logistica.getNavieras().stream()
                .flatMap(n -> n.getCircuitos().stream())
                .filter(c -> c.contieneTerminal(terminalOrigen))
                .toList();

        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(circuitoMock));
    }

    @Test
    void testMejorCircuitoConEstrategiaCircuitoCorto() {
        when(navieraMock.getCircuitos()).thenReturn(List.of(circuitoMock));
        when(circuitoMock.contieneTerminal(terminalOrigen)).thenReturn(true);
        when(circuitoMock.getTerminales()).thenReturn(List.of(terminalOrigen, terminalDestino));
        when(circuitoMock.getTerminalOrigen()).thenReturn(terminalOrigen);
        when(circuitoMock.rutaEntre(terminalOrigen, terminalDestino)).thenReturn(List.of(tramoMock));
        when(circuitoMock.contieneTerminal(terminalOrigen)).thenReturn(true);
        when(circuitoMock.contieneTerminal(terminalDestino)).thenReturn(true);

        logistica.registrarNaviera(navieraMock);

        Circuito mejor = logistica.mejorCircuito(terminalOrigen, terminalDestino);
        assertNotNull(mejor);
        assertEquals(circuitoMock, mejor);
    }
    
    @Test
    void testPrimeraFechaDeBuque() {
        LocalDate fechaActual = LocalDate.of(2023, 11, 1);
        LocalDate fechaDestino = LocalDate.of(2023, 11, 10);

        when(viajeMock.getBuque()).thenReturn(buqueMock);
        when(buqueMock.esElBuque(buqueMock)).thenReturn(true);
        when(viajeMock.getCronograma()).thenReturn(Map.of(
            terminalOrigen, LocalDate.of(2023, 11, 5),
            terminalDestino, fechaDestino
        ));

        when(navieraMock.getViajes()).thenReturn(List.of(viajeMock));
        logistica.registrarNaviera(navieraMock);

        LocalDate resultado = logistica.primeraFechaBuque(fechaActual, buqueMock, terminalOrigen, terminalDestino);

        assertEquals(fechaDestino, resultado);
    }

    @Test
    void testFechaMasProximaDeBuqueSinResultados() {
        LocalDate fechaActual = LocalDate.of(2023, 11, 1);

        when(viajeMock.getBuque()).thenReturn(buqueMock);
        when(buqueMock.esElBuque(buqueMock)).thenReturn(false);
        when(navieraMock.getViajes()).thenReturn(List.of(viajeMock));

        logistica.registrarNaviera(navieraMock);

        assertThrows(IllegalArgumentException.class,
            () -> logistica.primeraFechaBuque(fechaActual, buqueMock, terminalOrigen, terminalDestino));
    }
    
    @Test
    void testTiempoDeNavieraEntreConCircuitoValido() {
        when(circuitoMock.getTerminales()).thenReturn(List.of(terminalOrigen, terminalDestino));

        when(navieraMock.tiempoDeRecorrido(terminalOrigen, terminalDestino)).thenReturn(7);
        int tiempo = logistica.tiempoDeNavieraEntre(navieraMock, terminalOrigen, terminalDestino);

        assertEquals(7, tiempo);
        verify(navieraMock).tiempoDeRecorrido(terminalOrigen, terminalDestino);
    }


    @Test
    void testTiempoDeNavieraEntreSinCircuitosValidos() {
        when(navieraMock.tiempoDeRecorrido(terminalOrigen, terminalDestino))
            .thenThrow(new IllegalArgumentException("No hay circuitos..."));

        assertThrows(IllegalArgumentException.class,
            () -> logistica.tiempoDeNavieraEntre(navieraMock, terminalOrigen, terminalDestino));
    }
}



