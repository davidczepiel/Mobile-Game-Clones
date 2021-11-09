package es.ucm.videojuegos.moviles.logica;

import es.ucm.videojuegos.moviles.logica.Square.SquareType;

public class Hint5 implements Hint {
	@Override
	public boolean EsAplicable(Square square, Board board) {
		if(square.getNumber() == 0) return false;
		
		int azules = board.lookAround(square.getPos(), 2);
		return  azules < square.getNumber() &&
				buscarMuroModificable(square, board) != null;
	}
	
	Square buscarMuroRec(Square square, Board board, Vector2D dir) {
		Vector2D nuevaPos = new Vector2D(square.getPos().getX()+ dir.getX(), square.getPos().getY()+ dir.getY());
		if( nuevaPos.getX() < 0 || nuevaPos.getX() >= board.getDimensions() || nuevaPos.getY() < 0 || nuevaPos.getY() >= board.getDimensions())   //Si me he salido de cualquier limite
			return null;
		
		Square nuevaSquare = board.getTablero()[nuevaPos.getX()][nuevaPos.getY()];
		if(nuevaSquare.getCurrentType() == SquareType.RED && !nuevaSquare.is_modificable())          // Si me he encontrado un muro que no puedo alterar
			return null;
		
		else if(nuevaSquare.getCurrentType() == SquareType.RED && nuevaSquare.is_modificable())
			return nuevaSquare;
		
		return buscarMuroRec(nuevaSquare, board, dir);
	}
	Square buscarMuroModificable(Square square, Board board) {
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		for(int i = 0; i < 4 ; i++) {
			Square aux = buscarMuroRec(square, board, dir[i]);
			if(aux != null) return aux;
		}
		return null;
	}

	@Override
	public void AplicarPista(Square square, Board board) {
		// No es aplicable
	}

	@Override
	public String GenerarAyuda() {
		return "El numero tiene una cantidad insuficiente- de casillas azules visibles y- sin embargo esta cerrado.";
	}

}
