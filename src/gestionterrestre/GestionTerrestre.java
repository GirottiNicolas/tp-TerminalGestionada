package gestionterrestre;

import java.util.ArrayList;
import java.util.List;

public class GestionTerrestre {

	List<Cliente> clientes;
	
	public GestionTerrestre() {
		this.clientes = new ArrayList<Cliente>();
	}
	
	public void agregarCliente(Cliente cliente) {
		clientes.add(cliente);
		
	}

	public List<Cliente> getClientes() {
		// TODO Auto-generated method stub
		return clientes;
	}

}
