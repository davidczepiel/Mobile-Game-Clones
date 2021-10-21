package logica;

import java.util.Random;

import logica.Casilla.Tipo;

public class Tablero {

    public Tablero(int tam){

        _dimensiones = tam;
        _juegoTablero = new Casilla[_dimensiones][_dimensiones];
        _solucionTablero = new Casilla[_dimensiones][_dimensiones];
        _gestorDePistas = new GestorDePistas();
        _numVacias = tam * tam;
        do {
        	generarTablero();
    	} while(esValido());
        
        limpiarTableroJuego();       
    }
    
    /* Miramos cuantos azules hay alrededor de una casilla hasta encontrar una pared
	 * @param pos (actual desde la cual se mira en el tablero)
	 * @param tablero 0 indica que miran azules utilizando el tablerode la solucion;
	 * 1 indica que se mira en el tablero del juego y se cuentan azules teniendo en cuenta los vacios;
	 * y diferente a 0 y 1 que se mira en el juego sin importar los vacios*/
    public int mirarAlrededor(Vector2D pos, int tablero){
        Vector2D[] dir = {new Vector2D(1,0),new Vector2D(-1,0),new Vector2D(0,1),new Vector2D(0,-1)};
        int numVistos =0;
        for(int i =0 ;i < 4; i++){
        	if(tablero == 0)
        		numVistos += mirarAlrededorRecursivoEnSolucion(pos,dir[i]);
        	else if(tablero == 1)
        		numVistos += mirarAlrededorRecursivoParcial(pos,dir[i]);
        	else 
        		numVistos += mirarAlrededorRecursivoEnJuego(pos,dir[i]);
        }
        return numVistos;
    }
    /*Mira en el tablero del juego de manera recursiva dada una posicción y una dirección el número de azules.
     * Deja de contar al encontrarse con un rojo o con un vacío
     * @param pos inicial desde la cual se busca
     * @param dir en la que se buscan azules*/
    public int mirarAlrededorRecursivoParcial(Vector2D pos, Vector2D dir){
        Vector2D nuevaPos = new Vector2D(pos.getX()+ dir.getX(),pos.getY()+ dir.getY());
        if( nuevaPos.getX() < 0 || nuevaPos.getX() >= _dimensiones || nuevaPos.getY() < 0 || nuevaPos.getY() >= _dimensiones ||   	//Si me he salido de cualquier limite
		_juegoTablero[nuevaPos.getX()][nuevaPos.getY()].getTipoActual() == Tipo.ROJO ||										  	// Si me he encontrado un muro
        _juegoTablero[nuevaPos.getX()][nuevaPos.getY()].getTipoActual() == Tipo.VACIO)											// Si me he encontrado con un vacío
            return 0;
        return 1 + mirarAlrededorRecursivoParcial(nuevaPos,dir);
    }
    /* Dada una dirección, busca el último espacio vacio sin paredes de por medio
	 * @param dir dirección en la que buscará un sospechoso
	 * @param tablero (del juego)
	 * @param pos posición de la casilla desde la que partir */
	public Casilla buscarPrimerVacio(Vector2D dir, Vector2D pos) {
	    Vector2D nuevaPos = new Vector2D(pos.getX()+ dir.getX(),pos.getY()+ dir.getY());
	    
	    if( nuevaPos.getX() < 0 || nuevaPos.getX() >= _dimensiones ||  						//Si me he salido por las X's
		nuevaPos.getY() < 0 || nuevaPos.getY() >= _dimensiones ||   						//Si me he salido por las Y's
		_juegoTablero[nuevaPos.getX()][nuevaPos.getY()].getTipoActual() == Tipo.ROJO)			//La siguiente es roja
		  return null;
		
	    else if (_juegoTablero[nuevaPos.getX()][nuevaPos.getY()].getTipoActual() == Tipo.AZUL)	//La siguiente es azul
	    	return buscarPrimerVacio(dir, nuevaPos);
	    else	
	    	return _juegoTablero[nuevaPos.getX()][nuevaPos.getY()];
	}
    
    /* Modifica el tipo de la casilla dado su tipo actual
	 * @param casilla que se modifica*/
    public void modificarCasilla(Casilla casilla) {
    	switch(casilla.getTipoActual()) {
    	case ROJO:
    		casilla.setTipo(Tipo.VACIO); 	
    		_numVacias++;
    		break;
    	case AZUL:
    		casilla.setTipo(Tipo.ROJO);    		
    		break;
    	case VACIO:
    		casilla.setTipo(Tipo.AZUL);    		
    		_numVacias--;
    		break;

    	}
    }
    /* Suma el valor de vacías al actual valor
	 * @param mod modificador que se suma. Si es negativo se resta*/
    public void modificarVacias(int mod) { _numVacias+=mod;}
    /*Obtiene el número actual de casillas vacías en el tablero*/
    public int getNumVacias() { return _numVacias;}
    /*Obtiene la dimensión del tablero*/
    public int getDimensiones() { return _dimensiones;}
    /*Obtiene el tablero de juego*/
    public Casilla[][] getTablero() {return _juegoTablero;}
    
