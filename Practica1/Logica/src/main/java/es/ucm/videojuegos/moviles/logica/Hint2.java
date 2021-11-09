package es.ucm.videojuegos.moviles.logica;

import es.ucm.videojuegos.moviles.logica.Square.SquareType;

public class Hint2 implements Hint {

	Square vaciaActual = null;
	@Override
	public boolean EsAplicable(Square square, Board board) {
		if(square.getNumber() == 0 ) return false;
		
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		int numAzules = board.lookAround(square.getPos(), 1);	//Calculamos el numero de azules
		for(int i = 0; i < 4 ; ++i) {
			Square vacio = board.searchFirstVoid(dir[i], square.getPos());
			if(vacio != null) {
				int veVacio = board.lookAroundRecParcial(vacio.getPos(), dir[i]);
				if (numAzules + veVacio > square.getNumber()) {	//Si supero el numero establecido por la casilla
					vaciaActual = vacio;
					return true;									//Significa que puedo poner una pared
				}
			}		
		}
		return false;
	}

	@Override
	public void AplicarPista(Square square, Board board) {
		vaciaActual.setTipo(SquareType.RED);						//Pongo una pared
	}

	@Override
	public String GenerarAyuda() {
		return "Si pusieramos un punto azul- en una celda vacia superariamos el"
				+ " numero de- visibles del numero y por- tanto debe ser una pared";
	}

}
