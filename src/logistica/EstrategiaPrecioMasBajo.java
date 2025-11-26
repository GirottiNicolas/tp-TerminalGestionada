package logistica;

import java.util.Comparator;
import java.util.List;

import terminalgestionada.TerminalGestionada;

public class EstrategiaPrecioMasBajo implements EstrategiaDeBusqueda {

    @Override
    public Circuito seleccionarMejorCircuito(List<Circuito> circuitos, TerminalGestionada origen, TerminalGestionada destino) {
        return circuitos.stream()
                .filter(c -> c.contieneTerminal(origen) && c.contieneTerminal(destino))
                .min(Comparator.comparingDouble(c -> c.precioTotalEntre(origen, destino)))
                .orElseThrow(() -> new IllegalArgumentException("No hay circuitos que conecten el origen con el destino."));
    }
}
