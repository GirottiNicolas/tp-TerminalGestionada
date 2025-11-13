package warehousetest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import warehouse.Buque;
import warehouse.Carga;
import warehouse.IVisitorReporte;
import warehouse.ReporteMuelle;

// Fechas
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    
}
