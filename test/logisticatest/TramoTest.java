package logisticatest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gestion.terrestre.Ubicacion;
import logistica.Tramo;
import terminalgestionada.TerminalGestionada;

public class TramoTest {

	Ubicacion ubiOrigen;
	Ubicacion ubiDestino;
	TerminalGestionada terminalOrigen;
	TerminalGestionada terminalDestino;

	
	@BeforeEach
	public void setUp() {
		ubiOrigen = new Ubicacion(1,2);
		ubiDestino = new Ubicacion(4,8);
		terminalDestino = new TerminalGestionada(ubiDestino,null,null, null);
		terminalOrigen =new TerminalGestionada(ubiOrigen,null,null, null);
	}
	
	@Test
	public void testTramoValido() {

	    Tramo tramo = new Tramo(terminalOrigen, terminalDestino, 200, 3);

	    assertTrue(tramo.getOrigen().esLaTerminal(terminalOrigen));
	    assertTrue(tramo.getDestino().esLaTerminal(terminalDestino));
	    assertEquals(200, tramo.getValor(), 0.01);
	    assertEquals(3, tramo.getDuracion());
	}
	
	@Test
	public void testOrigenNuloDebeFallar() {
	    assertThrows(NullPointerException.class, () -> {
	        new Tramo(null, terminalDestino, 100, 2);
	    });
	}
	
	@Test
	public void testDestinoNuloDebeFallar() {
	    assertThrows(NullPointerException.class, () -> {
	        new Tramo(terminalDestino, null, 100, 2);
	    });
	}
	
	@Test
	public void testOrigenIgualADestinoDebeFallar() {
	    assertThrows(IllegalArgumentException.class, () -> {
	        new Tramo(terminalDestino, terminalDestino, 1000, 2);
	    });
	}
	
	@Test
	public void testValorNegativoDebeFallar() {
	    assertThrows(IllegalArgumentException.class, () -> {
	        new Tramo(terminalOrigen, terminalDestino, -0.1f, 2);
	    });
	}
	
	@Test
	public void testDuracionCeroDebeFallar() {
	    assertThrows(IllegalArgumentException.class, () -> {
	        new Tramo(terminalOrigen, terminalDestino, 1000, 0);
	    });
	}
	
	@Test
	public void testDuracionNegativaDebeFallar() {
	    assertThrows(IllegalArgumentException.class, () -> {
	        new Tramo(terminalOrigen, terminalDestino, 1000, -3);
	    });
	}

}
