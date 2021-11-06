package es.ucm.videojuegos.moviles.logica;

/*Clase que guarda la informacion de una casilla del tablero*/
public class Casilla {

	/*Constructora de la clase*/
    Casilla(Tipo mTipo, int num, boolean modificable, Vector2D pos, Tablero tab){
    	_miTipoActual = mTipo;
        _numero =num;
        _modificable = modificable;
        _posicion = pos;
        _tablero = tab;

    }

	//Enum que especifica el estado de la casilla
	public enum Tipo{ROJO, AZUL, VACIO};

    public Vector2D getPos()  { return _posicion;}
    public void setPos( Vector2D nuevaPos) { _posicion = nuevaPos;}
    
    public Tipo getTipoActual() { return _miTipoActual;}
    /*Modifica el tipo de la casilla actual y actualiza el numero de vacias del tablero
    * si su tipo es vacio o su tipo ya modificado tambien lo es*/
    public void setTipo(Tipo tipo) 
    {
    	if(_miTipoActual == Tipo.VACIO && tipo != Tipo.VACIO)
    		_tablero.modificarVacias(-1);
    	else if(_miTipoActual != Tipo.VACIO && tipo == Tipo.VACIO)
    		_tablero.modificarVacias(1);
    	
		_miTipoActual = tipo; 
    	
    }
    
    public boolean esModificable()  { return _modificable;}
    public void setModificable( boolean m) { _modificable = m;}
    
    public int getNumero()  { return _numero;}
    public void setNumero( int m) { _numero = m;}
    
    //Posicion dentro del tablero
    private Vector2D _posicion;
    
    private Tipo _miTipoActual;
    
    //Numero de casillas que puedo ver desde mi posicion
    private int _numero;

    //Representa si esta casilla se ve desde el principio de la partida o no
    private boolean _modificable;
    
    private Tablero _tablero;
    

}