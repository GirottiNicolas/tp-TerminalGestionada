package gestionterrestretest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gestionterrestre.GestionTerrestre;

public class GestionTerrestreTest {
	
	GestionTerrestre gestion;
	
	
	@BeforeEach
	public void setUp() {
		gestion = new GestionTerrestre();
	}
	
	
	@Test
	public void test1() {
		assertTrue(false);
	}
	
}
