package logistica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import terminalgestionada.TerminalGestionada;
import warehouse.Buque;

public class Logistica {
	private List<Naviera> navieras;
	private EstrategiaDeBusqueda estrategia;
	
	public void setMejorCircuito(EstrategiaDeBusqueda estrategia) {
		this.estrategia = estrategia;
	}
	
	public void registrarNaviera(Naviera naviera) {
		navieras.add(naviera);
	}
	
	public Circuito mejorCircuito(TerminalGestionada origen, TerminalGestionada destino) {
		return estrategia.seleccionarMejorCircuito(this.circuitosQueIncluyen(origen), destino);
	}
	
	public LocalDate primeraFechaBuque(LocalDate fechaActual, Buque buque, TerminalGestionada origen, TerminalGestionada destino) {
		return navieras.stream() .flatMap(n -> n.getViajes().stream())
				.filter(v -> v.getBuque().esElBuque(buque))
				.filter(v -> v.getCronograma().containsKey(origen))
				.filter(v -> v.getCronograma().containsKey(destino))
				.map(v -> v.getCronograma().get(destino))
				.filter(f -> !f.isBefore(fechaActual))
				.min(LocalDate::compareTo) .orElseThrow(() -> new IllegalArgumentException("No hay viajes futuros del buque desde origen a destino."));
		}
	
	public Logistica(EstrategiaDeBusqueda estrategiaInicial){
		navieras = new ArrayList<>();
		estrategia = estrategiaInicial;
	}
	
	private List<Circuito> circuitosQueIncluyen(TerminalGestionada terminal) {
	    return navieras.stream()
	            .flatMap(naviera -> naviera.getCircuitos().stream())
	            .filter(circuito -> circuito.contieneTerminal(terminal))
	            .toList();
	    }


	public List<Naviera> getNavieras() {
		return navieras;
	}
	
	public int tiempoDeNavieraEntre(Naviera naviera, TerminalGestionada origen, TerminalGestionada destino) {
		return naviera.getCircuitos().stream()
				.filter(c -> c.getTerminales().contains(origen) && c.getTerminales().contains(destino))
				.mapToInt(c -> c.tiempoTotalEntre(origen, destino))
				.min().orElseThrow(() -> new IllegalArgumentException("La naviera no tiene circuitos que conecten origen y destino."));
		}
}
