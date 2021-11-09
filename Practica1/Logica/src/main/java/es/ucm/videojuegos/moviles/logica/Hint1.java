package es.ucm.videojuegos.moviles.logica;
import es.ucm.videojuegos.moviles.logica.Square.SquareType;

public class Hint1 implements Hint {
	
	@Override
	public boolean EsAplicable(Square square, Board board) {
		if(square.getNumber() == 0 ) return false;

		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		//Buscamos si existe alg�n vac�o donde poner rojos
		boolean posible = false;
		for(int i = 0; i < 4 ; ++i) {
			Square sospechoso = board.searchFirstVoid(dir[i], square.getPos());
			posible = (sospechoso != null) || posible;	//Si es null implica que no existe vac�o para esa direcci�n
		}
		return  posible &&		//Si sus casillas vecinas no son vac�as
			   board.lookAround(square.getPos(), 1) == square.getNumber();
	}
	
	@Override
	public void AplicarPista(Square square, Board board) {
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		for(int i = 0; i < 4 ; ++i) {
			Square sospechoso = board.searchFirstVoid(dir[i], square.getPos());
			if(sospechoso != null && sospechoso.getCurrentType() == SquareType.VOID) {
				sospechoso.setTipo(SquareType.RED);
			}
		}
	}

	@Override
	public String GenerarAyuda() {
		return "Si un numero tiene ya visibles- el numero de celdas que dice- entonces se puede cerrar";
	}
	

	

}
