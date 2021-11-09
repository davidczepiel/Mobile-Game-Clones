package es.ucm.videojuegos.moviles.logica;

public interface Hint {
	/* Vemos si dado el estado del board la pista actual puede ser aplicable
	 * @param casilla (actual desde la cual se mira)
	 * @param board (del juego)*/
    boolean EsAplicable(Square square, Board board);
    /* Aplica la pista al board actual, solo utilizable para generar el board
	 * @param casilla (actual desde la cual se mira)
	 * @param board (del juego)*/
    void AplicarPista(Square square, Board board);
    /* Genera una cadena de texto con la informaci�n para el jugador sobre la pista*/
    String GenerarAyuda();
}