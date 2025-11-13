package warehouse;

import java.time.format.DateTimeFormatter;

public class ReporteAduana implements IVisitorReporte {

	private StringBuilder sb = new StringBuilder();
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Override
    public void visitBuque(Buque buque) {
        // 1. El visitante pide los datos del buque
        sb.append("<html><body>\n");
        sb.append("<h1>Reporte Aduana</h1>\n");
        sb.append("<h2>Buque: ").append(buque.getNombre()).append("</h2>\n");
        sb.append("<p>Arribo: ").append(buque.getFechaArribo().format(fmt)).append("</p>\n");
        sb.append("<p>Partida: ").append(buque.getFechaPartida().format(fmt)).append("</p>\n");

        // 2. El visitante pide las listas de cargas
        sb.append("<h3>Contenedores Operados:</h3>\n<ul>\n");

        // Itera sobre las cargas descargadas
        for (Carga carga : buque.getContainersDescargados()) {
            // Llama a un helper para no repetir código
            generarItemHTML(carga); 
        }

        // Itera sobre las cargas cargadas
        for (Carga carga : buque.getContainersCargados()) {
            generarItemHTML(carga);
        }

        sb.append("</ul>\n");
    }

    // Método privado para generar la línea de la carga
    private void generarItemHTML(Carga carga) {
        sb.append("<li>")
          .append(carga.getID()).append(" (")
          .append(carga.getTipo()).append(")")
          .append("</li>\n");
    }

    @Override
    public void visitCarga(Carga carga) {
        // Este visitante no necesita este método, ya que visitBuque maneja todo.
    }

    @Override
    public String getReporte() {
        // Cierra las etiquetas HTML
        sb.append("</body></html>");
        return sb.toString();
    }

}
