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
    public PCInput(Graphics g){
        this._list = new ArrayList<>();
        this._pool = new Pool(30);
        this._graphics = g;
    }
    @Override
    synchronized public List<TouchEvent> getTouchEvents() {
        List<TouchEvent> l = new ArrayList<>();
        for (TouchEvent e: this._list) {
            l.add(e);
        }
        this._list.clear();
        return l;
    }

    @Override
    synchronized public void addEvent(TouchEvent e) {
        _list.add(e);
    }

    @Override
    public void clearEvents(List<TouchEvent> l) {
        _pool.setNotUsed(l);
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

        int nativeX = _graphics.getWidthNativeCanvas() * (e.getX() - xLogicCanvas) / _graphics.getLogicCanvasWidth();
        int nativeY = _graphics.getHeigthNativeCanvas() * (e.getY() - yLogicCanvas) / _graphics.getLogicCanvasHeight();

        event.set_x(nativeX);
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
    protected List<TouchEvent> _list;

    private Graphics _graphics;
}
