package logistica;

import java.util.List;

import terminalgestionada.TerminalGestionada;

public interface EstrategiaDeBusqueda {
	Circuito seleccionarMejorCircuito(List<Circuito> circuitos, TerminalGestionada destino);
}
