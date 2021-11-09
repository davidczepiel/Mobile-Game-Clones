package es.ucm.videojuegos.moviles.logica;

import es.ucm.videojuegos.moviles.logica.Square.SquareType;

public class Hint10 implements Hint {
	  
	@Override
	public boolean EsAplicable(Square square, Board board) {
		if(square.getNumber() == 0)	return false; //Si no es un azul NO MODIFICABLE mo tenemos nada que hacer aqui
		
		//Miro que no est� cerrado
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		boolean encerrado = true;
		for(int i = 0; i < 4 ; i++) 
			encerrado = encerrado && mirarPared(square.getPos(),dir[i], board); //Si en algun momento no hay pared no estamos encerrados
		if(encerrado) return false;
		
		//Si las que tengo alrededor son INSUFICIENTES para llegar al n�mero de puntos que deber�a ver 
		int numVeo = board.lookAround(square.getPos(), 2);
		return numVeo < square.getNumber();
	}

	@Override
	public void AplicarPista(Square square, Board board) {
		//Cubierta por pista 3
	}

	
	private boolean mirarPared(Vector2D pos, Vector2D dir, Board board){
		Vector2D nuevaPos = new Vector2D(pos.getX()+ dir.getX(),pos.getY()+ dir.getY());
		if( nuevaPos.getX() < 0 || nuevaPos.getX() >= board.getDimensions() || nuevaPos.getY() < 0 || nuevaPos.getY() >= board.getDimensions() ||   	//Si me he salido de cualquier limite
				board.getTablero()[nuevaPos.getX()][nuevaPos.getY()].getCurrentType() == SquareType.RED)									  	// Si me he encontrado un muro
			return true;
		
		return false;		
	}
	
	@Override
	public String GenerarAyuda() {
		return "Un numero no esta cerrado- y tiene varias direcciones-, pero la suma alcanzable no es el"
				+ "-valor que hay que conseguir ";
	}
}
