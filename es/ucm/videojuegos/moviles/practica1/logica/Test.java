package logica;

public class Test {
	
	public static void main(String [] args) {
		Tablero tablero = new Tablero(2);
		Casilla[][] juegoTablero = tablero.getTablero();
	 	for(int i = 0; i < 5 ; i++) {
			for(int j = 0; j < 5 ; j++) {
				switch(juegoTablero[i][j].getTipoActual()) {
		    	case ROJO:
		    		System.out.print("X ");
		    	case AZUL:
		    		System.out.print("O ");
		    		break;
		    	case VACIO:
		    		System.out.print("-- ");
		    		break;
		    	}
			}
			System.out.print("\n");
		}    
	}
	
}
