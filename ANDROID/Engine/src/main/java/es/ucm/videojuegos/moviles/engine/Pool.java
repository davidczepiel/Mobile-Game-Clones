package es.ucm.videojuegos.moviles.engine;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import es.ucm.videojuegos.moviles.engine.TouchEvent.TouchEventType;
import es.ucm.videojuegos.moviles.engine.TouchEvent;

public class Pool {
    /*Constructora de la clase
    * @params num numero de elementos que tiene la pool
    */
    public Pool(int num){
        _pool = new ArrayList<>();
        _notUsed = new ArrayDeque<>();
        for(int i = 0; i< num; ++i){
            TouchEvent e = new TouchEvent(TouchEventType.desplazar,0,0,0,0,0);
            this._pool.add(e);
            this._notUsed.add(e);
        }
    }
    /*Devuelve un evento que no esté siendo utilizado*/
    synchronized public TouchEvent getTouchEvent() {
        if(!this._notUsed.isEmpty()){
            TouchEvent e = this._notUsed.poll();
            return e;
        }
        return null;
    }
    /* Aniade a la pool la lista dada
    * @params list lista de eventos.
    * */
    synchronized public void setNotUsed(List<TouchEvent> list){
        for (TouchEvent e: list) {
            _notUsed.add(e);
        }
    }

    public List<TouchEvent> get_pool() {
        return _pool;
    }
    private Queue<TouchEvent> _notUsed;
    private List<TouchEvent> _pool;
}
