package gestiontest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gestion.terrestre.Camion;
import warehouse.Carga;
import warehouse.Dry;

public class CamionTest {
	Camion camion1;
	Camion camion2;
	Carga carga;
	
	@BeforeEach
	public void setUp() {
		camion1 = new Camion("2222","Jorge",null);
		camion2 = new Camion("2222","Sofia",null);
		carga = new Dry(0, 0, 0, 0, null, null);
	}
	
	
	@Test 
	public void esElCamion() {
		assertTrue(camion1.esCamionDesignado(camion1));
	}
	
	@Test 
	public void noEsElCamion() {
		assertFalse(camion1.esCamionDesignado(camion2));
	}
	
	@Test 
	public void cambioDeChofer() {
		assertTrue(camion1.esCamionDesignado(camion1));
		camion1.setChofer("Jack");
		assertTrue(camion1.getChofer().equals("Jack"));
	}
	
	@Test
	public void camionConCargaNueva() {
		assertDoesNotThrow(()-> camion1.asignarCarga(carga));
		
	}
	
}
