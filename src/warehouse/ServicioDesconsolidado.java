package warehouse;

public class ServicioDesconsolidado implements IServicio{
	
	private double costoFijo;

    public ServicioDesconsolidado(double costoFijo) {
        this.costoFijo = costoFijo;
    }

    @Override
    public double calcularCosto() {
        return this.costoFijo;
    }
	
}
