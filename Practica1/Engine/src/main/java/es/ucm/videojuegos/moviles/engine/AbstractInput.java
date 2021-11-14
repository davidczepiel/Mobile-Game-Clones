package es.ucm.videojuegos.moviles.engine;

import java.util.ArrayList;
import java.util.List;
/*Clase encargada de tener una capa abstracta para cada una de las plataformas
* donde se realizan los calculos de transformacion de coordenadas y se recogen y aniaden
* los eventos a las listas de eventos.*/
public class AbstractInput implements Input{

    //Numero de listas que usa el input para almacenar los eventos y hacer swap cada vez que la logica pida una lista de eventos (siempre 2)
    private static final int numLists = 2;

    public AbstractInput(Graphics g){
        this._lists = new ArrayList<>();

        //Creamos las listas que haran swap en ejecucion
        for(int i = 0; i < numLists; ++i){
            this._lists.add(new ArrayList<TouchEvent>());
        }
        this._activeListIndex = 0;

        this._pool = new Pool(40);
        this._graphics = g;
    }
    /*Devuelve la lista de eventos sin procesar*/
    @Override
    synchronized public List<TouchEvent> getTouchEvents() {
        List<TouchEvent> l = this._lists.get(this._activeListIndex);

        //Cambiamos la lista activa a la siguiente
        this._activeListIndex = (this._activeListIndex + 1) % numLists;
        //Devolvemos todos los eventos de la anterior lista a la pool
        this._pool.setNotUsed(this._lists.get(this._activeListIndex));
        this._lists.get(this._activeListIndex).clear();

        return l;
    }
    /*Aniade un evento a la lista de eventos sin procesar*/
    @Override
    synchronized public void addEvent(TouchEvent e) {
        _lists.get(this._activeListIndex).add(e);
    }

    /*Devuelve un Pair(x,y) con las posiciones de raton transformadas a coordenadas nativas*/
    protected Pair transformateCoord(int x, int y){

        //Calculo de la posicion x e y donde empieza el canvas logico
        int xLogicCanvas = (_graphics.getWidth() - _graphics.getLogicCanvasWidth()) / 2;
        int yLogicCanvas = (_graphics.getHeigth() - _graphics.getLogicCanvasHeight()) / 2;

        //No procesamos el evento si esta fuera del canvas logico
        if(x < xLogicCanvas || x > xLogicCanvas + _graphics.getLogicCanvasWidth() ||
                y < yLogicCanvas || y > yLogicCanvas + _graphics.getLogicCanvasHeight())
            return null;
        //Calculamos las coordenadas en nativo de la pulsacion
        int nativeX = _graphics.getWidthNativeCanvas() * (x - xLogicCanvas) / _graphics.getLogicCanvasWidth();
        int nativeY = _graphics.getHeightNativeCanvas() * (y - yLogicCanvas) / _graphics.getLogicCanvasHeight();

        return new Pair<Integer,Integer>(nativeX,nativeY);
    }


    protected Pool _pool;                           //pool
    protected List<List<TouchEvent>> _lists;        //listas de eventos: una sera la que se recoja evetos y otra la que se procesa
    protected int _activeListIndex;                 //indice de la lista activa

    private Graphics _graphics;
}
