package warehouse;

import warehouse.BillOfLading;

public abstract class Carga {
	
	private double ancho;
    private double largo;
    private double altura;
    private double pesoTotal;
    private BillOfLading bl;

    public Carga(double ancho, double largo, double altura, double pesoTotal, BillOfLading bl) {
        this.ancho = ancho;
        this.largo = largo;
        this.altura = altura;
        this.pesoTotal = pesoTotal;
        this.bl = bl;
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
	
}
