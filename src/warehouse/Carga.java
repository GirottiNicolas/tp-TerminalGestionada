package warehouse;

import java.util.ArrayList;
import java.util.List;

import warehouse.IElementoVisitable;
import warehouse.IVisitorReporte;

public abstract class Carga implements IElementoVisitable{
	
	private double ancho;
    private double largo;
    private double altura;
    private double pesoTotal;
    private BillOfLading bl;
    private List<IServicio> serviciosAplicados;
    private String id;

    public Carga(double ancho, double largo, double altura, double pesoTotal, BillOfLading bl, String id) {
        this.ancho = ancho;
        this.largo = largo;
        this.altura = altura;
        this.pesoTotal = pesoTotal;
        this.bl = bl;
        this.serviciosAplicados = new ArrayList<>();
        this.id = id;
    }

    public double getAncho() {
        return this.ancho;
    }

    public double getLargo() {
        return this.largo;
    }

    public double getAltura() {
        return this.altura;
    }

    public double getPesoTotal() {
        return this.pesoTotal;
    }
    
    public BillOfLading getBillOfLading() {
        return this.bl;
    }

    public double getVolumen() {
        return this.ancho * this.largo * this.altura;
    }
    
    public String getID() {
        return this.id;
    }

    public String getTipo() {
    	// Devuelve el nombre de la clase hija (ej: "Dry", "Reefer")
        return this.getClass().getSimpleName();
    }
    
    public List<IServicio> getServiciosAplicados() {
        return this.serviciosAplicados;
    }

    public void agregarServicio(IServicio servicio) {
        this.serviciosAplicados.add(servicio);
    }
    
    @Override
    public void accept(IVisitorReporte visitor) {
        visitor.visitCarga(this);
    }
	
}