    /*Comprueba si el tablero generado es resoluble dadas las pistas
     * que proporciona el juego */
    private boolean esValido(){
    	if(!this._gestorDePistas.esValido(this))
    		return false;
    	//Vemos si la solución dada se corresponde con el tablero de la solcion
    	for(int i = 0; i < this._dimensiones ; i++) {
			for(int j = 0; j < this._dimensiones ; j++) {
				if(this._solucionTablero[i][j] != this._juegoTablero[i][j]) return false;
			}
		}    	
    	return true;
    }
    /*Genera un tablero de manera aleatoria. Siendo un 75% azules y un 25% rojos*/
    private void generarTablero(){
        Random rand = new Random();
                
        for(int i=0; i < _dimensiones; i++){
            for(int j=0; j < _dimensiones; j++){
            	//Rellenamos el tablero del juego con vacíos
                _juegoTablero[i][j] = new Casilla(Tipo.VACIO,0,true, new Vector2D(i,j),this);	
                int caso = rand.nextInt(4);
                //Azul
                if(caso < 2)
                    _solucionTablero[i][j] = new Casilla(Tipo.AZUL,0,false, new Vector2D(i,j),this);		

                //Rojo
                else
                	_solucionTablero[i][j] = new Casilla(Tipo.ROJO,0,false, new Vector2D(i,j),this);                                    
            }
        }
        escogerCasillas();	//Escogemos casillas para ponerlas estáticas
        
    }
    
    /*Mira en el tablero de la solcion de manera recursiva dada una posicción y una dirección el número de azules.
     * Usado para calcular el número en los no modificables a la hora de generar el tablero
     * Deja de contar al encontrarse con un rojo
     * @param pos inicial desde la cual se busca
     * @param dir en la que se buscan azules*/
    private int mirarAlrededorRecursivoEnSolucion(Vector2D pos, Vector2D dir){
        Vector2D nuevaPos = new Vector2D(pos.getX()+ dir.getX(),pos.getY()+ dir.getY());
        if( nuevaPos.getX() < 0 || nuevaPos.getX() >= _dimensiones || nuevaPos.getY() < 0 || nuevaPos.getY() >= _dimensiones ||   //Si me he salido de cualquier limite
            _solucionTablero[nuevaPos.getX()][nuevaPos.getY()].getTipoActual() == Tipo.ROJO)                                      		// Si me he encontrado un muro
            return 0;
        return 1 + mirarAlrededorRecursivoEnSolucion(nuevaPos,dir);
    }
    
    /*Mira en el tablero del juego de manera recursiva dada una posicción y una dirección el número de azules.
     * Deja de contar al encontrarse con un rojo
     * @param pos inicial desde la cual se busca
     * @param dir en la que se buscan azules*/
    private int mirarAlrededorRecursivoEnJuego(Vector2D pos, Vector2D dir){
        Vector2D nuevaPos = new Vector2D(pos.getX()+ dir.getX(),pos.getY()+ dir.getY());
        if( nuevaPos.getX() < 0 || nuevaPos.getX() >= _dimensiones || nuevaPos.getY() < 0 || nuevaPos.getY() >= _dimensiones ||   	//Si me he salido de cualquier limite
		_juegoTablero[nuevaPos.getX()][nuevaPos.getY()].getTipoActual() == Tipo.ROJO)									  	// Si me he encontrado un muro
            return 0;
        return 1 + mirarAlrededorRecursivoEnJuego(nuevaPos,dir);
    }
    /*Escoge de manera aleatoria */
    private void escogerCasillas(){
    	
        int azules =  _dimensiones*_dimensiones/4;
        int rojos =  _dimensiones*_dimensiones/4;
           
        Random rand = new Random();
        //Mientras que haya azules que posicionar, busco una posición aleatoria
        // y añado al tablero del juego siempre que sea posible
        while(azules>0){
            int posX=rand.nextInt(_dimensiones);
            int posY=rand.nextInt(_dimensiones);
            if(_solucionTablero[posX][posY].getTipoActual() == Tipo.AZUL &&		//Si en la solución es azul
            _juegoTablero[posX][posY].getTipoActual() != Tipo.AZUL){			//Si no he visitado ya esta casilla
                _juegoTablero[posX][posY].setModificable(false);
                _juegoTablero[posX][posY].setTipo(Tipo.AZUL);
                _juegoTablero[posX][posY].setNumero(mirarAlrededor(new Vector2D(posX,posY),0));
                azules--;
            }
        }
        //Mientras que haya rojos que posicionar, busco una posición aleatoria
        // y añado al tablero del juego siempre que sea posible
        while(rojos>0){
            int posX=rand.nextInt(_dimensiones);
            int posY=rand.nextInt(_dimensiones);
            if(_solucionTablero[posX][posY].getTipoActual() == Tipo.ROJO && 	//Si en la solución es azul
    	    _juegoTablero[posX][posY].getTipoActual() != Tipo.ROJO){			//Si no he visitado ya esta casilla
                _juegoTablero[posX][posY].setModificable(false);
                _juegoTablero[posX][posY].setTipo(Tipo.ROJO);
                rojos--;
            }
        }
        
        //Actualizo el valor de las casillas vacías
        _numVacias = (_dimensiones*_dimensiones) - rojos - azules; 
    }

    /*Vuelve a poner el tablero del juego a su estado original.
     *Este método se llama después de comprobar que el tablero funcione. */
    private void limpiarTableroJuego() {
    	for(int i = 0; i < this._dimensiones ; i++) {
			for(int j = 0; j < this._dimensiones ; j++) {
				if(this._juegoTablero[i][j].esModificable())
					this._juegoTablero[i][j].setTipo(Tipo.VACIO);
			}
		}    	
    }
    
    //Dimensiones del tablero 
    private int _dimensiones;
    //Numero de casillas que se encuentran vacias
    private int _numVacias;
    //Array que representa las distintas casillas del tablero
    private Casilla[][] _juegoTablero;
    private Casilla [][] _solucionTablero;
    private GestorDePistas _gestorDePistas;

}