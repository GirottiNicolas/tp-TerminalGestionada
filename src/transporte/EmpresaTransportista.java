package transporte;

import java.util.ArrayList;
import java.util.List;

public class EmpresaTransportista {
	List<Camion> transportes;
	
	public EmpresaTransportista() {
		this.transportes = new ArrayList<Camion>();
	}
	
	public void agregarCamion(Camion camion) {
		transportes.add(camion);
	}
}
