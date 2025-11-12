package logistica;

import java.util.Objects;

import terminalgestionada.TerminalGestionada;

public class Tramo {
	private TerminalGestionada origen;
	private TerminalGestionada destino;
	private float valor;
	private int duracion; //duración en días
	
	public TerminalGestionada getOrigen() {
		return origen;
	}
	
	public TerminalGestionada getDestino() {
		return destino;
	}
	
	public float getValor(){
		return valor;
	}
	
	public int getDuracion() {
		return duracion;
	}
	
	private float validarValor(float valor) {
    if (valor <= 0) {
        throw new IllegalArgumentException("El valor debe ser mayor a cero.");
    	}
    return valor;
	}

	private int validarDuracion(int duracion) {
    if (duracion <= 0) {
        throw new IllegalArgumentException("La duración debe ser mayor a cero.");
    	}
    return duracion;
	}
	
	private TerminalGestionada validarTerminales(TerminalGestionada origen, TerminalGestionada destino) {
	    Objects.requireNonNull(origen, "El origen no puede ser nulo.");
	    Objects.requireNonNull(destino, "El destino no puede ser nulo.");
	    if (origen.esLaTerminal(destino)) {
	        throw new IllegalArgumentException("El origen y el destino no pueden ser la misma terminal.");
	    }
	    return origen;
	}

	public Tramo(TerminalGestionada origen, TerminalGestionada destino, float valor, int duracion) {
		this.origen = this.validarTerminales(origen, destino);
		this.destino = destino;
		this.valor = this.validarValor(valor);
		this.duracion = this.validarDuracion(duracion);
	}
	
	
}
