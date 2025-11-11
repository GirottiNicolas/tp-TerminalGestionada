package warehouse;

import gestionterrestre.Ubicacion;
import terminalgestionada.TerminalGestionada;

public class Buque {

	private EstadoBuque fase;
    private Ubicacion posicion;
    private TerminalGestionada terminal;

    public Buque(Ubicacion posicion, TerminalGestionada terminal) {
        this.posicion = posicion;
        this.terminal = terminal;
        // la fase inicial es Outbound 
        this.fase = new Outbound(); 
    }

    public EstadoBuque getFase() {
        return this.fase;
    }
    
    public Ubicacion getUbicacion() {
    	return this.posicion;
    }
	
    public TerminalGestionada getTerminal() {
    	return this.terminal;
    }
    
    void setFase(EstadoBuque nuevaFase) {
        this.fase = nuevaFase;
    }

	public void actualizarPosicion(Ubicacion nuevaPosicion) {
		this.posicion = nuevaPosicion; 
        this.fase.actualizarPosicion(this, nuevaPosicion);
	}
	
	public void iniciarTrabajo() {
        this.fase.iniciarTrabajo(this);
    }
	
	public void depart() {
		this.fase.depart(this);
	}
	
	public boolean esElBuque(Buque buque) {
		 return buque.getUbicacion().esLaUbicacion(buque.getUbicacion());
 	}
    
}
