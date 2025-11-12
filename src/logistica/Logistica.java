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
	
	public Circuito mejorCircuito(TerminalGestionada destino) {
		List<Circuito> todosLosCircuitos = navieras.stream()
	            .flatMap(n -> n.getCircuitos().stream()).toList();
		return estrategia.seleccionarMejorCircuito(todosLosCircuitos, destino);
	}
	
	public LocalDate primeraFechaDeBuque(LocalDate fechaActual, Buque buque, TerminalGestionada destino) {
        return navieras.stream()
            .flatMap(n -> n.getViajes().stream())
            .filter(v -> v.getBuque().esElBuque(buque))
            .filter(v -> v.getCronograma().containsKey(destino))
            .map(v -> v.getCronograma().get(destino))
            .filter(f -> !f.isBefore(fechaActual))
            .min(LocalDate::compareTo)
            .orElseThrow(() -> new IllegalArgumentException("No hay viajes futuros del buque a la terminal."));
    }
	
	public Logistica(EstrategiaDeBusqueda estrategiaInicial){
		navieras = new ArrayList<>();
		estrategia = estrategiaInicial;
	}

	public List<Naviera> getNavieras() {
		return navieras;
	}
}
