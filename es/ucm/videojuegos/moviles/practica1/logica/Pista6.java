package logica;

import logica.Casilla.Tipo;

public class Pista6 implements Pista{
   
	@Override
	public boolean EsAplicable(Casilla casilla, Tablero tablero) {
		if(casilla.getTipoActual() != Tipo.VACIO) return false;
		
		return  buscaAzulNoMod(casilla, tablero) == null; //Si no se ha encontrtado nada es que estamos ante una pared cerrada
	}

	@Override
	public void AplicarPista(Casilla casilla, Tablero tablero) {
		casilla.setTipo(Tipo.ROJO);
	}

	@Override
	public String GenerarAyuda() {
		return "Hay una celda vacia que no ve ninguna azul, por lo que debería ser una pared";
	}
	
	private Casilla buscaAzulNoModRec(Vector2D pos, Vector2D dir, Tablero tablero){
		Vector2D nuevaPos = new Vector2D(pos.getX()+ dir.getX(),pos.getY()+ dir.getY());
		if( nuevaPos.getX() < 0 || nuevaPos.getX() >= tablero.getDimensiones() || nuevaPos.getY() < 0 || nuevaPos.getY() >= tablero.getDimensiones() ||   	//Si me he salido de cualquier limite
				tablero.getTablero()[nuevaPos.getX()][nuevaPos.getY()].getTipoActual() == Tipo.ROJO)									  	// Si me he encontrado un muro
			return null;
		
		if(tablero.getTablero()[nuevaPos.getX()][nuevaPos.getY()].getTipoActual() == Tipo.AZUL &&
				tablero.getTablero()[nuevaPos.getX()][nuevaPos.getY()].getNumero() > 0 )
			return tablero.getTablero()[nuevaPos.getX()][nuevaPos.getY()];
		
		return buscaAzulNoModRec(nuevaPos,dir,tablero);
	}
	
	Casilla buscaAzulNoMod(Casilla casilla, Tablero tablero) {
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		for(int i = 0; i < 4 ; i++) {
			Casilla aux = buscaAzulNoModRec(casilla.getPos(),dir[i], tablero);
			if(aux != null) return aux;
		}
		return null;
	}
	

}
