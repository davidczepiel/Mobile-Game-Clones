package es.ucm.videojuegos.moviles.logica;

import es.ucm.videojuegos.moviles.logica.Casilla.Tipo;

public class Pista9 implements Pista{
	  
	@Override
	public boolean EsAplicable(Casilla casilla, Tablero tablero) {
		if(casilla.getNumero() == 0)	return false;
		//Cuantos azules veo a mi alrededor
		int numVeo = tablero.mirarAlrededor(casilla.getPos(), 1);
		if(numVeo == casilla.getNumero()) return false;
		
		//Miro las casillas que tengo a mi alrededor
		int casillasVisibles = tablero.mirarAlrededor(casilla.getPos(), 2);
		
		//Si las que tengo alrededor JUSTO coincide con las que deber�a ver esta pista es correcta
		return casillasVisibles == casilla.getNumero();
	}

	@Override
	public void AplicarPista(Casilla casilla, Tablero tablero) {
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
        for(int i =0 ;i < 4; i++){
        	mirarAlrededorRecursivoEnJuego(casilla.getPos(),dir[i], tablero);
        }
	}

	@Override
	public String GenerarAyuda() {
		return "Un numero no esta cerrado y tiene varias direcciones, pero la suma alcanzable es el\r\n"
				+ "valor que hay que conseguir ";
	}
	
    private int mirarAlrededorRecursivoEnJuego(Vector2D pos, Vector2D dir, Tablero tablero){
        Vector2D nuevaPos = new Vector2D(pos.getX()+ dir.getX(),pos.getY()+ dir.getY());
        if( nuevaPos.getX() < 0 || nuevaPos.getX() >= tablero.getDimensiones() || nuevaPos.getY() < 0 || nuevaPos.getY() >= tablero.getDimensiones() ||   	//Si me he salido de cualquier limite
		tablero.getTablero()[nuevaPos.getX()][nuevaPos.getY()].getTipoActual() == Tipo.ROJO)									  	// Si me he encontrado un muro
            return 0;
        else tablero.getTablero()[nuevaPos.getX()][nuevaPos.getY()].setTipo(Tipo.AZUL);
        return 1 + mirarAlrededorRecursivoEnJuego(nuevaPos,dir, tablero);
    }
}
