package warehousetest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import warehouse.Buque;
import warehouse.Carga;
import warehouse.IVisitorReporte;
import warehouse.ReporteAduana;
import warehouse.ReporteMuelle;
import warehouse.ReporteBuque;

// Fechas
import java.time.LocalDateTime;
import java.util.List; 

public class ReporteVisitorTest {
	
	private Buque buqueMock;
    private Carga cargaMock1;
    private Carga cargaMock2;

    private LocalDateTime arribo;
    private LocalDateTime partida;

    @BeforeEach
    public void setUp() {
        // 1. Creamos Mocks para las clases de dominio
        this.buqueMock = Mockito.mock(Buque.class);
        this.cargaMock1 = Mockito.mock(Carga.class);
        this.cargaMock2 = Mockito.mock(Carga.class);

        this.arribo = LocalDateTime.of(2025, 11, 10, 8, 0);  // 10/Nov 8:00
        this.partida = LocalDateTime.of(2025, 11, 11, 14, 0); // 11/Nov 14:00

        // Programamos los Mocks (Stubbing)
        when(buqueMock.getNombre()).thenReturn("El Neptuno");
        when(buqueMock.getFechaArribo()).thenReturn(arribo);
        when(buqueMock.getFechaPartida()).thenReturn(partida);

        when(buqueMock.getContainersCargados()).thenReturn(List.of(cargaMock1));
        when(buqueMock.getContainersDescargados()).thenReturn(List.of(cargaMock2));
    }
	
    @Test
    public void test01_ReporteMuelleGeneraTextoPlano() {
        // 1. SETUP: Creamos el visitante concreto
        IVisitorReporte reporteMuelle = new ReporteMuelle();

        // 2. EJECUCIÓN: Simulamos la visita.
        // El Buque "acepta" al visitante (buque.accept(reporteMuelle))
        // lo que llama al método 'visitBuque' del visitante.
        reporteMuelle.visitBuque(buqueMock);

        // Obtenemos el resultado
        String resultado = reporteMuelle.getReporte();

        // 3. VERIFICACIÓN: Comparamos el resultado con el texto plano esperado
        String esperado = 
            "Buque: El Neptuno\n" +
            "Arribo: 10-11-2025 08:00\n" +
            "Partida: 11-11-2025 14:00\n" +
            "Contenedores Operados: 2";

        assertEquals(esperado, resultado.trim());
    }
    
    @Test
    public void test02_ReporteAduanaGeneraHTMLConListaDeCargas() {
        // SETUP
        when(cargaMock1.getID()).thenReturn("DRY777");
        when(cargaMock1.getTipo()).thenReturn("Dry");

        when(cargaMock2.getID()).thenReturn("REEF123");
        when(cargaMock2.getTipo()).thenReturn("Reefer");

        when(buqueMock.getContainersDescargados()).thenReturn(List.of(cargaMock1));
        when(buqueMock.getContainersCargados()).thenReturn(List.of(cargaMock2));

        IVisitorReporte reporteAduana = new ReporteAduana();
        reporteAduana.visitBuque(buqueMock);

        String resultado = reporteAduana.getReporte();

        // Verificamos que el HTML se generó

        assertTrue(resultado.contains("<html>"));
        assertTrue(resultado.contains("Buque: El Neptuno"));
        assertTrue(resultado.contains("Arribo: 10-11-2025 08:00"));
        assertTrue(resultado.contains("Partida: 11-11-2025 14:00"));

        assertTrue(resultado.contains("DRY777 (Dry)"));
        assertTrue(resultado.contains("REEF123 (Reefer)"));
        assertTrue(resultado.contains("</html>"));
    }
    
    @Test
    public void test03_ReporteBuqueGeneraXMLConListasDeCargas() {
        // SETUP
        when(cargaMock1.getID()).thenReturn("merk1234567"); 

        when(cargaMock2.getID()).thenReturn("green7654321");

        when(buqueMock.getContainersDescargados()).thenReturn(List.of(cargaMock1));
        // La lista de exportación es 'containersCargados'
        when(buqueMock.getContainersCargados()).thenReturn(List.of(cargaMock2));

        // Creamos el visitante y lo mandamos al buque
        IVisitorReporte reporteBuque = new ReporteBuque();
        reporteBuque.visitBuque(buqueMock);

        String resultado = reporteBuque.getReporte();

        // 3. VERIFICACIÓN: Verificamos que el XML se generó
        String resultadoLimpio = resultado.replaceAll("\\s+", "");

        String esperado = 
            "<report>" +
                "<import>" +
                    "<item>merk1234567</item>" +
                "</import>" +
                "<export>" +
                    "<item>green7654321</item>" +
                "</export>" +
            "</report>";

        assertEquals(esperado, resultadoLimpio);
    }
    
}
