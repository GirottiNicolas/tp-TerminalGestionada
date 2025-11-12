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
import logistica.EstrategiaDeBusqueda;
import logistica.Logistica;
import logistica.Naviera;
import logistica.Viaje;
import terminalgestionada.TerminalGestionada;
import warehouse.Buque;

public class LogisticaTest {

    private Logistica logistica;
    private EstrategiaDeBusqueda estrategiaMock;
    private Naviera navieraMock;
    private Circuito circuitoMock;
    private Viaje viajeMock;
    private Buque buqueMock;
    private TerminalGestionada terminalMock;
    private TerminalGestionada terminalMock2;

    @BeforeEach
    public void setUp() {
        estrategiaMock = mock(EstrategiaDeBusqueda.class);
        logistica = new Logistica(estrategiaMock);

        navieraMock = mock(Naviera.class);
        circuitoMock = mock(Circuito.class);
        viajeMock = mock(Viaje.class);
        buqueMock = mock(Buque.class);
        terminalMock = mock(TerminalGestionada.class);
        terminalMock2 = mock(TerminalGestionada.class);
    }
    
    @Test
    public void testRegistrarNaviera() {
        logistica.registrarNaviera(navieraMock);
        // Accedemos por reflexión o agregamos un getter en Logistica
        // Para simplificar, asumimos que hay un getter:
        assertTrue(logistica.getNavieras().contains(navieraMock));
    }
    
    @Test
    public void testMejorCircuitoUsaEstrategia() {
        logistica.registrarNaviera(navieraMock);
        when(navieraMock.getCircuitos()).thenReturn(List.of(circuitoMock));
        when(estrategiaMock.seleccionarMejorCircuito(anyList(), eq(terminalMock)))
            .thenReturn(circuitoMock);

        Circuito resultado = logistica.mejorCircuito(terminalMock);

        assertEquals(circuitoMock, resultado);
        verify(estrategiaMock).seleccionarMejorCircuito(anyList(), eq(terminalMock));
    }
    
    @Test
    public void testPrimeraFechaDeBuque() {
        logistica.registrarNaviera(navieraMock);

        LocalDate base = LocalDate.of(2025, 11, 1);
        LocalDate llegada1 = base.plusDays(3);
        LocalDate llegada2 = base.plusDays(5);

        when(navieraMock.getViajes()).thenReturn(List.of(viajeMock));
        when(viajeMock.getBuque()).thenReturn(buqueMock);
        when(buqueMock.esElBuque(buqueMock)).thenReturn(true);
        when(viajeMock.getCronograma()).thenReturn(
            Map.of(terminalMock, llegada1, terminalMock2, llegada2)
        );

        LocalDate resultado = logistica.primeraFechaDeBuque(base, buqueMock, terminalMock);
        assertEquals(llegada1, resultado);
    }
    
    @Test
    public void testPrimeraFechaDeBuqueSinViajes() {
        logistica.registrarNaviera(navieraMock);
        when(navieraMock.getViajes()).thenReturn(List.of());

        assertThrows(IllegalArgumentException.class,
            () -> logistica.primeraFechaDeBuque(LocalDate.now(), buqueMock, terminalMock));
    }



}
