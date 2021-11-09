package es.ucm.videojuegos.moviles.logica;

import es.ucm.videojuegos.moviles.logica.Square.SquareType;

public class Hint3 implements Hint {

	Vector2D _dir;
	
	@Override
	public boolean EsAplicable(Square square, Board board) {
		if(square.getNumber() == 0)	return false;
		//Cuantos azules veo a mi alrededor
		int numVeo = board.lookAround(square.getPos(), 1);
		if(numVeo == square.getNumber()) return false;
		
		//System.out.print("Es aplicable pista 3 en " + casilla.getPos().getX() + ", " + casilla.getPos().getY() + "\n");
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		int[] vaciosVeo = {0,0,0,0}; 
		int posibilidades = 0;
		int faltan = square.getNumber() - numVeo;
		
		int vaciosDisponibles = 0;
		for(int i = 0; i < 4 ; ++i) {
			vaciosVeo[i] = contarVacios(square.getPos(), dir[i], false, board);
			vaciosDisponibles += vaciosVeo[i];
		}
		
		for(int i = 0; i < dir.length; ++i) {
			if(vaciosDisponibles - vaciosVeo[i] < faltan) {
				posibilidades++;
				_dir = dir[i];
			}
		}
		
		if(posibilidades == 1)
			return true;
		
		return false;
	}

	
	@Override
	public void AplicarPista(Square square, Board board) {
		Square aux = board.searchFirstVoid(_dir, square.getPos());
		aux.setTipo(SquareType.BLUE);
	}
	
	

	@Override
	public String GenerarAyuda() {
		return "Si no se coloca un azul en -una posicion vacia no es -posible alcanzar el numero- de azules visibles";
	}
	
	int contarVacios(Vector2D pos,Vector2D dir, boolean contando,  Board board) {
		Vector2D nuevaPos = new Vector2D(pos.getX()+ dir.getX(),pos.getY()+ dir.getY());
		if( (nuevaPos.getX() < 0 || nuevaPos.getX() >= board.getDimensions() || nuevaPos.getY() < 0 || nuevaPos.getY() >= board.getDimensions()) ||
				board.getTablero()[nuevaPos.getX()][nuevaPos.getY()].getCurrentType() == SquareType.RED ||
				contando  && board.getTablero()[nuevaPos.getX()][nuevaPos.getY()].getCurrentType() == SquareType.BLUE)   //Si me he salido de cualquier limite
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
