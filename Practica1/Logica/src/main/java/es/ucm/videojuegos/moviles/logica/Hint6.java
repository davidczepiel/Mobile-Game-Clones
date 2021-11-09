package es.ucm.videojuegos.moviles.logica;

import es.ucm.videojuegos.moviles.logica.Square.SquareType;

public class Hint6 implements Hint {
   
	@Override
	public boolean EsAplicable(Square square, Board board) {
		if(square.getCurrentType() != SquareType.VOID) return false;
		
		return  buscaAzulNoMod(square, board) == null; //Si no se ha encontrtado nada es que estamos ante una pared cerrada
	}

	@Override
	public void AplicarPista(Square square, Board board) {
		square.setTipo(SquareType.RED);
	}

	@Override
	public String GenerarAyuda() {
		return "Hay una celda vacia que no -ve ninguna azul, por lo que- deberia ser una pared";
	}
	
	private Square buscaAzulNoModRec(Vector2D pos, Vector2D dir, Board board){
		Vector2D nuevaPos = new Vector2D(pos.getX()+ dir.getX(),pos.getY()+ dir.getY());
		if( nuevaPos.getX() < 0 || nuevaPos.getX() >= board.getDimensions() || nuevaPos.getY() < 0 || nuevaPos.getY() >= board.getDimensions() ||   	//Si me he salido de cualquier limite
				board.getTablero()[nuevaPos.getX()][nuevaPos.getY()].getCurrentType() == SquareType.RED)									  	// Si me he encontrado un muro
			return null;
		
		if(board.getTablero()[nuevaPos.getX()][nuevaPos.getY()].getCurrentType() == SquareType.BLUE &&
				board.getTablero()[nuevaPos.getX()][nuevaPos.getY()].getNumber() > 0 )
			return board.getTablero()[nuevaPos.getX()][nuevaPos.getY()];
		
		return buscaAzulNoModRec(nuevaPos,dir, board);
	}
	
	Square buscaAzulNoMod(Square square, Board board) {
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		for(int i = 0; i < 4 ; i++) {
			Square aux = buscaAzulNoModRec(square.getPos(),dir[i], board);
			if(aux != null) return aux;
		}
		return null;
	}
	

}
