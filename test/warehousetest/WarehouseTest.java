package warehousetest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import warehouse.Warehouse;

public class WarehouseTest {
	
	Warehouse warehouse;
	
	@BeforeEach
	public void setUp() {
		warehouse = new Warehouse();
	}
	
	@Test
	public void test1() {
		assertTrue(false);
	}
	
	
}
