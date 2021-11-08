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

    /*Metodo que devuelve la posicion de la casilla en el tablero
    * @return posicion en el tablero*/
    public Vector2D getPos()  { return _posicion;}

    /*Metodo que permite establecer la posicion de la casilla dentro del tablero
     *  a una concreta*/
    public void setPos( Vector2D nuevaPos) { _posicion = nuevaPos;}

    /*Metodo que devuelve de que tipo es la casilla
    * @return tipo de la casilla (ROJO,AZUL,VACIO)*/
    public Tipo getTipoActual() { return _miTipoActual;}


    /*Modifica el tipo de la casilla actual y actualiza el numero de vacias del tablero
    * si su tipo es vacio o su tipo ya modificado tambien lo es
    * @param tipo Tipo que queremos que la casilla pase a ser*/
    public void setTipo(Tipo tipo) 
    {
    	if(_miTipoActual == Tipo.VACIO && tipo != Tipo.VACIO)
    		_tablero.modificarVacias(-1);
    	else if(_miTipoActual != Tipo.VACIO && tipo == Tipo.VACIO)
    		_tablero.modificarVacias(1);
    	
		_miTipoActual = tipo; 
    	
    }

    /*Metodo que devuelve si la casilla se puede modificar
    * @return boolean que dice si la casilla se puede modificar o no*/
    public boolean esModificable()  { return _modificable;}

    /*Metodo que permite establecer si la casilla se puede modificar
    * @param boolean que establece si la casilla se puede modificar o no*/
    public void setModificable( boolean m) { _modificable = m;}

    /*Metodo que devuelve el numero de casillas azules que esta casilla es capaz de ver
    * @return numero de azules que esta casilla ve*/
    public int getNumero()  { return _numero;}

    /*Permite establecer el numero de casillas azules que esta casilla ve
    * @param m Numero de casillas azules que esta casilla ve*/
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