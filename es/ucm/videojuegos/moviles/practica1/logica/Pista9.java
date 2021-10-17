package logica;

import logica.Casilla.Tipo;

public class Pista9 implements Pista{
	  
	@Override
	public boolean EsAplicable(Casilla casilla, Tablero tablero) {
		if(casilla.getNumero() == 0)	return false;
		//Cuantos azules veo a mi alrededor
		int numVeo = tablero.mirarAlrededor(casilla.getPos(), 1);
		if(numVeo == casilla.getNumero()) return false;
		
		//Miro las casillas que tengo a mi alrededor
		int casillasVisibles = tablero.mirarAlrededor(casilla.getPos(), 2);
		
		//Si las que tengo alrededor JUSTO coincide con las que debería ver esta pista es correcta
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
