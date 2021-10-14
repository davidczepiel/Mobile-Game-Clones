package logica;

public class Pista4 implements Pista{

	@Override
	public boolean EsAplicable(Casilla casilla, Tablero tablero) {
		if(casilla.getNumero() == 0) return false;
		
		int azules = tablero.mirarAlrededor(casilla.getPos(), 2);
		return azules > casilla.getNumero();
	}

	@Override
	public void AplicarPista(Casilla casilla, Tablero tablero) {
		//Esta pista no es aplicable, es meramente informativa
	}

	@Override
	public String GenerarAyuda() {
		return "Esta casilla tiene más casillas azules visibles de las que debería";
	}
	
}
