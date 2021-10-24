package logica;

import logica.Casilla.Tipo;

public class Pista8 implements Pista{
	  
	@Override
	public boolean EsAplicable(Casilla casilla, Tablero tablero) {
		if(casilla.getNumero() == 0 || casilla.getTipoActual() != Tipo.AZUL) return false;
		
		int numVeo = tablero.mirarAlrededor(casilla.getPos(), 1);
		
		if(numVeo >= casilla.getNumero()) return false;
		
		int direccionesAbiertas = 0;
		
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		for(int i = 0; i < dir.length; ++i) {
			if(contarVacios(casilla.getPos(), dir[i], false, tablero) > 0) {
				direccionesAbiertas++;
			}
		}
		
		return direccionesAbiertas == 1;
	}

	@Override
	public void AplicarPista(Casilla casilla, Tablero tablero) {
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		for(int i = 0; i < dir.length; ++i) {
			Casilla aux = casilla;
			while(contarVacios(casilla.getPos(), dir[i], false, tablero) > 0) {
				Vector2D nuevaPos = new Vector2D(aux.getPos().getX()+ dir[i].getX(),
						aux.getPos().getY()+ dir[i].getY());
				tablero.getTablero()[nuevaPos.getX()][nuevaPos.getY()].setTipo(Tipo.AZUL);
				aux = tablero.getTablero()[nuevaPos.getX()][nuevaPos.getY()];
			}
		}
		System.out.append("Pista 8 aplicada\n");
	}

	@Override
	public String GenerarAyuda() {
		return "Un numero que no ve suficientes puntos no esta cerrado aun y solo tiene abierta una direccion";
	}
	
	int contarVacios(Vector2D pos,Vector2D dir, boolean contando,  Tablero tablero) {
		Vector2D nuevaPos = new Vector2D(pos.getX()+ dir.getX(),pos.getY()+ dir.getY());
		if( (nuevaPos.getX() < 0 || nuevaPos.getX() >= tablero.getDimensiones() || nuevaPos.getY() < 0 || nuevaPos.getY() >= tablero.getDimensiones()) ||
			tablero.getTablero()[nuevaPos.getX()][nuevaPos.getY()].getTipoActual() == Tipo.ROJO ||
			(contando  && tablero.getTablero()[nuevaPos.getX()][nuevaPos.getY()].getTipoActual() == Tipo.AZUL))   //Si me he salido de cualquier limite
			return 0;
		
		else if(!contando && tablero.getTablero()[nuevaPos.getX()][nuevaPos.getY()].getTipoActual() == Tipo.AZUL)
			return contarVacios(nuevaPos, dir, contando, tablero);
		
		else if(tablero.getTablero()[nuevaPos.getX()][nuevaPos.getY()].getTipoActual() == Tipo.VACIO) {
			contando = true;
			return contarVacios(nuevaPos, dir, contando, tablero) + 1;
		}
		
		return 0;
	}

}
