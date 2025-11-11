package logistica;

import java.util.ArrayList;
import java.util.List;

import terminalgestionada.TerminalGestionada;

public class Circuito {
	private List<Tramo> tramos;
	
	public List<Tramo> getTramos(){
		return tramos;
	}

	public TerminalGestionada getTerminalOrigen() {
		return tramos.getFirst().getOrigen();
	}
	
	public TerminalGestionada getTerminalDestino() {
		return tramos.get(tramos.size() - 1).getOrigen();
    }
	

    public List<TerminalGestionada> getTerminales() {
        List<TerminalGestionada> terminales = new ArrayList<>();
        for (Tramo tramo : tramos) {
            terminales.add(tramo.getOrigen());
        }
        return terminales;
    }
    
    public List<Tramo> rutaEntre(TerminalGestionada origen, TerminalGestionada destino) {
        List<Tramo> ruta = new ArrayList<>();
        boolean enRuta = false;
        for (Tramo tramo : tramos) {
            if (tramo.getOrigen().equals(origen)) {
                enRuta = true;
            }
            if (enRuta) {
                ruta.add(tramo);
                if (tramo.getDestino().equals(destino)) break;
            }
        }
        return ruta;
    }
    
    public int tiempoTotalEntre(TerminalGestionada origen, TerminalGestionada destino) {
        return rutaEntre(origen, destino).stream()
                .mapToInt(Tramo::getDuracion)
                .sum();
    }
    
    public double precioTotalEntre(TerminalGestionada origen, TerminalGestionada destino) {
        return rutaEntre(origen, destino).stream()
                .mapToDouble(Tramo::getValor)
                .sum();
    }
    
    private void completaCircuito(List<Tramo> tramos) {
    	 TerminalGestionada origen = tramos.get(0).getOrigen();
    	    TerminalGestionada destinoFinal = tramos.get(tramos.size() - 1).getDestino();

    	    if (!destinoFinal.equals(origen)) {
    	        throw new IllegalArgumentException("El circuito debe cerrar: la última terminal debe conectar con la primera.");
    	    }
    	}

	public Circuito(List<Tramo> tramos) {
		this.tramos = new ArrayList<>(tramos);
		this.completaCircuito(tramos);
	}
    
    
}


