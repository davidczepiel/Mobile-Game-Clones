package es.ucm.videojuegos.moviles.logica.board;

import es.ucm.videojuegos.moviles.logica.board.Square.SquareType;

public class Hint7 implements Hint {

	@Override
	public boolean isApplicable(Square square, Board board) {
		if( square.getNumber() != 0 || (square.getCurrentType() != SquareType.BLUE)) return false;
		
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		for(int i = 0; i < 4 ; i++) {
			boolean aux = seekWall(square.getPos(),dir[i], board);
			if(!aux) return false;
		}	
		return true;
	}

	@Override
	public void applyHint(Square square, Board board) {
		square.setType(SquareType.RED);
	}

	@Override
	public String generateHelp() {
		return "Hay una celda azul cerrada -que no ve ninguna otra azul,- lo cual no es posible";
	}

	/*Busca una pared dado un origen y una direccion
	* @param pos posicion origen
	* @param dir direccion a buscar
	* @param board referencia al tablero
	* */
	private boolean seekWall(Vector2D pos, Vector2D dir, Board board){
		Vector2D newPos = new Vector2D(pos.getX()+ dir.getX(),pos.getY()+ dir.getY());
		if( newPos.getX() < 0 || newPos.getX() >= board.getDimensions() || newPos.getY() < 0 || newPos.getY() >= board.getDimensions() ||   	//Si me he salido de cualquier limite
				board.getBoard()[newPos.getX()][newPos.getY()].getCurrentType() == SquareType.RED)									  			// Si me he encontrado un muro
			return true;
		
		return false;		
	}
}

