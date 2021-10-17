package logica;

import logica.Casilla.Tipo;

public class Pista10 implements Pista{
	  
	@Override
	public boolean EsAplicable(Casilla casilla, Tablero tablero) {
		
	}

	@Override
	public void AplicarPista(Casilla casilla, Tablero tablero) {
		//Cubierta por pista 3
	}

	@Override
	public String GenerarAyuda() {
		return "Un numero no esta cerrado y tiene varias direcciones, pero la suma alcanzable es el\r\n"
				+ "valor que hay que conseguir ";
	}
}
