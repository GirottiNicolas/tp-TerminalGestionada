package warehouse;

public class Tanque extends Carga {
    public Tanque(double ancho, double largo, double altura, double pesoTotal, BillOfLading bl, String id) {
        super(ancho, largo, altura, pesoTotal, bl, id);
        
        if (bl instanceof BLCompuesto) {
            throw new IllegalArgumentException("Un Tanque no puede tener un BL Compuesto");
        }
        
    }
    
}
