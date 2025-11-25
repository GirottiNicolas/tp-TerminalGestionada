package warehouse;

import gestion.terrestre.Ubicacion;
import terminalgestionada.TerminalGestionada;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class Buque implements IElementoVisitable{

	private String nombre;
	private EstadoBuque fase;
    private Ubicacion posicion;
    private TerminalGestionada terminal;
    private LocalDateTime fechaArribo;
    private LocalDateTime fechaPartida;
    private List<Carga> containersDescargados = new ArrayList<>();
    private List<Carga> containersCargados = new ArrayList<>();

    public Buque(Ubicacion posicion, TerminalGestionada terminal, String nombre) {
        this.posicion = posicion;
        this.terminal = terminal;         // la fase inicial es Outbound 
        this.fase = new Outbound();
        this.nombre = nombre;
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
    
    public String getNombre() {
    	return this.nombre;
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
    
	@Override
    public void accept(IVisitorReporte visitor) {
        visitor.visitBuque(this);
    }
	
	public void setFechaArribo(LocalDateTime fecha) { 
		this.fechaArribo = fecha; 
	}
    
	public LocalDateTime getFechaArribo() { 
		return this.fechaArribo; 
	}
	
    public void setFechaPartida(LocalDateTime fecha) { 
    	this.fechaPartida = fecha; 
    }
    
    public LocalDateTime getFechaPartida() { 
    	return this.fechaPartida; 
    	
    }

    public void registrarCargaImportacion(Carga carga) { 
    	this.containersDescargados.add(carga); 
    	
    }
    
    public void registrarCargaExportacion(Carga carga) { 
    	this.containersCargados.add(carga); 
    	
    }
    
    public List<Carga> getContainersDescargados() { 
    	return this.containersDescargados; 
    	
    }
    
    public List<Carga> getContainersCargados() { 
    	return this.containersCargados; 
    }
}
	

