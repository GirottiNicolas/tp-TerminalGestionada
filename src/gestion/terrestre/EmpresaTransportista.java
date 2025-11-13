package gestion.terrestre;

import java.util.ArrayList;
import java.util.List;

public class EmpresaTransportista {
	String nombreEmpresa;
	List<Camion> camiones;
	
	
	public EmpresaTransportista(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
		this.camiones = new ArrayList<Camion>();
	}
	
	public void agregarCamion(Camion camion) {
		camiones.add(camion);
	}
	
	public boolean tieneCamion(Camion camion) {
		return camiones.contains(camion);
	}
}
