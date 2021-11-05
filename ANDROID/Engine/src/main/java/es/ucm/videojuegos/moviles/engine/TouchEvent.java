package es.ucm.videojuegos.moviles.engine;

public class TouchEvent {

    public enum TouchEventType { pulsar, liberar, desplazar}
    /* Constructora de la clase
     * @param x posicion en X
     * @param y posicion en y
     * @param deltaX desplazamiento en X
     * @param deltaY desplazamiento en Y
     * @param id id del dedo */
    public TouchEvent(TouchEventType type, int x, int y, int deltaX, int deltaY, int id){
        this._x = x;            this._y = y;
        this._deltaX = deltaX;  this._deltaY = deltaY;
        this._id = id;          this._type = type;
    }

    public TouchEventType get_type() {
        return _type;
    }

    public void set_type(TouchEventType _type) {
        this._type = _type;
    }

    public int get_x() {
        return _x;
    }

    public void set_x(int _x) {
        this._x = _x;
    }

    public int get_y() {
        return _y;
    }

    public void set_y(int _y) {
        this._y = _y;
    }

    public int get_deltaX() {
        return _deltaX;
    }

    public void set_deltaX(int _deltaX) {
        this._deltaX = _deltaX;
    }

    public int get_deltaY() {
        return _deltaY;
    }

    public void set_deltaY(int _deltaY) {
        this._deltaY = _deltaY;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    TouchEventType _type;
    //posicion
    private int _x;
    private int _y;
    //desplazamiento
    private int _deltaX;
    private int _deltaY;
    //identificador de android
    private int _id;
}
