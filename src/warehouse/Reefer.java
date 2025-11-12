package warehouse;

public class Reefer extends Carga {
    
	private double consumoKwHora;

    public Reefer(double ancho, double largo, double altura, double pesoTotal, double consumoKwHora, BillOfLading bl, String id) { 
        super(ancho, largo, altura, pesoTotal, bl, id); 
        if (bl instanceof BLCompuesto) {
            throw new IllegalArgumentException("Un Reefer no puede tener un BL Compuesto");
        }
        this.consumoKwHora = consumoKwHora;
    }

    public double getConsumoKwHora() {
        return this.consumoKwHora;
    }
}
