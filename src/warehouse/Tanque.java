package warehouse;

import warehouse.BillOfLading;
import warehouse.BLCompuesto;

public class Tanque extends Carga {
    public Tanque(double ancho, double largo, double altura, double pesoTotal, BillOfLading bl) {
        super(ancho, largo, altura, pesoTotal, bl);
        
        if (bl instanceof BLCompuesto) {
            throw new IllegalArgumentException("Un Tanque no puede tener un BL Compuesto");
        }
        
    }
    
}
