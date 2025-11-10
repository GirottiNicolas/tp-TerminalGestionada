package warehouse;

import warehouse.BillOfLading;

public class Dry extends Carga {
    public Dry(double ancho, double largo, double altura, double pesoTotal, BillOfLading bl) {
        super(ancho, largo, altura, pesoTotal, bl); 
    }
}
