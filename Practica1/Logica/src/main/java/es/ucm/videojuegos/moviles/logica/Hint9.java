package es.ucm.videojuegos.moviles.logica;

import es.ucm.videojuegos.moviles.logica.Square.SquareType;

public class Hint9 implements Hint {
	  
	@Override
	public boolean isApplicable(Square square, Board board) {
		if(square.getNumber() == 0)	return false;
		//Cuantos azules veo a mi alrededor
		int numinSight = board.lookAround(square.getPos(), 1);
		if(numinSight == square.getNumber()) return false;
		
		//Miro las casillas que tengo a mi alrededor
		int visibleSquares = board.lookAround(square.getPos(), 2);
		
		//Si las que tengo alrededor JUSTO coincide con las que deber�a ver esta pista es correcta
		return visibleSquares == square.getNumber();
	}

	@Override
	public void applyHint(Square square, Board board) {
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
        for(int i =0 ;i < 4; i++){
        	completeVoidsAroundRecInGame(square.getPos(),dir[i], board);
        }
	}

	@Override
	public String generateHelp() {
		return "Un numero no esta cerrado y- tiene varias direcciones,- pero la suma alcanzable es el"
				+ "-valor que hay que conseguir ";
	}

	/*Metodo recursivo para poner a azul todos los vacios que tengo a la vista desde una posicion
	* @param pos posicion de origen
	* @param dir direccion a buscar
	* @param board referencia al tablero*/
    private int completeVoidsAroundRecInGame(Vector2D pos, Vector2D dir, Board board){
        Vector2D newPos = new Vector2D(pos.getX()+ dir.getX(),pos.getY()+ dir.getY());
        if( newPos.getX() < 0 || newPos.getX() >= board.getDimensions() || newPos.getY() < 0 || newPos.getY() >= board.getDimensions() ||   	//Si me he salido de cualquier limite
		board.getBoard()[newPos.getX()][newPos.getY()].getCurrentType() == SquareType.RED)									  	// Si me he encontrado un muro
            return 0;
        else board.getBoard()[newPos.getX()][newPos.getY()].setType(SquareType.BLUE);
        return 1 + completeVoidsAroundRecInGame(newPos,dir, board);
    }
}
