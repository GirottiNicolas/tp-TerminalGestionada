package warehouse;

public class ServicioPesado implements IServicio {

	private double costoFijo;

    public ServicioPesado(double costoFijo) {
        this.costoFijo = costoFijo;
    }

    @Override
    public double calcularCosto() {
        return this.costoFijo;
    }
	
}
