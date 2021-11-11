package es.ucm.videojuegos.moviles.logica;

import es.ucm.videojuegos.moviles.logica.Square.SquareType;

public class Hint8 implements Hint {
	  
	@Override
	public boolean isApplicable(Square square, Board board) {
		if(square.getNumber() == 0 || square.getCurrentType() != SquareType.BLUE) return false;
		
		int numInSight = board.lookAround(square.getPos(), 1);
		
		if(numInSight >= square.getNumber()) return false;
		
		int openDirections = 0;
		
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		for(int i = 0; i < dir.length; ++i) {
			if(countVoids(square.getPos(), dir[i], false, board) > 0) {
				openDirections++;
			}
		}
		
		return openDirections == 1;
	}

	@Override
	public void applyHint(Square square, Board board) {
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		for(int i = 0; i < dir.length; ++i) {
			Square aux = square;
			while(countVoids(square.getPos(), dir[i], false, board) > 0
					&& board.lookAround(square.getPos(), 1) < square.getNumber()) {
				Vector2D nuevaPos = new Vector2D(aux.getPos().getX()+ dir[i].getX(),
						aux.getPos().getY()+ dir[i].getY());
				board.getBoard()[nuevaPos.getX()][nuevaPos.getY()].setType(SquareType.BLUE);
				aux = board.getBoard()[nuevaPos.getX()][nuevaPos.getY()];
			}
		}
	}

	@Override
	public String generateHelp() {
		return "Un numero que no ve suficientes- puntos no esta cerrado aun y- solo tiene abierta una direccion";
	}
	
	int countVoids(Vector2D pos, Vector2D dir, boolean counting, Board board) {
		//Nos movemos a la siguiente posicion
		Vector2D newPos = new Vector2D(pos.getX()+ dir.getX(),pos.getY()+ dir.getY());
		//Si me he salido de cualquier limite
		if( (newPos.getX() < 0 || newPos.getX() >= board.getDimensions() || newPos.getY() < 0 || newPos.getY() >= board.getDimensions()) ||
			board.getBoard()[newPos.getX()][newPos.getY()].getCurrentType() == SquareType.RED ||
			(counting  && board.getBoard()[newPos.getX()][newPos.getY()].getCurrentType() == SquareType.BLUE))
			return 0;

		//Si aun no estoy contando vacios y la casilla es azul, sigo avanzando en la direccion
		else if(!counting && board.getBoard()[newPos.getX()][newPos.getY()].getCurrentType() == SquareType.BLUE)
			return countVoids(newPos, dir, counting, board);

		//Si la casilla es vacia, sumo 1 al numero de vacios que he visto y activo el bool de contando vacios
		else if(board.getBoard()[newPos.getX()][newPos.getY()].getCurrentType() == SquareType.VOID) {
			counting = true;
			return countVoids(newPos, dir, counting, board) + 1;
		}
		
		return 0;
	}

}
