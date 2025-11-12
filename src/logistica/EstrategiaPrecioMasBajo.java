package logistica;

import java.util.Comparator;
import java.util.List;

import terminalgestionada.TerminalGestionada;

public class EstrategiaPrecioMasBajo implements EstrategiaDeBusqueda {

	@Override
	public Circuito seleccionarMejorCircuito(List<Circuito> circuitos, TerminalGestionada destino) {
		return circuitos.stream()
	            .filter(c -> c.getTerminales().contains(destino))
	            .min(Comparator.comparingDouble(c -> c.precioTotalEntre(c.getTerminalOrigen(), destino)))
	            .orElseThrow(() -> new IllegalArgumentException("No hay circuitos que lleguen al destino."));
	}
}
