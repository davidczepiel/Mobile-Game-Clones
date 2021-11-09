package es.ucm.videojuegos.moviles.logica;

/*Clase que guarda la informacion de una casilla del tablero*/
public class Square {

	/*Constructora de la clase*/
    Square(SquareType mSquareType, int num, boolean modificable, Vector2D pos, Board tab){
    	_myCurrentSquareType = mSquareType;
        _number =num;
        _modificable = modificable;
        _position = pos;
        _board = tab;

    }

	//Enum que especifica el estado de la casilla
	public enum SquareType {RED, BLUE, VOID};

    /*Metodo que devuelve la posicion de la casilla en el tablero
    * @return posicion en el tablero*/
    public Vector2D getPos()  { return _position;}

    /*Metodo que permite establecer la posicion de la casilla dentro del tablero
     *  a una concreta*/
    public void setPos( Vector2D newPos) { _position = newPos;}

    /*Metodo que devuelve de que tipo es la casilla
    * @return tipo de la casilla (ROJO,AZUL,VACIO)*/
    public SquareType getCurrentType() { return _myCurrentSquareType;}


    /*Modifica el tipo de la casilla actual y actualiza el numero de vacias del tablero
    * si su tipo es vacio o su tipo ya modificado tambien lo es
    * @param tipo Tipo que queremos que la casilla pase a ser*/
    public void setTipo(SquareType squareType)
    {
    	if(_myCurrentSquareType == SquareType.VOID && squareType != SquareType.VOID)
    		_board.modifyVoid(-1);
    	else if(_myCurrentSquareType != SquareType.VOID && squareType == SquareType.VOID)
    		_board.modifyVoid(1);
    	
		_myCurrentSquareType = squareType;
    	
    }

    /*Metodo que devuelve si la casilla se puede modificar
    * @return boolean que dice si la casilla se puede modificar o no*/
    public boolean is_modificable()  { return _modificable;}

    /*Metodo que permite establecer si la casilla se puede modificar
    * @param boolean que establece si la casilla se puede modificar o no*/
    public void setModificable( boolean m) { _modificable = m;}

    /*Metodo que devuelve el numero de casillas azules que esta casilla es capaz de ver
    * @return numero de azules que esta casilla ve*/
    public int getNumber()  { return _number;}

    /*Permite establecer el numero de casillas azules que esta casilla ve
    * @param m Numero de casillas azules que esta casilla ve*/
    public void setNumber(int m) { _number = m;}
    
    //Posicion dentro del tablero
    private Vector2D _position;
    
    private SquareType _myCurrentSquareType;
    
    //Numero de casillas que puedo ver desde mi posicion
    private int _number;

    //Representa si esta casilla se ve desde el principio de la partida o no
    private boolean _modificable;
    
    private Board _board;
    

}