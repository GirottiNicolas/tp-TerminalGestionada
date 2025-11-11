package warehousetest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import warehouse.Warehouse;
import warehouse.Carga;
import warehouse.IServicio;

public class WarehouseTest {
	
	private Warehouse warehouse;
	private Carga cargaMock;
    private IServicio servicioMock;

    @BeforeEach
    public void setUp() {
        this.warehouse = new Warehouse();
        this.cargaMock = Mockito.mock(Carga.class);
        this.servicioMock = Mockito.mock(IServicio.class);
    }
	
    @Test
    public void test01_UnWarehouseNuevoEstaVacio() {
        // Un warehouse nuevo no debe tener cargas ni transportes
        assertTrue(warehouse.getCargasAlmacenadas().isEmpty());
    }
    
    @Test
    public void test02_WarehousePuedeRegistrarCargas() {
        warehouse.registrarCarga(cargaMock);

        assertTrue(warehouse.getCargasAlmacenadas().contains(cargaMock));
    }
    
    @Test
    public void test03_WarehouseAplicaServicioAUnaCargaRegistrada() {
        warehouse.registrarCarga(cargaMock);

        warehouse.aplicarServicio(servicioMock, cargaMock);

        verify(cargaMock, times(1)).agregarServicio(servicioMock);
    }

    @Test
    public void test04_WarehouseLanzaExcepcionSiAplicaServicioACargaNoRegistrada() {

        Carga cargaFantasma = Mockito.mock(Carga.class);

        assertThrows(IllegalArgumentException.class, () -> {
            warehouse.aplicarServicio(servicioMock, cargaFantasma);
        });

        verify(cargaFantasma, never()).agregarServicio(servicioMock);
    }
	
	
}
