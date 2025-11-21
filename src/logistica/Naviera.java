package logistica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import terminalgestionada.TerminalGestionada;
import warehouse.Buque;

public class Naviera {
	private List<Viaje> viajes;
	private List<Circuito> circuitos;
	
	public Naviera() {
        this.circuitos = new ArrayList<>();
        this.viajes = new ArrayList<>();
    }

    public List<Circuito> getCircuitos() {
        return circuitos;
    }

    public List<Viaje> getViajes() {
        return viajes;
    }

    public void agregarCircuito(Circuito circuito) {
        circuitos.add(circuito);
    }
    

    public void agregarViaje(Viaje viaje) {
    	if (!circuitos.contains(viaje.getCircuito())) {
    		throw new IllegalArgumentException("El circuito no pertenece a la naviera");
    	}
        viajes.add(viaje);
    }
    
    public void publicarViaje(Circuito circuito, Buque buque, LocalDate fechaPartida) {
    	Viaje nuevoViaje = new Viaje(buque, circuito, fechaPartida);
    	this.agregarViaje(nuevoViaje);
    }

    public List<Viaje> viajesPorCircuito(Circuito circuito) {
        return viajes.stream()
            .filter(v -> v.getCircuito().equals(circuito)).toList();
    }

    public List<Viaje> viajesDesde(TerminalGestionada origen) {
        return viajes.stream()
            .filter(v -> v.getOrigenViaje().equals(origen)).toList();
    }

    public List<TerminalGestionada> terminalesVisitadas() {
        return circuitos.stream()
            .flatMap(c -> c.getTerminales().stream())
            .distinct().toList();
    }
    
    public LocalDate fechaDeViajeA(TerminalGestionada destino) {
    	return viajes.stream()
    		    .filter(v -> v.getCronograma().containsKey(destino))
    		    .map(v -> v.getCronograma().get(destino))
    		    .min(LocalDate::compareTo)
    		    .orElseThrow(() -> new IllegalArgumentException("No hay viajes a la terminal destino."));
    }
    
    public int tiempoDeViajeHasta(TerminalGestionada destino) {
        return viajes.stream()
            .filter(v -> v.getCronograma().containsKey(destino))
            .mapToInt(v -> {
                int acumulado = 0;
                for (Tramo tramo : v.getCircuito().getTramos()) {
                    acumulado += tramo.getDuracion();
                    if (tramo.getDestino().equals(destino)) {
                        return acumulado;
                    }
                }
                throw new IllegalStateException("El cronograma decía que llegaba, pero no se encontró el tramo.");
            })
            .min()
            .orElseThrow(() -> new IllegalArgumentException("No hay viajes que lleguen a la terminal destino."));
    }

}