package warehouse;

import warehouse.Buque;
import warehouse.Carga;
import java.time.format.DateTimeFormatter;

public class ReporteMuelle implements IVisitorReporte {

	
	// Tiene las variables estas porque hay que generar un texto plano 
	// Un StringBuilder para construir el reporte
    private StringBuilder sb = new StringBuilder();

    // El formato de fecha que definimos en el test
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Override
    public void visitBuque(Buque buque) {
        // 1. El visitante le pide los datos al buque
        sb.append("Buque: ").append(buque.getNombre()).append("\n");
        sb.append("Arribo: ").append(buque.getFechaArribo().format(fmt)).append("\n");
        sb.append("Partida: ").append(buque.getFechaPartida().format(fmt)).append("\n");

        // 2. Calcula el total de contenedores
        int total = buque.getContainersCargados().size() + 
                    buque.getContainersDescargados().size();

        sb.append("Contenedores Operados: ").append(total);
    }

    @Override
    public void visitCarga(Carga carga) {
        // El reporte de Muelle NO necesita visitar cada carga,
        // así que este método se deja vacío.
    }

    @Override
    public String getReporte() {
        return sb.toString();
    }

}
