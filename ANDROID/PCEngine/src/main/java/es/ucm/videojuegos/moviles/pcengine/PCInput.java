package es.ucm.videojuegos.moviles.pcengine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import es.ucm.videojuegos.moviles.engine.Input;
import es.ucm.videojuegos.moviles.engine.Pool;
import es.ucm.videojuegos.moviles.engine.TouchEvent;
import es.ucm.videojuegos.moviles.engine.TouchEvent.TouchEventType;
import es.ucm.videojuegos.moviles.engine.Graphics;

public class PCInput implements Input, MouseListener{

    //Esta constante se utiliza para solventar el problema de que (0,0) en la ventana del JFrame no coincide
    //con el borde de la pantalla dibujado en el eje de las X's
    private static final int OFFSET_JFRAME_X = 7;

    //Numero de listas que usa el input para almacenar los eventos y hacer swap cada vez que la logica pida una lista de eventos (siempre 2)
    private static final int numLists = 2;

    public PCInput(Graphics g){
        this._lists = new ArrayList<>();

        //Creamos las listas que haran swap en ejecucion
        for(int i = 0; i < numLists; ++i){
            this._lists.add(new ArrayList<TouchEvent>());
        }
        this._activeListIndex = 0;

        this._pool = new Pool(40);
        this._graphics = g;
    }

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

    @Override
    synchronized public void addEvent(TouchEvent e) {
        _lists.get(this._activeListIndex).add(e);
    }

    //+-----------------------------------------------------------------------+
    //|                       Interfaz Mouse Listener                         |
    //+-----------------------------------------------------------------------+
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        TouchEvent event = _pool.getTouchEvent();
        if(event == null) return;

        //Calculo de la posicion x e y donde empieza el canvas logico
        int xLogicCanvas = (_graphics.getWidth() - _graphics.getLogicCanvasWidth()) / 2;
        int yLogicCanvas = (_graphics.getHeigth() - _graphics.getLogicCanvasHeight()) / 2;

        //No procesamos el evento si esta fuera del canvas logico
        if(e.getX() < xLogicCanvas || e.getX() > xLogicCanvas + _graphics.getLogicCanvasWidth() ||
                e.getY() < yLogicCanvas || e.getY() > yLogicCanvas + _graphics.getLogicCanvasHeight()) return;

        //Definimos el tipo del TouchEvent
        event.set_type(TouchEventType.pulsar);

        //Calculamos las coordenadas en nativo de la pulsacion
        int nativeX = _graphics.getWidthNativeCanvas() * (e.getX() - xLogicCanvas) / _graphics.getLogicCanvasWidth();
        int nativeY = _graphics.getHeigthNativeCanvas() * (e.getY() - yLogicCanvas) / _graphics.getLogicCanvasHeight();

        //Incorporamos el evento a la lista
        event.set_x(nativeX - OFFSET_JFRAME_X);
        event.set_y(nativeY);
        addEvent(event);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }

    protected Pool _pool;
    protected List<List<TouchEvent>> _lists;
    protected int _activeListIndex;

    private Graphics _graphics;
}
