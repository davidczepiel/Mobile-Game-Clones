package es.ucm.videojuegos.moviles.logica;

import es.ucm.videojuegos.moviles.logica.Square.SquareType;

public class Hint8 implements Hint {
	  
	@Override
	public boolean EsAplicable(Square square, Board board) {
		if(square.getNumber() == 0 || square.getCurrentType() != SquareType.BLUE) return false;
		
		int numVeo = board.lookAround(square.getPos(), 1);
		
		if(numVeo >= square.getNumber()) return false;
		
		int direccionesAbiertas = 0;
		
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		for(int i = 0; i < dir.length; ++i) {
			if(contarVacios(square.getPos(), dir[i], false, board) > 0) {
				direccionesAbiertas++;
			}
		}
		
		return direccionesAbiertas == 1;
	}

	@Override
	public void AplicarPista(Square square, Board board) {
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		for(int i = 0; i < dir.length; ++i) {
			Square aux = square;
			while(contarVacios(square.getPos(), dir[i], false, board) > 0) {
				Vector2D nuevaPos = new Vector2D(aux.getPos().getX()+ dir[i].getX(),
						aux.getPos().getY()+ dir[i].getY());
				board.getTablero()[nuevaPos.getX()][nuevaPos.getY()].setTipo(SquareType.BLUE);
				aux = board.getTablero()[nuevaPos.getX()][nuevaPos.getY()];
			}
		}
	}

	@Override
	public String GenerarAyuda() {
		return "Un numero que no ve suficientes- puntos no esta cerrado aun y- solo tiene abierta una direccion";
	}
	
	int contarVacios(Vector2D pos,Vector2D dir, boolean contando,  Board board) {
		Vector2D nuevaPos = new Vector2D(pos.getX()+ dir.getX(),pos.getY()+ dir.getY());
		if( (nuevaPos.getX() < 0 || nuevaPos.getX() >= board.getDimensions() || nuevaPos.getY() < 0 || nuevaPos.getY() >= board.getDimensions()) ||
			board.getTablero()[nuevaPos.getX()][nuevaPos.getY()].getCurrentType() == SquareType.RED ||
			(contando  && board.getTablero()[nuevaPos.getX()][nuevaPos.getY()].getCurrentType() == SquareType.BLUE))   //Si me he salido de cualquier limite
			return 0;
		
		else if(!contando && board.getTablero()[nuevaPos.getX()][nuevaPos.getY()].getCurrentType() == SquareType.BLUE)
			return contarVacios(nuevaPos, dir, contando, board);
		
		else if(board.getTablero()[nuevaPos.getX()][nuevaPos.getY()].getCurrentType() == SquareType.VOID) {
			contando = true;
			return contarVacios(nuevaPos, dir, contando, board) + 1;
		}
		
		return 0;
	}

}
