package es.ucm.videojuegos.moviles.logica;
/*Clase que guarda la informacion necesaria para devolver a una casilla
* a su estado anterior. Es usada en Restore Manager con el fin de no guardar
* Casilla*/
public class RestoreCasilla {

    RestoreCasilla(Vector2D pos, Casilla.Tipo currentType){
        this._currentType = currentType;
        this._position = pos;
    }

    public Vector2D get_position() {
        return _position;
    }

    public Casilla.Tipo get_currentType() {
        return _currentType;
    }

    //Posicion dentro del tablero
    private Vector2D _position;
    //Tipo de la casilla que se quiere guardar
    private Casilla.Tipo _currentType;
}
