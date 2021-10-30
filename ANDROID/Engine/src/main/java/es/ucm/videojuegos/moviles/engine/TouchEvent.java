package es.ucm.videojuegos.moviles.engine;

public class TouchEvent {

    enum Type { pulsar, liberar, desplazar}
    /* Constructora de la clase
     * @param x posicion en X
     * @param y posicion en y
     * @param deltaX desplazamiento en X
     * @param deltaY desplazamiento en Y
     * @param id id del dedo */
    public TouchEvent(Type type, int x, int y, int deltaX, int deltaY, int id ){
        this._x = x;            this._y = y;
        this._deltaX = deltaX;  this._deltaY = deltaY;
        this._id = id;          this._type = type;
    }
    Type _type;
    //posicion
    private int _x;
    private int _y;
    //desplazamiento
    private int _deltaX;
    private int _deltaY;
    //identificador de android
    private int _id;

}
