package es.ucm.videojuegos.moviles.logica;

import es.ucm.videojuegos.moviles.logica.Square.SquareType;

public class Hint10 implements Hint {
	  
	@Override
	public boolean isApplicable(Square square, Board board) {
		if(square.getNumber() == 0)	return false; //Si no es un azul NO MODIFICABLE mo tenemos nada que hacer aqui
		
		//Miro que no esta cerrado
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		boolean locked = true;
		for(int i = 0; i < 4 ; i++) 
			locked = locked && seekWall(square.getPos(),dir[i], board); //Si en algun momento no hay pared no estamos encerrados
		if(locked) return false;
		
		//Si las que tengo alrededor son INSUFICIENTES para llegar al numero de puntos que deberia ver
		int numVeo = board.lookAround(square.getPos(), 2);
		return numVeo < square.getNumber();
	}

	@Override
	public void applyHint(Square square, Board board) {
		//Cubierta por pista 3
	}

	/*Busca una pared dado una posicion y una direccion
	* @param pos posicion origen
	* @param dir direccion a buscar
	* @param board referencia a tablero*/
	private boolean seekWall(Vector2D pos, Vector2D dir, Board board){
		Vector2D nuevaPos = new Vector2D(pos.getX()+ dir.getX(),pos.getY()+ dir.getY());
		if( nuevaPos.getX() < 0 || nuevaPos.getX() >= board.getDimensions() || nuevaPos.getY() < 0 || nuevaPos.getY() >= board.getDimensions() ||   	//Si me he salido de cualquier limite
				board.getBoard()[nuevaPos.getX()][nuevaPos.getY()].getCurrentType() == SquareType.RED)									  	// Si me he encontrado un muro
			return true;
		
		return false;		
	}
	
	@Override
	public String generateHelp() {
		return "Un numero no esta cerrado- y tiene varias direcciones-, pero la suma alcanzable no es el"
				+ "-valor que hay que conseguir ";
	}
}
