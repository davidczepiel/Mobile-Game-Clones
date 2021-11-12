package es.ucm.videojuegos.moviles.engine;
/*Clase que implementa los los eventos. Guarda informacion del tipo de evento que ha
 * sido capturado, en que posicion y con que id. Ademas contiene metodos para modificar y
 * obtener los valores de estas variables
 */
public class TouchEvent {

    /*enum que representa los tipos de input que somos capaces de procesar*/
    public enum TouchEventType {
        touch,    //cuando se clica
        release,      //cuando se libera el dedo/click
        slide           //cuando ha habido un desplazamiento
    }
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

    /*Metodo que devuelve el typo de evento de input que se ha procesado*/
    public TouchEventType get_type() {
        return _type;
    }

    /*Metodo que permite establecer el tipo de evento de input que estamos procesando
    * @param _type tipo de evento que queremos almacenar*/
    public void set_type(TouchEventType _type) {
        this._type = _type;
    }

    /*Metodo que devuelve la posicion en X en la que ha ocurrido el input*/
    public int get_x() {
        return _x;
    }

    /*Metodo que devuelve la posicion en Y en la que ha ocurrido el input*/
    public int get_y() {
        return _y;
    }

    /*Metodo que permite establecer la posicion en X en la que ha sucedido el input
    * @param _x posicion en x del input*/
    public void set_x(int _x) {
        this._x = _x;
    }

    /*Metodo que permite establecer la posicion en Y en la que ha sucedido el input
     * @param _y posicion en x del input*/
    public void set_y(int _y) {
        this._y = _y;
    }

    /*Metodo que permite obtener el desplazamiento en X que ha sucedido en el evento de input*/
    public int get_deltaX() {
        return _deltaX;
    }

    /*Metodo que permite establecer el desplazamiento en X que ha sucedido en el evento de input
     * @param _deltaX desplazamiento en x del input*/
    public void set_deltaX(int _deltaX) {
        this._deltaX = _deltaX;
    }

    /*Metodo que permite obtener el desplazamiento en Y que ha sucedido en el evento de input*/
    public int get_deltaY() {
        return _deltaY;
    }

    /*Metodo que permite establecer el desplazamiento en Y que ha sucedido en el evento de input
     * @param _deltaY desplazamiento en y del input*/
    public void set_deltaY(int _deltaY) {
        this._deltaY = _deltaY;
    }

    /*Metodo que permite recibir el id asociado a cada evento de input*/
    public int get_id() {
        return _id;
    }

    /*Metodo  que permite establecer un id concreto a los eventos de input
    * @param _id id que se le quiere estalecer al evento*/
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
