package es.ucm.videojuegos.moviles.aengine;

import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;

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

        //Posiciones en X/Y dentro de la seccion pintable (sin contar la banda superior)
        float xLogic=event.getX();
        float yLogic=event.getY();

        //TODO considerar separar en metodos? en pc input esta en distintos metodos pero porque JFrame los separaba
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                TouchEvent ev = _pool.getTouchEvent();
                if(ev == null) return false; //false porque no hemos podido procesar el input

                ev.set_type(TouchEvent.TouchEventType.pulsar);
                Pair<Integer, Integer> pair = transformateCoord((int) xLogic, (int) yLogic);
                if(pair == null) return true;

                //Incorporamos el evento a la lista
                ev.set_x(pair.getLeft());
                ev.set_y(pair.getRight());
                ev.set_id(event.getDeviceId());
                addEvent(ev);
            }
                break;
            case MotionEvent.ACTION_UP: {
                TouchEvent ev = _pool.getTouchEvent();
                if(ev == null) return false; //false porque no hemos podido procesar el input

                ev.set_type(TouchEvent.TouchEventType.liberar);
                Pair<Integer, Integer> pair = transformateCoord((int) xLogic, (int) yLogic);
                if(pair == null) return true;

                //Incorporamos el evento a la lista
                ev.set_x(pair.getLeft());
                ev.set_y(pair.getRight());
                ev.set_id(event.getDeviceId());
                addEvent(ev);
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                TouchEvent ev = _pool.getTouchEvent();
                if(ev == null) return false; //false porque no hemos podido procesar el input

                ev.set_type(TouchEvent.TouchEventType.desplazar);
                Pair<Integer, Integer> pair = transformateCoord((int) xLogic, (int) yLogic);
                if(pair == null) return true;

                //Incorporamos el evento a la lista
                ev.set_x(pair.getLeft());
                ev.set_y(pair.getRight());
                ev.set_id(event.getDeviceId());
                addEvent(ev);
            }
                break;
        }

        //true si hemos consumido el evento
        return true;
    }
}