package warehouse;

public class ReporteBuque implements IVisitorReporte {

	private StringBuilder sb = new StringBuilder();

    @Override
    public void visitBuque(Buque buque) {
        sb.append("<report>\n");

        // 1. Sección de Importación (containersDescargados)
        sb.append("\t<import>\n");
        for (Carga carga : buque.getContainersDescargados()) {
            // Llama a visitCarga para generar el item
            this.visitCarga(carga); 
        }
        sb.append("\t</import>\n");

        // 2. Sección de Exportación (containersCargados)
        sb.append("\t<export>\n");
        for (Carga carga : buque.getContainersCargados()) {
            this.visitCarga(carga);
        }
        sb.append("\t</export>\n");

        sb.append("</report>\n");
    }

    @Override
    public void visitCarga(Carga carga) {

        sb.append("\t\t<item>").append(carga.getID()).append("</item>\n");
        
    }

    @Override
    public String getReporte() {
        return sb.toString();
    }

}
