package terminalgestionada;

import java.time.LocalDate;

import gestion.ordenes.OrdenDeExportacion;
import gestion.ordenes.OrdenDeImportacion;
import gestion.terrestre.Camion;
import gestion.terrestre.Cliente;
import gestion.terrestre.EmpresaTransportista;
import gestion.terrestre.Ubicacion;
import logistica.Circuito;
import logistica.EstrategiaDeBusqueda;
import logistica.Naviera;
import warehouse.Buque;



public interface FachadaTerminal {
	Ubicacion getPosicionGeografica();
	boolean esLaTerminal(TerminalGestionada terminal);
	void agregarCliente(Cliente cliente);
	Circuito mejorCircuito(TerminalGestionada destino);
	void registrarNaviera(Naviera naviera);
	void importar(OrdenDeImportacion orden);
	void exportar(OrdenDeExportacion orden);
	void registrarEmpresaTransportista(EmpresaTransportista empresa);
	void setMejorEstrategiaParaCircuito(EstrategiaDeBusqueda estrategia);
	LocalDate proximaFechaDePartida(Buque buque, TerminalGestionada destino);
	void agregarCamion(EmpresaTransportista empresa, Camion camion);
	 
}
