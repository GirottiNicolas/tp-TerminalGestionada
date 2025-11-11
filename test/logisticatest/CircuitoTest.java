package logisticatest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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

    @BeforeEach
    public void setUp() {
    	terminalA = Mockito.mock(TerminalGestionada.class);
    	terminalB = Mockito.mock(TerminalGestionada.class);
    	terminalC = Mockito.mock(TerminalGestionada.class);
    	tramoAB = new Tramo(terminalA, terminalB, 100, 2);
    	tramoBC = new Tramo(terminalB, terminalC, 50.2f, 6);
    	tramoCA = new Tramo(terminalC, terminalA, 48, 10);
        circuito = new Circuito(List.of(tramoAB, tramoBC,tramoCA));
    }

    @Test
    public void testGetTramos() {
        List<Tramo> tramos = circuito.getTramos();
        assertEquals(3, tramos.size());
        assertEquals(tramoAB, tramos.get(0));
        assertEquals(tramoBC, tramos.get(1));
        assertEquals(tramoCA, tramos.get(2));
    }

    @Test
    public void testGetTerminalOrigen() {
        assertEquals(terminalA, circuito.getTerminalOrigen());
    }

    @Test
    public void testGetTerminalDestino() {
        assertEquals(terminalC, circuito.getTerminalDestino());
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
