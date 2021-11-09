package es.ucm.videojuegos.moviles.logica;

import es.ucm.videojuegos.moviles.logica.Square.SquareType;

public class Hint9 implements Hint {
	  
	@Override
	public boolean EsAplicable(Square square, Board board) {
		if(square.getNumber() == 0)	return false;
		//Cuantos azules veo a mi alrededor
		int numVeo = board.lookAround(square.getPos(), 1);
		if(numVeo == square.getNumber()) return false;
		
		//Miro las casillas que tengo a mi alrededor
		int casillasVisibles = board.lookAround(square.getPos(), 2);
		
		//Si las que tengo alrededor JUSTO coincide con las que deber�a ver esta pista es correcta
		return casillasVisibles == square.getNumber();
	}

	@Override
	public void AplicarPista(Square square, Board board) {
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
        for(int i =0 ;i < 4; i++){
        	mirarAlrededorRecursivoEnJuego(square.getPos(),dir[i], board);
        }
	}

	@Override
	public String GenerarAyuda() {
		return "Un numero no esta cerrado y- tiene varias direcciones,- pero la suma alcanzable es el"
				+ "-valor que hay que conseguir ";
	}
	
    private int mirarAlrededorRecursivoEnJuego(Vector2D pos, Vector2D dir, Board board){
        Vector2D nuevaPos = new Vector2D(pos.getX()+ dir.getX(),pos.getY()+ dir.getY());
        if( nuevaPos.getX() < 0 || nuevaPos.getX() >= board.getDimensions() || nuevaPos.getY() < 0 || nuevaPos.getY() >= board.getDimensions() ||   	//Si me he salido de cualquier limite
		board.getTablero()[nuevaPos.getX()][nuevaPos.getY()].getCurrentType() == SquareType.RED)									  	// Si me he encontrado un muro
            return 0;
        else board.getTablero()[nuevaPos.getX()][nuevaPos.getY()].setTipo(SquareType.BLUE);
        return 1 + mirarAlrededorRecursivoEnJuego(nuevaPos,dir, board);
    }
}
