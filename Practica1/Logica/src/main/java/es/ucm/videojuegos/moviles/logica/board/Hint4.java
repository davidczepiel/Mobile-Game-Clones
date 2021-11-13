package es.ucm.videojuegos.moviles.logica.board;

public class Hint4 implements Hint {

	@Override
	public boolean isApplicable(Square square, Board board) {
		if(square.getNumber() == 0) return false;
		
		int azules = board.lookAround(square.getPos(), 1);
		return azules > square.getNumber();
	}

	@Override
	public void applyHint(Square square, Board board) {
		//Esta pista no es aplicable, es meramente informativa
	}

	@Override
	public String generateHelp() {
		return "Esta casilla tiene mas- casillas azules visibles- de las que deberia";
	}
	
}
