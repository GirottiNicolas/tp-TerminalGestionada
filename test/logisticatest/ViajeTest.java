package logisticatest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import gestion.terrestre.Ubicacion;
import logistica.Circuito;
import logistica.Tramo;
import logistica.Viaje;
import terminalgestionada.TerminalGestionada;
import warehouse.Buque;

public class ViajeTest {
	private Ubicacion ubiBuque;
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
    	ubiBuque = new Ubicacion (1, 2);
    	terminalA = Mockito.mock(TerminalGestionada.class);
    	terminalB = Mockito.mock(TerminalGestionada.class);
    	terminalC = Mockito.mock(TerminalGestionada.class);
    	Mockito.when(terminalA.esLaTerminal(terminalA)).thenReturn(true);
    	tramoAB = new Tramo(terminalA, terminalB, 10, 2);
    	tramoBC = new Tramo(terminalB, terminalC, 60, 3);
    	tramoCA = new Tramo(terminalC, terminalA, 90, 7);
    	circuito = new Circuito(List.of(tramoAB, tramoBC, tramoCA));
    	buque = new Buque(ubiBuque, null, null);
    	fechaPartida = LocalDate.of(2025, 9, 12);
    	viaje = new Viaje(buque, circuito, fechaPartida);
    }
    
    @Test
    public void testGetBuque() {
        assertTrue(viaje.getBuque().esElBuque(buque));
    }

    @Test
    public void testGetFechaPartida() {
        assertEquals(fechaPartida, viaje.getFechaPartida());
    }

    @Test
    public void testGetOrigenViaje() {
        assertTrue(viaje.getOrigenViaje().esLaTerminal(terminalA));
    }

    @Test
    public void testGetCircuito() {
        assertEquals(circuito.getTramos(), viaje.getCircuito().getTramos());
    }

    @Test
    public void testCronogramaCompleto() {
        Map<TerminalGestionada, LocalDate> esperado = new LinkedHashMap<>();
        esperado.put(terminalB, fechaPartida.plusDays(2));  // tramoAB
        esperado.put(terminalC, fechaPartida.plusDays(5));  // tramoBC
        esperado.put(terminalA, fechaPartida.plusDays(12)); // tramoCA

        assertEquals(esperado, viaje.getCronograma());
    }

    @Test
    public void testOrdenCronograma() {
        List<TerminalGestionada> orden = new ArrayList<>(viaje.getCronograma().keySet());
        assertEquals(List.of(terminalB, terminalC, terminalA), orden);
    }
}

