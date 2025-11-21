package filtrostest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import filtros.FiltroAND;
import filtros.FiltroBusqueda;
import filtros.FiltroFechaLlegada;
import filtros.FiltroFechaSalida;
import filtros.FiltroOR;
import filtros.FiltroPuertoDestino;
import logistica.Circuito;
import logistica.Viaje;
import terminalgestionada.TerminalGestionada;

public class FiltroBusquedaTest {

	private Viaje viajeCumple;
    private Viaje viajeNoCumple;
    private Circuito circuitoMock;
    private TerminalGestionada puertoDestino;
    private TerminalGestionada otroPuerto;

    @BeforeEach
    public void setUp() {
        this.viajeCumple = Mockito.mock(Viaje.class);
        this.viajeNoCumple = Mockito.mock(Viaje.class);
        this.circuitoMock = Mockito.mock(Circuito.class);
        this.puertoDestino = Mockito.mock(TerminalGestionada.class);
        this.otroPuerto = Mockito.mock(TerminalGestionada.class);

        when(viajeCumple.getCircuito()).thenReturn(circuitoMock);
        when(viajeNoCumple.getCircuito()).thenReturn(circuitoMock);
    }

    @Test
    public void testFiltroPuertoDestino() {

        FiltroBusqueda filtro = new FiltroPuertoDestino(puertoDestino);

        when(circuitoMock.getTerminalDestino()).thenReturn(puertoDestino);

        assertTrue(filtro.cumple(viajeCumple));
       
        FiltroBusqueda filtroIncorrecto = new FiltroPuertoDestino(otroPuerto);
        
        assertFalse(filtroIncorrecto.cumple(viajeCumple));
    }


    @Test
    public void testFiltroFechaSalida() {
        LocalDate fechaBuscada = LocalDate.of(2025, 11, 20);
        LocalDate otraFecha = LocalDate.of(2025, 12, 1);

        FiltroBusqueda filtro = new FiltroFechaSalida(fechaBuscada);

        when(viajeCumple.getFechaPartida()).thenReturn(fechaBuscada); // Coincide
        when(viajeNoCumple.getFechaPartida()).thenReturn(otraFecha);  // No coincide

        assertTrue(filtro.cumple(viajeCumple));
        assertFalse(filtro.cumple(viajeNoCumple));
    }

    @Test
    public void testFiltroFechaLlegada() {
        LocalDate fechaLlegada = LocalDate.of(2025, 11, 25);
        FiltroBusqueda filtro = new FiltroFechaLlegada(fechaLlegada, puertoDestino);

        when(viajeCumple.getFechaLlegadaA(puertoDestino)).thenReturn(fechaLlegada);
        assertTrue(filtro.cumple(viajeCumple));

        when(viajeNoCumple.getFechaLlegadaA(puertoDestino)).thenReturn(fechaLlegada.plusDays(1));
        assertFalse(filtro.cumple(viajeNoCumple));

        when(viajeNoCumple.getFechaLlegadaA(puertoDestino)).thenReturn(null);
        assertFalse(filtro.cumple(viajeNoCumple));
    }
    
    @Test
    public void testFiltroAND() {
        FiltroBusqueda fTrue = mock(FiltroBusqueda.class);
        FiltroBusqueda fFalse = mock(FiltroBusqueda.class);
        
        when(fTrue.cumple(any())).thenReturn(true);
        when(fFalse.cumple(any())).thenReturn(false);

        FiltroBusqueda and1 = new FiltroAND(fTrue, fTrue);
        assertTrue(and1.cumple(viajeCumple));

        FiltroBusqueda and2 = new FiltroAND(fTrue, fFalse);
        assertFalse(and2.cumple(viajeCumple));
    }

    @Test
    public void testFiltroOR() {
        FiltroBusqueda fTrue = mock(FiltroBusqueda.class);
        FiltroBusqueda fFalse = mock(FiltroBusqueda.class);
        
        when(fTrue.cumple(any())).thenReturn(true);
        when(fFalse.cumple(any())).thenReturn(false);

        FiltroBusqueda or1 = new FiltroOR(fTrue, fFalse);
        assertTrue(or1.cumple(viajeCumple));

        FiltroBusqueda or2 = new FiltroOR(fFalse, fFalse);
        assertFalse(or2.cumple(viajeCumple));
    }
    
    @Test
    public void testFiltroComplejoOREnAND() {
        
        LocalDate fechaBuscada = LocalDate.of(2025, 1, 1);
        
        FiltroBusqueda filtroPuertoA = new FiltroPuertoDestino(puertoDestino); // Puerto A
        FiltroBusqueda filtroPuertoB = new FiltroPuertoDestino(otroPuerto);    // Puerto B
        FiltroBusqueda filtroFecha = new FiltroFechaSalida(fechaBuscada);
        
        FiltroBusqueda opcionPuertos = new FiltroOR(filtroPuertoA, filtroPuertoB);

        FiltroBusqueda filtroFinal = new FiltroAND(opcionPuertos, filtroFecha);
        
        when(circuitoMock.getTerminalDestino()).thenReturn(puertoDestino);
        when(viajeCumple.getFechaPartida()).thenReturn(fechaBuscada);
        assertTrue(filtroFinal.cumple(viajeCumple)); 
        
        Viaje viajePuertoB = Mockito.mock(Viaje.class);
        Circuito circuitoB = Mockito.mock(Circuito.class);
        when(viajePuertoB.getCircuito()).thenReturn(circuitoB);
        when(circuitoB.getTerminalDestino()).thenReturn(otroPuerto); // Puerto B
        when(viajePuertoB.getFechaPartida()).thenReturn(fechaBuscada);
        assertTrue(filtroFinal.cumple(viajePuertoB));
        
        Viaje viajeFechaMal = Mockito.mock(Viaje.class);
        when(viajeFechaMal.getCircuito()).thenReturn(circuitoMock); // Puerto A
        when(viajeFechaMal.getFechaPartida()).thenReturn(fechaBuscada.plusDays(1));
        // DEBE FALLAR
        assertFalse(filtroFinal.cumple(viajeFechaMal));
    }
	
	
}
