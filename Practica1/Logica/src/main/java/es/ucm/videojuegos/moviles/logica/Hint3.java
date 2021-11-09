package es.ucm.videojuegos.moviles.logica;

import es.ucm.videojuegos.moviles.logica.Square.SquareType;

public class Hint3 implements Hint {

	Vector2D _dir;
	
	@Override
	public boolean isApplicable(Square square, Board board) {
		if(square.getNumber() == 0)	return false;
		//Cuantos azules veo a mi alrededor
		int bluesInSight = board.lookAround(square.getPos(), 1);
		if(bluesInSight == square.getNumber()) return false;
		
		//System.out.print("Es aplicable pista 3 en " + casilla.getPos().getX() + ", " + casilla.getPos().getY() + "\n");
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		int[] voidsInSight = {0,0,0,0};
		int posibilities = 0;
		int left = square.getNumber() - bluesInSight;
		
		int voidsAvailable = 0;
		//Contamos los vacios que se ven en cada direccion
		for(int i = 0; i < 4 ; ++i) {
			voidsInSight[i] = countVoids(square.getPos(), dir[i], false, board);
			voidsAvailable += voidsInSight[i];
		}

		//Si prescindimos de los vacios de una direccion y no podemos llegar al valor, necesitamos esa direccion
		for(int i = 0; i < dir.length; ++i) {
			if(voidsAvailable - voidsInSight[i] < left) {
				posibilities++;
				_dir = dir[i];
			}
		}

		//Si solo es prescindible una direccion, necesitamos poner un azul como minimo en el primer vacio de la cual
		if(posibilities == 1)
			return true;
		
		return false;
	}

	
	@Override
	public void applyHint(Square square, Board board) {
		Square aux = board.searchFirstVoid(_dir, square.getPos());
		aux.setType(SquareType.BLUE);
	}
	
	

	@Override
	public String generateHelp() {
		return "Si no se coloca un azul en -una posicion vacia no es -posible alcanzar el numero- de azules visibles";
	}

	/*Metodo recursivo que cuenta el numero de vacios que pueden extender el numero de azules que ve una casilla
	* @param pos posicion inicial de la busqueda
	* @param dir direccion a buscar
	* @param counting booleano de control para no contar los azules pegados a la casilla origen
	* @param counting referencia al tablero
	* */
	int countVoids(Vector2D pos, Vector2D dir, boolean counting, Board board) {
		//nos situamos en la nueva posicion
		Vector2D newPos = new Vector2D(pos.getX()+ dir.getX(),pos.getY()+ dir.getY());

		//Si me he salido de cualquier limite
		if( (newPos.getX() < 0 || newPos.getX() >= board.getDimensions() || newPos.getY() < 0 || newPos.getY() >= board.getDimensions()) ||
				board.getBoard()[newPos.getX()][newPos.getY()].getCurrentType() == SquareType.RED ||
				counting  && board.getBoard()[newPos.getX()][newPos.getY()].getCurrentType() == SquareType.BLUE)
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
