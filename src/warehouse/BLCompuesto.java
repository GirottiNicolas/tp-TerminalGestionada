package warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BLCompuesto implements BillOfLading{

	private List<BillOfLading> blsAgrupados = new ArrayList<>();

    public double getPesoTotal() {
        return blsAgrupados.stream().mapToDouble(BillOfLading::getPesoTotal).sum();
    }

    
    @Override
    public List<String> getTiposDeProducto() {
        // Acumula las listas de productos de todos los B/L hijos
        return blsAgrupados.stream().flatMap(bl -> bl.getTiposDeProducto().stream()).collect(Collectors.toList());
    }

    public void agregarBL(BillOfLading bl) {
        this.blsAgrupados.add(bl);
    }
	
}
