package es.ucm.videojuegos.moviles.logica;

import es.ucm.videojuegos.moviles.logica.Square.SquareType;

public class Hint6 implements Hint {
   
	@Override
	public boolean isApplicable(Square square, Board board) {
		if(square.getCurrentType() != SquareType.VOID) return false;
		
		return  searchNoModdificableBlue(square, board) == null; //Si no se ha encontrtado nada es que estamos ante una pared cerrada
	}

	@Override
	public void applyHint(Square square, Board board) {
		square.setType(SquareType.RED);
	}

	@Override
	public String generateHelp() {
		return "Hay una celda vacia que no -ve ninguna azul, por lo que- deberia ser una pared";
	}

	/*Busca al menos una casilla azul no modificable en todas direcciones (Exclusivo de la pista 6)*/
	Square searchNoModdificableBlue(Square square, Board board) {
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		for(int i = 0; i < 4 ; i++) {
			Square aux = searchNoModdifiedBlueRec(square.getPos(),dir[i], board);
			if(aux != null) return aux;
		}
		return null;
	}

	/*Metodo recursivo que busca una casilla azul no modificable dado una direccion
	* @param pos posicion origen
	* @param dir direccion a buscar
	* @param board referencia al tablero
	* */
	private Square searchNoModdifiedBlueRec(Vector2D pos, Vector2D dir, Board board){
		Vector2D nuevaPos = new Vector2D(pos.getX()+ dir.getX(),pos.getY()+ dir.getY());
		if( nuevaPos.getX() < 0 || nuevaPos.getX() >= board.getDimensions() || nuevaPos.getY() < 0 || nuevaPos.getY() >= board.getDimensions() ||   	//Si me he salido de cualquier limite
				board.getBoard()[nuevaPos.getX()][nuevaPos.getY()].getCurrentType() == SquareType.RED)									  	// Si me he encontrado un muro
			return null;
		
		if(board.getBoard()[nuevaPos.getX()][nuevaPos.getY()].getCurrentType() == SquareType.BLUE &&
				board.getBoard()[nuevaPos.getX()][nuevaPos.getY()].getNumber() > 0 )
			return board.getBoard()[nuevaPos.getX()][nuevaPos.getY()];
		
		return searchNoModdifiedBlueRec(nuevaPos,dir, board);
	}
}
