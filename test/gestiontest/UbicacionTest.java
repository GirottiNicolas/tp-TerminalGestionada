package gestiontest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gestion.terrestre.Ubicacion;

public class UbicacionTest {
	
	Ubicacion ubicacion1;
	Ubicacion ubicacion2;
	
	
	@BeforeEach
	public void setUp() {
		ubicacion1 = new Ubicacion(2,2);
		ubicacion2 = new Ubicacion(4,4);
	}
	
	@Test
	public void distanciaEntreUbicaciones() {
		assertEquals(2,ubicacion1.distanciaA(ubicacion2, 1.0));
	}
}
