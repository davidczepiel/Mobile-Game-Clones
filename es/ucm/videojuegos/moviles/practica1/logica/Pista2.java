package logica;

import logica.Casilla.Tipo;

public class Pista2 implements Pista{

	Casilla vaciaActual = null;
	@Override
	public boolean EsAplicable(Casilla casilla, Tablero tablero) {
		
		if(casilla.getNumero() == 0 ) return false;
		
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		int numAzules = tablero.mirarAlrededor(casilla.getPos(), 1);	//Calculamos el numero de azules
		for(int i = 0; i < 4 ; ++i) {
			Casilla vacio = tablero.buscarPrimerVacio(dir[i], casilla.getPos());		
			if(vacio != null) {
				int veVacio = tablero.mirarAlrededorRecursivoParcial(vacio.getPos(), dir[i]);
				if (numAzules + veVacio > casilla.getNumero()) {	//Si supero el número establecido por la casilla
					vaciaActual = vacio;
					return true;									//Significa que puedo poner una pared
				}
			}		
		}
		return false;
	}

	@Override
	public void AplicarPista(Casilla casilla, Tablero tablero) {
		vaciaActual.setTipo(Tipo.ROJO);						//Pongo una pared
		tablero.modificarVacias(-1);
	}

	@Override
	public String GenerarAyuda() {
		return "Si pusieramos un punto azul en una celda vacía superaríamos el"
				+ " número de visibles del número y por tanto debe ser una pared";
	}

}
