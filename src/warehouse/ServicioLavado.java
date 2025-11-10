package warehouse;

public class ServicioLavado implements IServicio {

	private double costoMenor;
    private double costoMayor;
    private double limiteVolumen = 70.0;
    private Carga carga; 

    public ServicioLavado(Carga carga, double costoMenor, double costoMayor) {
        this.carga = carga;
        this.costoMenor = costoMenor;
        this.costoMayor = costoMayor;
    }

    @Override
    public double calcularCosto() {
        if (this.carga.getVolumen() > this.limiteVolumen) {
            return this.costoMayor;
        } else {
            return this.costoMenor;
        }
    }

}
