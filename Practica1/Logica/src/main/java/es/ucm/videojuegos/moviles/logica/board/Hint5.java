package es.ucm.videojuegos.moviles.logica.board;

import es.ucm.videojuegos.moviles.logica.board.Square.SquareType;

public class Hint5 implements Hint {
	@Override
	public boolean isApplicable(Square square, Board board) {
		if(square.getNumber() == 0) return false;
		
		int blues = board.lookAround(square.getPos(), 2);
		return  blues < square.getNumber() &&
				searchModificableWall(square, board) != null;
	}

	/*Busca un muro puesto por el jugador desde una casilla origen*/
	Square searchModificableWall(Square square, Board board) {
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		for(int i = 0; i < 4 ; i++) {
			Square aux = searchWallRec(square, dir[i], board);
			if(aux != null) return aux;
		}
		return null;
	}

	/*Metodo recursivo que busca una pared puesta por el jugador
	* @param square origen ed la busqueda
	* @param dir direcion a buscar
	* @param board referencia al tablero
	* */
	Square searchWallRec(Square square, Vector2D dir,Board board) {
		Vector2D newPos = new Vector2D(square.getPos().getX()+ dir.getX(), square.getPos().getY()+ dir.getY());
		if( newPos.getX() < 0 || newPos.getX() >= board.getDimensions() || newPos.getY() < 0 || newPos.getY() >= board.getDimensions())   //Si me he salido de cualquier limite
			return null;
		
		Square newSquare = board.getBoard()[newPos.getX()][newPos.getY()];
		if(newSquare.getCurrentType() == SquareType.RED && !newSquare.is_modificable())          // Si me he encontrado un muro que no puedo alterar
			return null;
		
		else if(newSquare.getCurrentType() == SquareType.RED && newSquare.is_modificable())
			return newSquare;
		
		return searchWallRec(newSquare, dir, board);
	}

	@Override
	public void applyHint(Square square, Board board) {
		// No es aplicable
	}

	@Override
	public String generateHelp() {
		return "El numero tiene una cantidad insuficiente- de casillas azules visibles y- sin embargo esta cerrado.";
	}

}
