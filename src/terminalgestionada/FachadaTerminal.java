package terminalgestionada;

import gestion.ordenes.OrdenDeExportacion;
import gestion.ordenes.OrdenDeImportacion;
import gestion.terrestre.Cliente;
import gestion.terrestre.EmpresaTransportista;
import gestion.terrestre.Ubicacion;
import gestion.terrestre.dummies.Circuito;
import gestion.terrestre.dummies.Naviera;


public interface FachadaTerminal {
	Ubicacion getPosicionGeografica();
	boolean esLaTerminal(TerminalGestionada terminal);
	void agregarCliente(Cliente cliente);
	Circuito mejorCircuito();
	void registrarNaviera(Naviera naviera);
	void importar(OrdenDeImportacion orden);
	void exportar(OrdenDeExportacion orden);
	void registrarEmpresaTransportista(EmpresaTransportista empresa);
	//void setMejorEstrategiaParaCircuito(EstrategiaDeCircuito estrategia);
	// LocalDateTime proximaFechaDePartida(Buque buque, Terminal destino);
	
}
