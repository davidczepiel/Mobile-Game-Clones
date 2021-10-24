package logica;

import logica.Casilla.Tipo;

public class Pista7 implements Pista{

    
	@Override
	public boolean EsAplicable(Casilla casilla, Tablero tablero) {
		if( casilla.getNumero() != 0 || (casilla.getTipoActual() != Tipo.AZUL)) return false;
		
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		for(int i = 0; i < 4 ; i++) {
			boolean aux = mirarPared(casilla.getPos(),dir[i], tablero);
			if(!aux) return false;
		}	
		return true;
	}

	@Override
	public void AplicarPista(Casilla casilla, Tablero tablero) {
		casilla.setTipo(Tipo.ROJO);
	}

	@Override
	public String GenerarAyuda() {
		return "Hay una celda azul cerrada que no ve ninguna otra azul, lo cual no es posible";
	}

	private boolean mirarPared(Vector2D pos, Vector2D dir, Tablero tablero){
		Vector2D nuevaPos = new Vector2D(pos.getX()+ dir.getX(),pos.getY()+ dir.getY());
		if( nuevaPos.getX() < 0 || nuevaPos.getX() >= tablero.getDimensiones() || nuevaPos.getY() < 0 || nuevaPos.getY() >= tablero.getDimensiones() ||   	//Si me he salido de cualquier limite
				tablero.getTablero()[nuevaPos.getX()][nuevaPos.getY()].getTipoActual() == Tipo.ROJO)									  	// Si me he encontrado un muro
			return true;
		
		return false;		
	}
}

