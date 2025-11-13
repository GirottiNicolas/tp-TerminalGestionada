package gestiontest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gestion.terrestre.Camion;
import gestion.terrestre.EmpresaTransportista;

public class EmpresaTransportistaTest {
	Camion camion;
	EmpresaTransportista empresaTransporte;
	
	
	@BeforeEach
	public void setUp() {
		empresaTransporte = new EmpresaTransportista("Rodados");
		camion = new Camion("AAA222","Juan",null);
	}
	
	
	@Test
	public void agregarCamion() {
		assertFalse(empresaTransporte.tieneCamion(camion));
		assertDoesNotThrow(() -> empresaTransporte.agregarCamion(camion));
		assertTrue(empresaTransporte.tieneCamion(camion));
	}
	
	
	
}
