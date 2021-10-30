package es.ucm.videojuegos.moviles.logica;

import java.util.ArrayList;

public class GestorDePistas {

	public GestorDePistas() {
		this._pistasAplicables = new ArrayList<Pista>();
		this._pistasInformativas = new ArrayList<Pista>();
		
		Pista pista = new Pista1();
		this._pistasAplicables.add(pista);
		this._pistasInformativas.add(pista);
		
		pista = new Pista2();
		this._pistasAplicables.add(pista);
		this._pistasInformativas.add(pista);
		
		pista = new Pista8();
		this._pistasAplicables.add(pista);
		this._pistasInformativas.add(pista);
		
		pista = new Pista9();
		this._pistasAplicables.add(pista);
		this._pistasInformativas.add(pista);
		
		pista = new Pista10();
		this._pistasAplicables.add(pista);
		this._pistasInformativas.add(pista);
		
		pista = new Pista3();
		this._pistasAplicables.add(pista);
		this._pistasInformativas.add(pista);
		
		pista = new Pista6();
		this._pistasInformativas.add(pista);
		this._pistasAplicables.add(pista);
		
		pista = new Pista7();
		this._pistasInformativas.add(pista);
		this._pistasAplicables.add(pista);
		//-----------------------------------
		pista = new Pista4();
		this._pistasInformativas.add(pista);
		
		pista = new Pista5();
		this._pistasInformativas.add(pista);
		
		

		
	}
	
	public boolean esValido(Tablero tablero) {
		Casilla[][] tableroJuego = tablero.getTablero();
		int size = tablero.getDimensiones();
		while(tablero.getNumVacias() > 0) {
			boolean checkPista = false;
			for(int i = 0; i < size ; i++) {
				for(int j = 0; j < size ; j++) {
					checkPista = pruebaPista(tableroJuego[i][j], tablero);
					if(checkPista) {
						for(int x = 0; x < size ; x++) {
			    			for(int y = 0; y < size ; y++) {
			    				switch(tablero.getTablero()[x][y].getTipoActual()) {
				    		    	case ROJO:
				    		    		System.out.print("X ");
				    		    		break;
				    		    	case AZUL:
				    		    		System.out.print(tablero.getTablero()[x][y].getNumero() + " ");
				    		    		break;
				    		    	case VACIO:
				    		    		System.out.print("- ");
				    		    		break;
			    		    	}			
			    			}
	    		    		System.out.print("\n");
						}
    		    		System.out.print("////////////////////////// \n");
						break;
					}
				}
				if(checkPista) break;
			}
			if(!checkPista) return false;
			
		}
		return true;
	}
	
	public String damePista(Tablero tablero) {
		Casilla[][] tableroJuego = tablero.getTablero();
		int size = tablero.getDimensiones();
		for(int i = 0; i < size ; i++) {
			for(int j = 0; j < size ; j++) {
				for(Pista pista: this._pistasInformativas) {
					if(pista.EsAplicable(tableroJuego[i][j], tablero))
						return pista.GenerarAyuda();
				}
			}
		}
		return null;
	}
	private boolean pruebaPista(Casilla casilla, Tablero tablero) {
		for(Pista pista: this._pistasAplicables) {
			if(pista.EsAplicable(casilla, tablero)) {
				pista.AplicarPista(casilla, tablero);	
				return true;
			}
		}
		return false;
	}
	private ArrayList<Pista> _pistasInformativas;
	private ArrayList<Pista> _pistasAplicables;
}
