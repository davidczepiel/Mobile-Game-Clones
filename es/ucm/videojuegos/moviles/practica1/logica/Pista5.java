package logica;

import logica.Casilla.Tipo;

public class Pista5 implements Pista{
	@Override
	public boolean EsAplicable(Casilla casilla, Tablero tablero) {
		if(casilla.getNumero() == 0) return false;
		
		int azules = tablero.mirarAlrededor(casilla.getPos(), 2);		
		return  azules < casilla.getNumero() && 
				buscarMuroModificable(casilla, tablero) != null;
	}
	
	Casilla buscarMuroRec(Casilla casilla, Tablero tablero, Vector2D dir) {
		Vector2D nuevaPos = new Vector2D(casilla.getPos().getX()+ dir.getX(),casilla.getPos().getY()+ dir.getY());
		if( nuevaPos.getX() < 0 || nuevaPos.getX() >= tablero.getDimensiones() || nuevaPos.getY() < 0 || nuevaPos.getY() >= tablero.getDimensiones())   //Si me he salido de cualquier limite
			return null;
		
		Casilla nuevaCasilla = tablero.getTablero()[nuevaPos.getX()][nuevaPos.getY()]; 
		if(nuevaCasilla.getTipoActual() == Tipo.ROJO && !nuevaCasilla.esModificable())          // Si me he encontrado un muro que no puedo alterar
			return null;
		
		else if(nuevaCasilla.getTipoActual() == Tipo.ROJO && nuevaCasilla.esModificable())
			return nuevaCasilla;
		
		return buscarMuroRec(nuevaCasilla, tablero, dir);
	}
	Casilla buscarMuroModificable(Casilla casilla, Tablero tablero) {
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		for(int i = 0; i < 4 ; i++) {
			Casilla aux = buscarMuroRec(casilla, tablero, dir[i]);
			if(aux != null) return aux;
		}
		return null;
	}

	@Override
	public void AplicarPista(Casilla casilla, Tablero tablero) {
		// No es aplicable
	}

	@Override
	public String GenerarAyuda() {
		return "El número tiene una cantidad insuficiente de casillas azules visibles y sin embargo está cerrado.";
	}

}
