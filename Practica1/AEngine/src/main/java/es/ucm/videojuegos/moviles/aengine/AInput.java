package es.ucm.videojuegos.moviles.aengine;

import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintSet;

import es.ucm.videojuegos.moviles.engine.AbstractInput;
import es.ucm.videojuegos.moviles.engine.Graphics;
import es.ucm.videojuegos.moviles.engine.Pair;
import es.ucm.videojuegos.moviles.engine.TouchEvent;


/*Clase que implementa el input para Android. Obtiene el input gracias a implementar la Interfaces de
 * View.OnTouchListener. Contiene un buffer de la lista de eventos y una pool. Hace la transformacion
 * de coordenadas a nativas.*/
public class AInput extends AbstractInput implements View.OnTouchListener {
    public AInput(Graphics g){
        super(g);
    }

    /*Metodo que es notificado cada vez que ocurre un evento de input en la pantalla
    * Los tipos de eventos que es capaz de procesar son pulsar la pantalla, dejar de pulsar y
    * arrastrar el dedo
    * @return boolean que indica si hemos procesado el input que android nos ha dado*/
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        boolean eventProcessed=true;

        //Pregunto por la accion (no uso getAction porque eso incluye info del index culpable, este metodo solo informa de la accion,
        // posteriormente obtendo el index)
        switch(event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                eventProcessed=processTouchInput(event, TouchEvent.TouchEventType.pulsar,event.getActionIndex());
                break;

            case MotionEvent.ACTION_UP:
                eventProcessed=processTouchInput(event, TouchEvent.TouchEventType.liberar,event.getActionIndex());
                break;

            case MotionEvent.ACTION_MOVE:
                //Como no disponemos de un caso especifico en al accion de mover para cuando estamos con multitouch
                //estamos obligados a mirar cuantos dedos hay presionando la pantalla y decir que todos ellos se acaban de mover
                //porque si preguntamos por el index del dedo que ha causado la accion siempre obtendremos el index del primer
                //dedo que toco la pantalla, a pesar de que pueda haber sido el segundo o el tercero el que se haya movido
                for(int i =0; i<event.getPointerCount();i++)
                    eventProcessed=processTouchInput(event,TouchEvent.TouchEventType.desplazar,i);
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                eventProcessed=processTouchInput(event, TouchEvent.TouchEventType.pulsar,event.getActionIndex());
                break;

            case MotionEvent.ACTION_POINTER_UP:
                eventProcessed=processTouchInput(event, TouchEvent.TouchEventType.liberar,event.getActionIndex());
                break;
        }

        //true si hemos consumido el evento
        return eventProcessed;
    }

    private boolean processTouchInput(MotionEvent event, TouchEvent.TouchEventType type, int index){
        TouchEvent ev = _pool.getTouchEvent();
        if(ev == null) return false; //false porque no hemos podido procesar el input

        //Posiciones en X/Y dentro de la seccion pintable (sin contar la banda superior)
        float xLogic=event.getX(index);
        float yLogic=event.getY(index);
        Pair<Integer, Integer> pair = transformateCoord((int) xLogic, (int) yLogic);

        //Devolvemos true porque hemos sido capaces de procesarlo, sin embargo no nos interesa hacer caso
        //si se encuentra fuera del canvas
        if(pair == null) return true;

        //Incorporamos el evento a la lista
        ev.set_type(type);
        ev.set_x(pair.getLeft());
        ev.set_y(pair.getRight());
        ev.set_id(event.getPointerId(index));
        addEvent(ev);
        return true;
    }
}