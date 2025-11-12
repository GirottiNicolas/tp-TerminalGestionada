package logistica;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import terminalgestionada.TerminalGestionada;
import warehouse.Buque;

public class Viaje {
	
	private Buque buqueDeViaje;
	
	private Circuito circuitoDeViaje;
	
	private LocalDate fechaPartida;
	
	private Map<TerminalGestionada, LocalDate> cronograma;
	
	public LocalDate getFechaPartida() {
		return this.fechaPartida;
	}
	
	public Buque getBuque() {
		return buqueDeViaje;
	}
	
	public TerminalGestionada getOrigenViaje() {
		return circuitoDeViaje.getTerminalOrigen();
	}
	
	public Circuito getCircuito() {
		return circuitoDeViaje;
	}
	
	public Map<TerminalGestionada, LocalDate> getCronograma(){
		return cronograma;
	}
	
	private Map<TerminalGestionada, LocalDate> calcularCronograma() {
        Map<TerminalGestionada, LocalDate> cronograma = new LinkedHashMap<>();
        LocalDate fecha = fechaPartida;
        for (Tramo tramo : circuitoDeViaje.getTramos()) {
        	fecha = fecha.plusDays(tramo.getDuracion());
            cronograma.put(tramo.getDestino(), fecha);
        }
        return cronograma;
    }

	public Viaje(Buque buqueDeViaje, Circuito circuitoDeViaje, LocalDate fechaPartida) {
		this.buqueDeViaje = buqueDeViaje;
		this.circuitoDeViaje = circuitoDeViaje;
		this.fechaPartida = fechaPartida;
		this.cronograma = this.calcularCronograma();
	}
	
}
