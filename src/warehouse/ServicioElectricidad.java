package warehouse;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ServicioElectricidad implements IServicio{
	
	private Reefer reefer;
    private double precioPorKw;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    public ServicioElectricidad(Reefer reefer, double precioPorKw) {
        this.reefer = reefer;
        this.precioPorKw = precioPorKw;
    }

    public void conectar(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void desconectar(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public double calcularCosto() {
        if (this.fechaInicio == null || this.fechaFin == null) {
            return 0.0; 
        }

        long horasConectado = ChronoUnit.HOURS.between(fechaInicio, fechaFin);
        double consumo = reefer.getConsumoKwHora();

        return consumo * horasConectado * this.precioPorKw;
    }
	
}
