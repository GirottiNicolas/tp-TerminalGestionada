package warehouse;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {
	
	private List<Carga> cargasAlmacenadas;

    public Warehouse() {
        this.cargasAlmacenadas = new ArrayList<>();
    }

    public List<Carga> getCargasAlmacenadas() {
        return this.cargasAlmacenadas;
    }
    
    public void registrarCarga(Carga carga) {
        this.cargasAlmacenadas.add(carga);
    }
    
    public void aplicarServicio(IServicio servicio, Carga carga) {

        if (this.cargasAlmacenadas.contains(carga)) {
            carga.agregarServicio(servicio);
        } else {
            throw new IllegalArgumentException("La carga no se encuentra en el warehouse.");
        }
    }
	
}
