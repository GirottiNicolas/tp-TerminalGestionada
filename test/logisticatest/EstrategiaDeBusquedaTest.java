package logisticatest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import logistica.Circuito;
import logistica.EstrategiaCircuitoCorto;
import logistica.EstrategiaDeBusqueda;
import logistica.EstrategiaMenorTiempo;
import logistica.EstrategiaPrecioMasBajo;
import logistica.Tramo;
import terminalgestionada.TerminalGestionada;

public class EstrategiaDeBusquedaTest {
	private TerminalGestionada terminalA;
	private TerminalGestionada terminalB;
	private TerminalGestionada terminalC;
	private Tramo tramoAB;
	private Tramo tramoBC;
	private Tramo tramoCA;
	private Tramo tramoAC;
	private Tramo tramoAB2;
	private Circuito circuitoRapido;
	private Circuito circuitoBarato;
	private Circuito circuitoCorto;
	private List<Circuito> circuitos;
	
	@BeforeEach
	public void setUp() {
		terminalA = Mockito.mock(TerminalGestionada.class);
		terminalB = Mockito.mock(TerminalGestionada.class);
		terminalC = Mockito.mock(TerminalGestionada.class);
		Mockito.when(terminalA.esLaTerminal(terminalA)).thenReturn(true);
		Mockito.when(terminalB.esLaTerminal(terminalB)).thenReturn(true);
		Mockito.when(terminalC.esLaTerminal(terminalC)).thenReturn(true);
		tramoAB = new Tramo(terminalA,terminalB, 50,3);
		tramoBC = new Tramo(terminalB,terminalC, 84,2);
		tramoCA = new Tramo(terminalC,terminalA, 180,7);
		tramoAC = new Tramo(terminalA,terminalC, 180,7);
		tramoAB2 = new Tramo(terminalA,terminalB, 70,1);
		circuitoRapido = new Circuito(List.of(tramoAB2,tramoBC,tramoCA));
		circuitoBarato = new Circuito(List.of(tramoAB,tramoBC,tramoCA));
		circuitoCorto = new Circuito(List.of(tramoAC,tramoCA));
		circuitos = List.of(circuitoRapido,circuitoBarato,circuitoCorto);
	}
	
	@Test
	public void testEstrategiaMenorTiempo() {
	    EstrategiaDeBusqueda estrategia = new EstrategiaMenorTiempo();
	    Circuito mejor = estrategia.seleccionarMejorCircuito(circuitos, terminalC);
	    assertEquals(circuitoRapido.getTramos(), mejor.getTramos());
	}
	
	@Test
	public void testEstrategiaPrecioMasBajo() {
	    EstrategiaDeBusqueda estrategia = new EstrategiaPrecioMasBajo();
	    Circuito mejor = estrategia.seleccionarMejorCircuito(circuitos, terminalC);
	    assertEquals(circuitoBarato.getTramos(), mejor.getTramos());
	}
	
	@Test
	public void testEstrategiaCircuitoCorto() {
	    EstrategiaDeBusqueda estrategia = new EstrategiaCircuitoCorto();
	    Circuito mejor = estrategia.seleccionarMejorCircuito(circuitos, terminalC);
	    assertEquals(circuitoCorto.getTramos(), mejor.getTramos());
	}
}
