package terminaltest;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gestion.terrestre.Ubicacion;
import terminalgestionada.FachadaTerminal;
import terminalgestionada.TerminalGestionada;

public class TerminalGestionadaTest {
	
	TerminalGestionada terminal;
	Ubicacion ubicacion;
	
	@BeforeEach
	public void setUp() {
		terminal = new TerminalGestionada(null, null, null, null);
	}
	
	@Test
	public void test1() {
		assertTrue(false);
	}
	
	//a
}
