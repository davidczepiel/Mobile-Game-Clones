package es.ucm.videojuegos.moviles.logica;

import java.util.ArrayList;

import es.ucm.videojuegos.moviles.engine.Pair;

/*Clase que gestiona las pistas desde su creación hasta su almacenamiento.
 *Contiene metodos para comprobar si dado un tablero este es valido además de
 * proporcionar una pista al jugador dado un tablero actual.
 */
public class HintsManager {

	public HintsManager() {
		this._pistasAplicables = new ArrayList<Hint>();
		this._pistasInformativas = new ArrayList<Hint>();
		
		Hint hint = new Hint1();
		this._pistasAplicables.add(hint);
		this._pistasInformativas.add(hint);
		
		hint = new Hint2();
		this._pistasAplicables.add(hint);
		this._pistasInformativas.add(hint);
		
		hint = new Hint8();
		this._pistasAplicables.add(hint);
		this._pistasInformativas.add(hint);
		
		hint = new Hint9();
		this._pistasAplicables.add(hint);
		this._pistasInformativas.add(hint);
		
		hint = new Hint3();
		this._pistasAplicables.add(hint);
		this._pistasInformativas.add(hint);
		
		hint = new Hint6();
		this._pistasInformativas.add(hint);
		this._pistasAplicables.add(hint);
		
		hint = new Hint7();
		this._pistasInformativas.add(hint);
		this._pistasAplicables.add(hint);
		//-----------------------------------
		hint = new Hint4();
		this._pistasInformativas.add(hint);
		
		hint = new Hint5();
		this._pistasInformativas.add(hint);
		
		hint = new Hint10();
		this._pistasInformativas.add(hint);
	}

	/* Comprueba si el board dado es válido y jugable dadas las pistas existentes
	* @param board Board que se va a comprobar
	* @return boolean que reperesenta si el board que se ha pasado es apto para ser juagado*/
	public boolean isValid(Board board) {
		Square[][] tableroJuego = board.getTablero();
		int size = board.getDimensions();
		while(board.getCurrentNumberOfVoid() > 0) {
			boolean checkPista = false;	//si se ha realizado una pista en ela iteraccion actual
			for(int i = 0; i < size ; i++) {
				for(int j = 0; j < size ; j++) {
					checkPista = pruebaPista(tableroJuego[i][j], board);
					if(checkPista) {
						if(DEBUG)
							board.debugBoardState();
						break;
					}
				}
				if(checkPista) break;
			}
			if(!checkPista) return false;	//si no se ha realizado una pista el board no es jugable
			
		}
		return true;
	}

	/* Devuelve una pista escrita dado el estado actual del board de juego
	* @param board Board que vamos a analizar para conseguir una pista
	* @return pareja que incluye tanto la pista a data como la casilla sobre la que se aplica*/
	public Pair<String,Vector2D> getHint(Board board) {
		Square[][] tableroJuego = board.getTablero();
		int size = board.getDimensions();
		for(int i = 0; i < size ; i++) {
			for(int j = 0; j < size ; j++) {
				for(Hint hint : this._pistasInformativas) {
					if(hint.EsAplicable(tableroJuego[i][j], board)){
						String texto = hint.GenerarAyuda();
						if(texto!= null)
							return new Pair(texto,tableroJuego[i][j].getPos());
					}
				}
			}
		}
		return null;
	}

	/*Prueba las pistas disponibles dada la casilla y el board
	* @param casilla Casilla sobre la que se van a probar las pistas
	* @param board Board del juego
	* @return boolean que representa si se ha podido aplicar alguna pista o no*/
	private boolean pruebaPista(Square square, Board board) {
		for(Hint hint : this._pistasAplicables) {
			if(hint.EsAplicable(square, board)) {
				hint.AplicarPista(square, board);
				return true;
			}
		}
		return false;
	}
	
	private ArrayList<Hint> _pistasInformativas;	//Lista con las pistas informativas para el jugador
	private ArrayList<Hint> _pistasAplicables;		//Lista con las pistas utiles para la generacin del tablero
	private boolean DEBUG = false;
}
