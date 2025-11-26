package logisticatest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import logistica.Circuito;
import logistica.EstrategiaCircuitoCorto;
import logistica.EstrategiaMenorTiempo;
import logistica.EstrategiaPrecioMasBajo;
import logistica.Tramo;
import terminalgestionada.TerminalGestionada;

public class EstrategiaDeBusquedaTest {

    private final TerminalGestionada origen = mock(TerminalGestionada.class);
    private final TerminalGestionada destino = mock(TerminalGestionada.class);

    @Test
    void testEstrategiaCircuitoCorto() {
        Circuito circuitoMock = mock(Circuito.class);
        Tramo tramoOD = mock(Tramo.class);

        when(tramoOD.getOrigen()).thenReturn(origen);
        when(tramoOD.getDestino()).thenReturn(destino);
        when(circuitoMock.contieneTerminal(origen)).thenReturn(true);
        when(circuitoMock.contieneTerminal(destino)).thenReturn(true);

        when(circuitoMock.getTerminales()).thenReturn(List.of(origen, destino));
        when(circuitoMock.getTerminalOrigen()).thenReturn(origen);
        when(circuitoMock.rutaEntre(origen, destino)).thenReturn(List.of(tramoOD));

        EstrategiaCircuitoCorto estrategia = new EstrategiaCircuitoCorto();
        Circuito resultado = estrategia.seleccionarMejorCircuito(List.of(circuitoMock), origen, destino);

        assertEquals(circuitoMock, resultado);
    }

    @Test
    void testEstrategiaCircuitoCortoConComparacionDeRutas() {
        Circuito c1 = mock(Circuito.class);
        Circuito c2 = mock(Circuito.class);

        Tramo t1 = mock(Tramo.class);
        Tramo t2a = mock(Tramo.class);
        Tramo t2b = mock(Tramo.class);

        when(c1.getTerminales()).thenReturn(List.of(origen, destino));
        when(c1.getTerminalOrigen()).thenReturn(origen);
        when(c1.rutaEntre(origen, destino)).thenReturn(List.of(t1));
        when(c1.contieneTerminal(origen)).thenReturn(true);
        when(c1.contieneTerminal(destino)).thenReturn(true);
        when(c2.contieneTerminal(origen)).thenReturn(true);
        when(c2.contieneTerminal(destino)).thenReturn(true);

        when(c2.getTerminales()).thenReturn(List.of(origen, destino));
        when(c2.getTerminalOrigen()).thenReturn(origen);
        when(c2.rutaEntre(origen, destino)).thenReturn(List.of(t2a, t2b));

        EstrategiaCircuitoCorto estrategia = new EstrategiaCircuitoCorto();
        Circuito resultado = estrategia.seleccionarMejorCircuito(List.of(c1, c2), origen, destino);

        assertEquals(c1, resultado);
    }

    @Test
    void testEstrategiaMenorTiempo() {
        Circuito circuitoMock = mock(Circuito.class);

        when(circuitoMock.getTerminales()).thenReturn(List.of(origen, destino));
        when(circuitoMock.getTerminalOrigen()).thenReturn(origen);
        when(circuitoMock.tiempoTotalEntre(origen, destino)).thenReturn(5);
        when(circuitoMock.contieneTerminal(origen)).thenReturn(true);
        when(circuitoMock.contieneTerminal(destino)).thenReturn(true);
        
        EstrategiaMenorTiempo estrategia = new EstrategiaMenorTiempo();
        Circuito resultado = estrategia.seleccionarMejorCircuito(List.of(circuitoMock), origen, destino);

        assertEquals(circuitoMock, resultado);
    }

    @Test
    void testEstrategiaMenorTiempoComparandoDosCircuitos() {
        Circuito c1 = mock(Circuito.class);
        Circuito c2 = mock(Circuito.class);

        when(c1.getTerminales()).thenReturn(List.of(origen, destino));
        when(c1.getTerminalOrigen()).thenReturn(origen);
        when(c1.tiempoTotalEntre(origen, destino)).thenReturn(10);
        when(c1.contieneTerminal(origen)).thenReturn(true);
        when(c1.contieneTerminal(destino)).thenReturn(true);
        when(c2.contieneTerminal(origen)).thenReturn(true);
        when(c2.contieneTerminal(destino)).thenReturn(true);

        when(c2.getTerminales()).thenReturn(List.of(origen, destino));
        when(c2.getTerminalOrigen()).thenReturn(origen);
        when(c2.tiempoTotalEntre(origen, destino)).thenReturn(6);

        EstrategiaMenorTiempo estrategia = new EstrategiaMenorTiempo();
        Circuito resultado = estrategia.seleccionarMejorCircuito(List.of(c1, c2), origen, destino);

        assertEquals(c2, resultado);
    }

    @Test
    void testEstrategiaPrecioMasBajo() {
        Circuito circuitoMock = mock(Circuito.class);

        when(circuitoMock.getTerminales()).thenReturn(List.of(origen, destino));
        when(circuitoMock.getTerminalOrigen()).thenReturn(origen);
        when(circuitoMock.precioTotalEntre(origen, destino)).thenReturn(100.0);
        when(circuitoMock.contieneTerminal(origen)).thenReturn(true);
        when(circuitoMock.contieneTerminal(destino)).thenReturn(true);

        EstrategiaPrecioMasBajo estrategia = new EstrategiaPrecioMasBajo();
        Circuito resultado = estrategia.seleccionarMejorCircuito(List.of(circuitoMock), origen, destino);

        assertEquals(circuitoMock, resultado);
    }

    @Test
    void testEstrategiaPrecioMasBajoComparandoDosCircuitos() {
        Circuito c1 = mock(Circuito.class);
        Circuito c2 = mock(Circuito.class);

        when(c1.getTerminales()).thenReturn(List.of(origen, destino));
        when(c1.getTerminalOrigen()).thenReturn(origen);
        when(c1.precioTotalEntre(origen, destino)).thenReturn(250.0);
        when(c1.contieneTerminal(origen)).thenReturn(true);
        when(c1.contieneTerminal(destino)).thenReturn(true);
        when(c2.contieneTerminal(origen)).thenReturn(true);
        when(c2.contieneTerminal(destino)).thenReturn(true);

        when(c2.getTerminales()).thenReturn(List.of(origen, destino));
        when(c2.getTerminalOrigen()).thenReturn(origen);
        when(c2.precioTotalEntre(origen, destino)).thenReturn(180.0);

        EstrategiaPrecioMasBajo estrategia = new EstrategiaPrecioMasBajo();
        Circuito resultado = estrategia.seleccionarMejorCircuito(List.of(c1, c2), origen, destino);

        assertEquals(c2, resultado);
    }

    @Test
    void testEstrategiaSinCircuitos() {
        EstrategiaCircuitoCorto estrategia = new EstrategiaCircuitoCorto();
        assertThrows(IllegalArgumentException.class,
            () -> estrategia.seleccionarMejorCircuito(List.of(), origen, destino));
    }
}