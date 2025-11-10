package warehouse;

import java.util.List;

public interface BillOfLading {
	
	public double getPesoTotal();
    
    public List<String> getTiposDeProducto();
    
    public void agregarBL(BillOfLading bl);
}
