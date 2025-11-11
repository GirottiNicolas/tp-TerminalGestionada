package warehouse;

import gestionterrestre.OrdenDeImportacion;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ServicioAlmacenamiento implements IServicio{

	private OrdenDeImportacion orden;
    private double costoPorDia;
    private final int toleranciaHoras = 24;

    public ServicioAlmacenamiento(OrdenDeImportacion orden, double costoPorDia) {
        this.orden = orden;
        this.costoPorDia = costoPorDia;
    }

    @Override
    public double calcularCosto() {
        LocalDateTime fechaLlegada = orden.getFechaLlegadaNotificada();
        LocalDateTime fechaRetiro = orden.getFechaRetiroEfectivo();

        if (fechaRetiro == null) {
            return 0.0; 
        }

        LocalDateTime fechaLimite = fechaLlegada.plusHours(toleranciaHoras);

        if (fechaRetiro.isBefore(fechaLimite) || fechaRetiro.isEqual(fechaLimite)) {
            return 0.0;
        }

        long horasDemora = ChronoUnit.HOURS.between(fechaLimite, fechaRetiro);
        long diasMulta = (horasDemora / 24) + 1;

        return diasMulta * this.costoPorDia;
    }
	
}
