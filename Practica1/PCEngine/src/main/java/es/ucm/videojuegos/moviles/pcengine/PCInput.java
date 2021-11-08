package es.ucm.videojuegos.moviles.pcengine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import es.ucm.videojuegos.moviles.engine.AbstractInput;
import es.ucm.videojuegos.moviles.engine.TouchEvent;
import es.ucm.videojuegos.moviles.engine.Pair;
import es.ucm.videojuegos.moviles.engine.TouchEvent.TouchEventType;
import es.ucm.videojuegos.moviles.engine.Graphics;

/*Clase que implementa el input para PC. Obtiene el input gracias a implementar las Interfaces de Mouse
* Listener y MouseMotion Listener. Contiene un buffer de la lista de eventos y una pool. Hace la transformacion
* de coordenadas a nativas.*/
public class PCInput extends AbstractInput implements MouseListener, MouseMotionListener {

    //Esta constante se utiliza para solventar el problema de que (0,0) en la ventana del JFrame no coincide
    //con el borde de la pantalla dibujado en el eje de las X's
    private static final int OFFSET_JFRAME_X = 7;

    public PCInput(Graphics g){
        super(g);
    }

    //+-----------------------------------------------------------------------+
    //|                       Interfaz Mouse Listener                         |
    //+-----------------------------------------------------------------------+

    /*Metodo de PC que obtiene un evento cuando el mouse presiona y libera el boton en cualquier lado de la pantalla
    * @param e Clase que almacena la informacion del evento que ha ocurrido*/
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /*Metodo de PC que obtiene un evento cuando el mouse presiona en cualquier lado de la pantalla
    * @param e Clase que almacena la informacion del evento que ha ocurrido*/
    @Override
    public void mousePressed(MouseEvent e) {
        TouchEvent event = _pool.getTouchEvent();
        if(event == null) return;
        //Definimos el tipo del TouchEvent
        event.set_type(TouchEventType.pulsar);

        Pair<Integer, Integer> pair = transformateCoord(e.getX(),e.getY());
        //Incorporamos el evento a la lista
        event.set_x(pair.getLeft() - OFFSET_JFRAME_X);
        event.set_y(pair.getRight());
        addEvent(event);
    }

    /*Metodo de PC que obtiene un evento cuando el mouse libera el boton en cualquier lado de la pantalla
    * @param e Clase que almacena la informacion del evento que ha ocurrido*/
    @Override
    public void mouseReleased(MouseEvent e) {
        TouchEvent event = _pool.getTouchEvent();
        if(event == null) return;
        //Definimos el tipo del TouchEvent
        event.set_type(TouchEventType.liberar);

        Pair<Integer, Integer> pair = transformateCoord(e.getX(),e.getY());
        //Incorporamos el evento a la lista
        event.set_x(pair.getLeft() - OFFSET_JFRAME_X);
        event.set_y(pair.getRight());
        addEvent(event);
    }


    /*Metodo de PC que obtiene un evento cuando el mouse ha entrado en la pantalla
    * @param e Clase que almacena la informacion del evento que ha ocurrido*/
    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    /*Metodo de PC que obtiene un evento cuando el mouse ha salido de la pantalla
    * @param e Clase que almacena la informacion del evento que ha ocurrido*/
    @Override
    public void mouseExited(MouseEvent e) {
        //
    }

    /*Metodo de PC que obtiene un evento cuando el mouse esta presionando y moviendose a la vez
     * en cualquier lado de la pantalla
     * @param e Clase que almacena la informacion del evento que ha ocurrido*/
    @Override
    public void mouseDragged(MouseEvent e) {
        TouchEvent event = _pool.getTouchEvent();
        if(event == null) return;
        //Definimos el tipo del TouchEvent
        event.set_type(TouchEventType.desplazar);

        Pair<Integer, Integer> pair = transformateCoord(e.getX(),e.getY());
        //Incorporamos el evento a la lista
        event.set_x(pair.getLeft() - OFFSET_JFRAME_X);
        event.set_y(pair.getRight());
        addEvent(event);
    }

    /*Metodo de PC que obtiene un evento cuando el mouse se ha movido dentro de la pantalla
    * @param e Clase que almacena la informacion del evento que ha ocurrido*/
    @Override
    public void mouseMoved(MouseEvent e) {
        //
    }

}
