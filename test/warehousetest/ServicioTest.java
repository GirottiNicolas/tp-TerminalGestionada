package warehousetest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import warehouse.IServicio;
import warehouse.ServicioLavado;
import warehouse.ServicioPesado;
import warehouse.Carga;
import warehouse.ServicioElectricidad;
import warehouse.Reefer;
import java.time.LocalDateTime;

public class ServicioTest {
	
	private IServicio servicioPesado;
	
	private Carga cargaChica; // Mock
    private Carga cargaGrande; // Mock
    private IServicio servicioLavado;

    @BeforeEach
    public void setUp() {
        // Creamos un servicio de pesado con un costo fijo de 1000
        this.servicioPesado = new ServicioPesado(1000.0);
        
     // --- Setup para Lavado ---
        this.cargaChica = Mockito.mock(Carga.class);
        this.cargaGrande = Mockito.mock(Carga.class);

        Mockito.when(cargaChica.getVolumen()).thenReturn(70.0);
        Mockito.when(cargaGrande.getVolumen()).thenReturn(71.0);

        this.servicioLavado = new ServicioLavado(cargaChica, 500.0, 1000.0);
    }
    

    @Test
    public void test01_UnServicioDePesadoTieneUnCostoFijo() {
        IServicio servicioPesado = new ServicioPesado(1000.0);

        assertEquals(1000.0, servicioPesado.calcularCosto());
    }
    
    @Test
    public void test02_ServicioDeLavadoCuestaMenosParaCargasChicas() {
  
        IServicio lavado = new ServicioLavado(cargaChica, 500.0, 1000.0);

        assertEquals(500.0, lavado.calcularCosto());
    }

    @Test
    public void test03_ServicioDeLavadoCuestaMasParaCargasGrandes() {
   
        IServicio lavado = new ServicioLavado(cargaGrande, 500.0, 1000.0);

        assertEquals(1000.0, lavado.calcularCosto());
    }
	
    @Test
    public void test04_ServicioElectricidadCalculaElCostoSegunElTiempo() {

        Reefer reeferMock = Mockito.mock(Reefer.class);
        Mockito.when(reeferMock.getConsumoKwHora()).thenReturn(10.0); // Consume 10kw/h

        ServicioElectricidad servicioElec = new ServicioElectricidad(reeferMock, 50.0);

        LocalDateTime inicio = LocalDateTime.of(2025, 11, 10, 8, 0); 
        LocalDateTime fin = LocalDateTime.of(2025, 11, 10, 10, 0);  

        servicioElec.conectar(inicio);
        servicioElec.desconectar(fin); // Estuvo conectado 2 horas

        assertEquals(1000.0, servicioElec.calcularCosto());
    }
    
}
