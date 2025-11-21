package warehousetest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import warehouse.BillOfLading;
import warehouse.BLSimple;
import warehouse.BLCompuesto;
import java.util.List;

public class BillOfLadingTest {
	
	private BillOfLading blSimple;
	private BillOfLading blCompuesto;
    private BillOfLading blHijo1;
    private BillOfLading blHijo2;

    @BeforeEach
    public void setUp() {
        this.blSimple = new BLSimple("Soja", 500.0);
        this.blCompuesto = new BLCompuesto();
        this.blHijo1 = new BLSimple("Trigo", 100.0);
        this.blHijo2 = new BLSimple("Maiz", 200.0);
        this.blCompuesto.agregarBL(blHijo1);
        this.blCompuesto.agregarBL(blHijo2);
    }

    @Test
    public void test01_UnBLSimpleConoceSuPesoYProducto() {
        assertEquals(500.0, blSimple.getPesoTotal());
        List<String> productos = blSimple.getTiposDeProducto();
        assertEquals(1, productos.size());
        assertTrue(productos.contains("Soja"));
    }
    
    @Test
    public void test02_UnBLCompuestoSumaElPesoDeSusHijos() {
        assertEquals(300.0, blCompuesto.getPesoTotal());
    }

    @Test
    public void test03_UnBLCompuestoAgregaLosProductosDeSusHijos() {
        List<String> productos = blCompuesto.getTiposDeProducto();
        assertEquals(2, productos.size());
        assertTrue(productos.contains("Trigo"));
        assertTrue(productos.contains("Maiz"));
    }
    
    @Test
    public void testBLSimpleLanzaExcepcionAlIntentarAgregarUnHijo() {
        BillOfLading otroBL = Mockito.mock(BillOfLading.class); 
        assertThrows(UnsupportedOperationException.class, () -> {
            blSimple.agregarBL(otroBL);
        });
    }
    
    
    
    
    
    
    
}
