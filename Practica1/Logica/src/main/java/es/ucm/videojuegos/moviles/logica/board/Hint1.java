package es.ucm.videojuegos.moviles.logica.board;
import es.ucm.videojuegos.moviles.logica.board.Square.SquareType;

public class Hint1 implements Hint {
	
	@Override
	public boolean isApplicable(Square square, Board board) {
		if(square.getNumber() == 0 ) return false;

		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		//Buscamos si existe algun vacio donde poner rojos
		boolean posible = false;
		for(int i = 0; i < 4 ; ++i) {
			Square firstVoid = board.searchFirstVoid(dir[i], square.getPos());
			posible = (firstVoid != null) || posible;	//Si es null implica que no existe vacio para esa direccion
		}
		return  posible &&		//Si sus casillas vecinas no son vacias
			   board.lookAround(square.getPos(), 1) == square.getNumber();
	}
	/*Cierra las casillas alrededor del azul*/
	@Override
	public void applyHint(Square square, Board board) {
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		for(int i = 0; i < 4 ; ++i) {
			Square firstVoid = board.searchFirstVoid(dir[i], square.getPos());
			if(firstVoid != null && firstVoid.getCurrentType() == SquareType.VOID) {
				firstVoid.setType(SquareType.RED);
			}
		}
	}

	@Override
	public String generateHelp() {
		return "Si un numero tiene ya visibles- el numero de celdas que dice- entonces se puede cerrar";
	}
	

	

}
