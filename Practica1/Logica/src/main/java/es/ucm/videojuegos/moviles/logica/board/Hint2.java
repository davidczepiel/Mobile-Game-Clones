package es.ucm.videojuegos.moviles.logica.board;

import es.ucm.videojuegos.moviles.logica.board.Square.SquareType;

public class Hint2 implements Hint {

	Square currentVoid = null;
	@Override
	public boolean isApplicable(Square square, Board board) {
		if(square.getNumber() == 0 ) return false;
		
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		int numBlueSquares = board.lookAround(square.getPos(), 1);	//Calculamos el numero de azules
		for(int i = 0; i < 4 ; ++i) {
			Square firstVoid = board.searchFirstVoid(dir[i], square.getPos());
			if(firstVoid != null) {
				int voidsIcanSee = board.lookAroundRecParcial(firstVoid.getPos(), dir[i]);	//Vacios que veo en la direccion i
				if (numBlueSquares + voidsIcanSee > square.getNumber()) {					//Si supero el numero establecido por la casilla
					currentVoid = firstVoid;
					return true;									//Significa que puedo poner una pared
				}
			}		
		}
		return false;
	}

	@Override
	public void applyHint(Square square, Board board) {
		currentVoid.setType(SquareType.RED);						//Pongo una pared
	}

	@Override
	public String generateHelp() {
		return "Si pusieramos un punto azul- en una celda vacia superariamos el"
				+ " numero de- visibles del numero y por- tanto debe ser una pared";
	}

}
