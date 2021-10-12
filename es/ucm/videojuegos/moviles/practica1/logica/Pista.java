package logica;

public interface Pista {
	/* Vemos si dado el estado del tablero la pista actual puede ser aplicable
	 * @param casilla (actual desde la cual se mira)
	 * @param tablero (del juego)*/
    boolean EsAplicable(Casilla casilla,Tablero tablero);
    /* Aplica la pista al tablero actual, solo utilizable para generar el tablero
	 * @param casilla (actual desde la cual se mira)
	 * @param tablero (del juego)*/
    void AplicarPista(Casilla casilla,Tablero tablero);
    /* Genera una cadena de texto con la información para el jugador sobre la pista*/
    String GenerarAyuda();
}