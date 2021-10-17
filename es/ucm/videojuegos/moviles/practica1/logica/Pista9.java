package logica;

import logica.Casilla.Tipo;

public class Pista9 implements Pista{
	  
	@Override
	public boolean EsAplicable(Casilla casilla, Tablero tablero) {
		if(casilla.getNumero() == 0)	return false;
		//Cuantos azules veo a mi alrededor
		int numVeo = tablero.mirarAlrededor(casilla.getPos(), 1);
		if(numVeo == casilla.getNumero()) return false;
		
		int casillasVisibles = 0;
		
		for(int i = 0; i < 4 ; ++i) {
			casillasVisibles += tablero.mirarAlrededor(casilla.getPos(), 2);
		}
		
		return casillasVisibles == casilla.getNumero();
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
