package warehousetest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import gestion.gestores.importacion.OrdenDeImportacion;
import warehouse.IServicio;
import warehouse.ServicioLavado;
import warehouse.ServicioPesado;
import warehouse.Carga;
import warehouse.ServicioElectricidad;
import warehouse.Reefer;
import java.time.LocalDateTime;
import warehouse.ServicioAlmacenamiento;
import warehouse.ServicioDesconsolidado;

public class ServicioTest {
	
	private IServicio servicioPesado;
	private Carga cargaChica; // Mock
    private Carga cargaGrande; // Mock
    private IServicio servicioLavado;
    private OrdenDeImportacion ordenA_Tiempo; // Mock
    private OrdenDeImportacion ordenDemorada;

    @BeforeEach
    public void setUp() {
        this.servicioPesado = new ServicioPesado(1000.0);
        // Costo fijo
        this.cargaChica = Mockito.mock(Carga.class);
        this.cargaGrande = Mockito.mock(Carga.class);

        Mockito.when(cargaChica.getVolumen()).thenReturn(70.0);
        // Verifico Volumen
        Mockito.when(cargaGrande.getVolumen()).thenReturn(71.0);
        this.servicioLavado = new ServicioLavado(cargaChica, 500.0, 1000.0);
        this.ordenA_Tiempo = Mockito.mock(OrdenDeImportacion.class);
        this.ordenDemorada = Mockito.mock(OrdenDeImportacion.class);

        LocalDateTime fechaLlegada = LocalDateTime.of(2025, 11, 10, 8, 0); 
        LocalDateTime fechaRetiroATiempo = LocalDateTime.of(2025, 11, 11, 7, 0); 
        Mockito.when(ordenA_Tiempo.getFechaLlegadaNotificada()).thenReturn(fechaLlegada);
        Mockito.when(ordenA_Tiempo.getFechaRetiroEfectivo()).thenReturn(fechaRetiroATiempo);
        LocalDateTime fechaRetiroTarde = LocalDateTime.of(2025, 11, 12, 9, 0);
        Mockito.when(ordenDemorada.getFechaLlegadaNotificada()).thenReturn(fechaLlegada);
        Mockito.when(ordenDemorada.getFechaRetiroEfectivo()).thenReturn(fechaRetiroTarde);
        // verifico fechas
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
        Mockito.when(reeferMock.getConsumoKwHora()).thenReturn(10.0); 
        // Consume 10kw/h

        ServicioElectricidad servicioElec = new ServicioElectricidad(reeferMock, 50.0);
        LocalDateTime inicio = LocalDateTime.of(2025, 11, 10, 8, 0); 
        LocalDateTime fin = LocalDateTime.of(2025, 11, 10, 10, 0);  

        servicioElec.conectar(inicio);
        servicioElec.desconectar(fin); 
        // Se concecto 2 horas
        assertEquals(1000.0, servicioElec.calcularCosto());
    }
    
    @Test
    public void test05_AlmacenamientoCobraCeroSiSeRetiraDentroDeLas24Hs() {
        IServicio almacenamiento = new ServicioAlmacenamiento(ordenA_Tiempo, 5000.0);

        assertEquals(0.0, almacenamiento.calcularCosto());
    }

    @Test
    public void test06_AlmacenamientoCobraPorDiaExcedente() {
        IServicio almacenamiento = new ServicioAlmacenamiento(ordenDemorada, 5000.0);

        assertEquals(10000.0, almacenamiento.calcularCosto());
    }
    
    @Test
    public void test07_ServicioDesconsolidadoTieneUnCostoFijo() {
        IServicio desconsolidado = new ServicioDesconsolidado(3000.0);

        assertEquals(3000.0, desconsolidado.calcularCosto());
    }
    
}
