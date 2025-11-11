package warehousetest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import warehouse.Carga;
import warehouse.Dry;
import warehouse.Reefer;
import warehouse.Tanque;
import warehouse.BillOfLading;
import warehouse.BLCompuesto;
import warehouse.BLSimple;
import warehouse.IServicio;

public class CargaTest {

	private Carga containerDry;
	private BillOfLading blMock;

    @BeforeEach
    public void setUp() {
    	this.blMock = Mockito.mock(BillOfLading.class);

        this.containerDry = new Dry(10.0, 5.0, 5.0, 2000.0, blMock);
    }

    @Test
    public void test01_UnContainerDrySeCreaConSusDimensionesYPeso() {
        assertEquals(10.0, containerDry.getAncho());
        assertEquals(5.0, containerDry.getLargo());
        assertEquals(5.0, containerDry.getAltura());
        assertEquals(2000.0, containerDry.getPesoTotal());
        assertEquals(blMock, containerDry.getBillOfLading());
    }

    @Test
    public void test02_UnContainerConoceSuVolumen() {
        // El volumen es ancho * largo * altura
        assertEquals(250.0, containerDry.getVolumen()); // 10*5*5
    }
    
    @Test
    public void test03_UnContainerReeferConoceSuConsumo() {
        // Creamos un Reefer con un consumo de 150 kw/hora
        Reefer containerReefer = new Reefer(10.0, 5.0, 5.0, 2000.0, 150.0, blMock);

        assertEquals(2000.0, containerReefer.getPesoTotal());
        assertEquals(250.0, containerReefer.getVolumen());
        assertEquals(150.0, containerReefer.getConsumoKwHora());
        assertEquals(blMock, containerReefer.getBillOfLading());
    }
    
    @Test
    public void test04_UnContainerTanqueSeCreaCorrectamente() {
        
    	Carga containerTanque = new Tanque(15.0, 4.0, 4.0, 3000.0, blMock);

        // Verificamos los atributos heredados
        assertEquals(15.0, containerTanque.getAncho());
        assertEquals(4.0, containerTanque.getLargo());
        assertEquals(4.0, containerTanque.getAltura());
        assertEquals(3000.0, containerTanque.getPesoTotal());
        assertEquals(240.0, containerTanque.getVolumen()); // 15*4*4
        assertEquals(blMock, containerTanque.getBillOfLading());
    }
	
    @Test
    public void test05_UnReeferNoPuedeTenerUnBLCompuesto() {
        BillOfLading blCompuesto = new BLCompuesto();

        // Verificamos que construir un Reefer con un BLCompuesto lanza una excepción
        assertThrows(IllegalArgumentException.class, () -> {
            new Reefer(10.0, 5.0, 5.0, 2000.0, 150.0, blCompuesto);
        });
    }

    @Test
    public void test06_UnReeferSiPuedeTenerUnBLSimple() {
        BillOfLading blSimple = new BLSimple("Medicamentos", 500.0);

        Reefer containerReefer = new Reefer(10.0, 5.0, 5.0, 2000.0, 150.0, blSimple);

        assertEquals(blSimple, containerReefer.getBillOfLading());
    }
    
    @Test
    public void test09_UnDrySiPuedeTenerUnBLCompuesto() {
        BillOfLading blCompuesto = new BLCompuesto();

        Dry containerDry = new Dry(10.0, 5.0, 5.0, 2000.0, blCompuesto);

        assertEquals(blCompuesto, containerDry.getBillOfLading());
        // No se lanza ninguna excepción 
    }
    
    @Test
    public void test10_UnaCargaPuedeRegistrarServicios() {

        IServicio servicioMock = Mockito.mock(IServicio.class);

        assertTrue(containerDry.getServiciosAplicados().isEmpty());
        containerDry.agregarServicio(servicioMock);

        assertTrue(containerDry.getServiciosAplicados().contains(servicioMock));
    }
    
}
