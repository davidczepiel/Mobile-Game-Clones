package es.ucm.videojuegos.moviles.logica;

import es.ucm.videojuegos.moviles.logica.Casilla.Tipo;

public class Pista3 implements Pista{

	Vector2D _dir;
	
	@Override
	public boolean EsAplicable(Casilla casilla, Tablero tablero) {
		if(casilla.getNumero() == 0)	return false;
		//Cuantos azules veo a mi alrededor
		int numVeo = tablero.mirarAlrededor(casilla.getPos(), 1);
		if(numVeo == casilla.getNumero()) return false;
		
		System.out.print("Es aplicable pista 3 en " + casilla.getPos().getX() + ", " + casilla.getPos().getY() + "\n");
		Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
		int[] vaciosVeo = {0,0,0,0}; 
		int posibilidades = 0;
		int faltan = casilla.getNumero() - numVeo;
		
		int vaciosDisponibles = 0;
		for(int i = 0; i < 4 ; ++i) {
			vaciosVeo[i] = contarVacios(casilla.getPos(), dir[i], false, tablero);
			vaciosDisponibles += vaciosVeo[i];
		}
		
		for(int i = 0; i < dir.length; ++i) {
			if(vaciosDisponibles - vaciosVeo[i] < faltan) {
				posibilidades++;
				_dir = dir[i];
			}
		}
		
		if(posibilidades == 1)
			return true;
		
		return false;
	}

	
	@Override
	public void AplicarPista(Casilla casilla, Tablero tablero) {
		Casilla aux = tablero.buscarPrimerVacio(_dir, casilla.getPos());
		aux.setTipo(Tipo.AZUL);
	}
	
	

	@Override
	public String GenerarAyuda() {
		return "Si no se coloca un azul en -una posicion vacia no es -posible alcanzar el numero- de azules visibles";
	}
	
	int contarVacios(Vector2D pos,Vector2D dir, boolean contando,  Tablero tablero) {
		Vector2D nuevaPos = new Vector2D(pos.getX()+ dir.getX(),pos.getY()+ dir.getY());
		if( (nuevaPos.getX() < 0 || nuevaPos.getX() >= tablero.getDimensiones() || nuevaPos.getY() < 0 || nuevaPos.getY() >= tablero.getDimensiones()) ||
				tablero.getTablero()[nuevaPos.getX()][nuevaPos.getY()].getTipoActual() == Tipo.ROJO ||
				contando  && tablero.getTablero()[nuevaPos.getX()][nuevaPos.getY()].getTipoActual() == Tipo.AZUL )   //Si me he salido de cualquier limite
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
