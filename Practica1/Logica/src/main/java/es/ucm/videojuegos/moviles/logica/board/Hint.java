package es.ucm.videojuegos.moviles.logica.board;
/*Inerfaz que absrae las pistas*/
public interface Hint {
	/* Vemos si dado el estado del board la pista actual puede ser aplicable
	 * @param casilla (actual desde la cual se mira)
	 * @param board (del juego)*/
    boolean isApplicable(Square square, Board board);

    /* Aplica la pista al board actual, solo utilizable para generar el board
	 * @param casilla (actual desde la cual se mira)
	 * @param board (del juego)*/
    void applyHint(Square square, Board board);

    /* Genera una cadena de texto con la informacion para el jugador sobre la pista*/
    String generateHelp();
}