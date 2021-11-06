package es.ucm.videojuegos.moviles.logica;

import java.util.ArrayList;

import es.ucm.videojuegos.moviles.engine.Pair;

/*Clase que gestiona las pistas desde su creación hasta su almacenamiento.
 *Contiene metodos para comprobar si dado un tablero este es valido además de
 * proporcionar una pista al jugador dado un tablero actual.
 */
public class GestorDePistas {

	public GestorDePistas() {
		this._pistasAplicables = new ArrayList<Pista>();
		this._pistasInformativas = new ArrayList<Pista>();
		
		Pista pista = new Pista1();
		this._pistasAplicables.add(pista);
		this._pistasInformativas.add(pista);
		
		pista = new Pista2();
		this._pistasAplicables.add(pista);
		this._pistasInformativas.add(pista);
		
		pista = new Pista8();
		this._pistasAplicables.add(pista);
		this._pistasInformativas.add(pista);
		
		pista = new Pista9();
		this._pistasAplicables.add(pista);
		this._pistasInformativas.add(pista);
		
		pista = new Pista3();
		this._pistasAplicables.add(pista);
		this._pistasInformativas.add(pista);
		
		pista = new Pista6();
		this._pistasInformativas.add(pista);
		this._pistasAplicables.add(pista);
		
		pista = new Pista7();
		this._pistasInformativas.add(pista);
		this._pistasAplicables.add(pista);
		//-----------------------------------
		pista = new Pista4();
		this._pistasInformativas.add(pista);
		
		pista = new Pista5();
		this._pistasInformativas.add(pista);
		
		pista = new Pista10();
		this._pistasInformativas.add(pista);
	}
	/* Comprueba si el tablero dado es válido y jugable dadas las pistas existentes*/
	public boolean esValido(Tablero tablero) {
		Casilla[][] tableroJuego = tablero.getTablero();
		int size = tablero.getDimensiones();
		while(tablero.getNumVacias() > 0) {
			boolean checkPista = false;	//si se ha realizado una pista en ela iteraccion actual
			for(int i = 0; i < size ; i++) {
				for(int j = 0; j < size ; j++) {
					checkPista = pruebaPista(tableroJuego[i][j], tablero);
					if(checkPista) {
						if(DEBUG)
							tablero.debugEstadoTableros();
						break;
					}
				}
				if(checkPista) break;
			}
			if(!checkPista) return false;	//si no se ha realizado una pista el tablero no es jugable
			
		}
		return true;
	}
	/* Devuelve una pista escrita dado el estado actual del tablero de juego*/
	public Pair<String,Vector2D> damePista(Tablero tablero) {
		Casilla[][] tableroJuego = tablero.getTablero();
		int size = tablero.getDimensiones();
		for(int i = 0; i < size ; i++) {
			for(int j = 0; j < size ; j++) {
				for(Pista pista: this._pistasInformativas) {
					if(pista.EsAplicable(tableroJuego[i][j], tablero)){
						String texto = pista.GenerarAyuda();
						if(texto!= null)
							return new Pair(texto,tableroJuego[i][j].getPos());
					}
				}
			}
		}
		return null;
	}
	/*Prueba las pistas disponibles dada la casilla y el tablero*/
	private boolean pruebaPista(Casilla casilla, Tablero tablero) {
		for(Pista pista: this._pistasAplicables) {
			if(pista.EsAplicable(casilla, tablero)) {
				pista.AplicarPista(casilla, tablero);	
				return true;
			}
		}
		return false;
	}
	
	private ArrayList<Pista> _pistasInformativas;	//Lista con las pistas informativas para el jugador
	private ArrayList<Pista> _pistasAplicables;		//Lista con las pistas utiles para la generacin del tablero
	private boolean DEBUG = true;
}
