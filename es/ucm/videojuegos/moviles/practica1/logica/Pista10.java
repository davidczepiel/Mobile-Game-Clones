package logica;

import logica.Casilla.Tipo;

public class Pista10 implements Pista{
	  
	@Override
	public boolean EsAplicable(Casilla casilla, Tablero tablero) {
		if(casilla.getNumero() == 0)	return false; //Si no es un azul NO MODIFICABLE mo tenemos nada que hacer aqui
		
		//Miro que no esté cerrado
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		boolean encerrado = true;
		for(int i = 0; i < 4 ; i++) 
			encerrado = encerrado && mirarPared(casilla.getPos(),dir[i], tablero); //Si en algun momento no hay pared no estamos encerrados
		if(encerrado) return false;
		
		//Si las que tengo alrededor son INSUFICIENTES para llegar al número de puntos que debería ver 
		int numVeo = tablero.mirarAlrededor(casilla.getPos(), 2);
		return numVeo < casilla.getNumero();	
	}

	@Override
	public void AplicarPista(Casilla casilla, Tablero tablero) {
		//Cubierta por pista 3
	}

	
	private boolean mirarPared(Vector2D pos, Vector2D dir, Tablero tablero){
		Vector2D nuevaPos = new Vector2D(pos.getX()+ dir.getX(),pos.getY()+ dir.getY());
		if( nuevaPos.getX() < 0 || nuevaPos.getX() >= tablero.getDimensiones() || nuevaPos.getY() < 0 || nuevaPos.getY() >= tablero.getDimensiones() ||   	//Si me he salido de cualquier limite
				tablero.getTablero()[nuevaPos.getX()][nuevaPos.getY()].getTipoActual() == Tipo.ROJO)									  	// Si me he encontrado un muro
			return true;
		
		return false;		
	}
	
	@Override
	public String GenerarAyuda() {
		return "Un numero no esta cerrado y tiene varias direcciones, pero la suma alcanzable es el\r\n"
				+ "valor que hay que conseguir ";
	}
}
