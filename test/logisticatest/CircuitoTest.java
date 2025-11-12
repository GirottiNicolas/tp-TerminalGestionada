package logisticatest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import gestion.terrestre.Ubicacion;
import logistica.Circuito;
import logistica.Tramo;
import terminalgestionada.TerminalGestionada;

public class CircuitoTest {

    private TerminalGestionada terminalA;
    private TerminalGestionada terminalB;
    private TerminalGestionada terminalC;
    private Tramo tramoAB;
    private Tramo tramoBC;
    private Tramo tramoCA;
    private Circuito circuito;
    private Ubicacion posicionA;
    private Ubicacion posicionB;
    private Ubicacion posicionC;

    @BeforeEach
    public void setUp() {
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
    }

    @Test
    public void testGetTramos() {
        assertEquals(3, circuito.getTramos().size());
        assertEquals(List.of(tramoAB, tramoBC,tramoCA), circuito.getTramos());
    }

    @Test
    public void testGetTerminalOrigen() {
        assertTrue(circuito.getTerminalOrigen().esLaTerminal(terminalA));
    }

    @Test
    public void testGetTerminalDestino() {
        assertTrue(circuito.getTerminalDestino().esLaTerminal(terminalC));
    }

    @Test
    public void testGetTerminales() {
        List<TerminalGestionada> terminales = circuito.getTerminales();
        assertEquals(List.of(terminalA, terminalB, terminalC), terminales);
    }

    @Test
    public void testRutaEntreTerminales() {
        List<Tramo> ruta = circuito.rutaEntre(terminalA, terminalC);
        assertEquals(List.of(tramoAB, tramoBC), ruta);
    }

    @Test
    public void testRutaEntreTerminalesParcial() {
        List<Tramo> ruta = circuito.rutaEntre(terminalA, terminalB);
        assertEquals(List.of(tramoAB), ruta);
    }

    @Test
    public void testTiempoTotalEntreTerminales() {
        int tiempo = circuito.tiempoTotalEntre(terminalA, terminalC);
        assertEquals(8, tiempo);
    }

    @Test
    public void testPrecioTotalEntreTerminales() {
        double precio = circuito.precioTotalEntre(terminalA, terminalC);
        assertEquals(150.2f, precio, 0.01);
    }

    @Test
    public void testTiempoYPrecioParcial() {
        assertEquals(2, circuito.tiempoTotalEntre(terminalA, terminalB));
        assertEquals(100, circuito.precioTotalEntre(terminalA, terminalB));
    }
}
