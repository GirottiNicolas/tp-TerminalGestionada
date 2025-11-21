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
        this.buqueMock = Mockito.mock(Buque.class);
        this.cargaMock1 = Mockito.mock(Carga.class);
        this.cargaMock2 = Mockito.mock(Carga.class);

        this.arribo = LocalDateTime.of(2025, 11, 10, 8, 0);  // 10/Nov 8:00
        this.partida = LocalDateTime.of(2025, 11, 11, 14, 0); // 11/Nov 14:00
        when(buqueMock.getNombre()).thenReturn("El Neptuno");
        when(buqueMock.getFechaArribo()).thenReturn(arribo);
        when(buqueMock.getFechaPartida()).thenReturn(partida);
        when(buqueMock.getContainersCargados()).thenReturn(List.of(cargaMock1));
        when(buqueMock.getContainersDescargados()).thenReturn(List.of(cargaMock2));
    }
	
    @Test
    public void test01_ReporteMuelleGeneraTextoPlano() {
        IVisitorReporte reporteMuelle = new ReporteMuelle();
        reporteMuelle.visitBuque(buqueMock);
        String resultado = reporteMuelle.getReporte();
        String esperado = 
            "Buque: El Neptuno\n" +
            "Arribo: 10-11-2025 08:00\n" +
            "Partida: 11-11-2025 14:00\n" +
            "Contenedores Operados: 2";

        assertEquals(esperado, resultado.trim());
    }
    
    @Test
    public void test02_ReporteAduanaGeneraHTMLConListaDeCargas() {
        when(cargaMock1.getID()).thenReturn("DRY777");
        when(cargaMock1.getTipo()).thenReturn("Dry");
        when(cargaMock2.getID()).thenReturn("REEF123");
        when(cargaMock2.getTipo()).thenReturn("Reefer");
        when(buqueMock.getContainersDescargados()).thenReturn(List.of(cargaMock1));
        when(buqueMock.getContainersCargados()).thenReturn(List.of(cargaMock2));
        IVisitorReporte reporteAduana = new ReporteAduana();
        reporteAduana.visitBuque(buqueMock);
        String resultado = reporteAduana.getReporte();

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
        when(cargaMock1.getID()).thenReturn("merk1234567"); 
        when(cargaMock2.getID()).thenReturn("green7654321");
        when(buqueMock.getContainersDescargados()).thenReturn(List.of(cargaMock1));
        when(buqueMock.getContainersCargados()).thenReturn(List.of(cargaMock2));

        IVisitorReporte reporteBuque = new ReporteBuque();
        reporteBuque.visitBuque(buqueMock);
        String resultado = reporteBuque.getReporte();
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
