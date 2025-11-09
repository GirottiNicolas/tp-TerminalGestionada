package logisticatest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logistica.Logistica;



public class LogisticaTest {
	
	Logistica logistica;
	
	
	@BeforeEach
	public void setUp() {
		logistica = new Logistica();
	}
	
	
	@Test
	public void test1() {
		assertTrue(false);
	}
	
}
