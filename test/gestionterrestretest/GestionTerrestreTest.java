package gestionterrestretest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gestionterrestre.Cliente;
import gestionterrestre.GestionTerrestre;

public class GestionTerrestreTest {
	
	GestionTerrestre gestion;
	Cliente cliente;
	
	
	@BeforeEach
	public void setUp() {
		cliente = new Cliente();
		gestion = new GestionTerrestre();
	}
	

	
	
	@Test
	public void clienteAgregado() {
		gestion.agregarCliente(cliente);
		assertTrue(gestion.getClientes().contains(cliente));
	}
}
