package gestion.terrestre;

public class Ubicacion {
    public int x;
    public int y;
	double metricaUtilizada;

    public Ubicacion(int x, int y) {
        this.x = x;
        this.y = y;
    
    }

    public int getX() { 
    	return x; 
    }
    public int getY() { 
    	return y; 
    }


    public double distanciaA(Ubicacion ubicacionDestino,double metricaDeDistancia) {
        int distanciaX = ubicacionDestino.x - this.x;
        int distanciaY = ubicacionDestino.y - this.y;
        
        double distancia = this.aplicarPitagorasA(distanciaX, distanciaY);
        
        return distancia * metricaDeDistancia; 
    }
    
    public double aplicarPitagorasA(int dx, int dy) {
    	// Calcula la distancia entre dos puntos de un eje
    	return Math.sqrt(dx * dx + dy * dy);
    }

  
	public boolean esLaUbicacion(Ubicacion posicionGeografica) {
		return x == posicionGeografica.ejeX() && y == posicionGeografica.ejeY();
	}

	public int ejeY() {
		return y;
	}

	public int ejeX() {
		
		return x;
	}
}