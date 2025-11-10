package warehouse;

import java.util.List;

public class BLSimple implements BillOfLading{
	
	private String tipoDeProducto;
    private double peso;

    public BLSimple(String tipoDeProducto, double peso) {
        this.tipoDeProducto = tipoDeProducto;
        this.peso = peso;
    }

    @Override
    public double getPesoTotal() {
        return this.peso;
    }

    @Override
    public List<String> getTiposDeProducto() {
        return List.of(this.tipoDeProducto);
    }
    
    @Override
    public void agregarBL(BillOfLading bl) {
        // Lanzamos una excepción porque un BLSimple no puede tener hijos
        throw new UnsupportedOperationException("No se pueden agregar B/L a un BLSimple");
    }
    
}
