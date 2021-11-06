package es.ucm.videojuegos.moviles.logica;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

/*Clase encargada de guardar las casillas que han sido modificadas antes de su modificacion,
* con el fin de devolver el último cambio realizado*/
public class RestoreManager {

    public RestoreManager() {
        casillaQueue = new Stack<>();
    };
    /*Aniade a la cola de casillas la casilla dada*/
    public void addCasilla(Casilla casilla){
        RestoreCasilla aux = new RestoreCasilla(casilla.getPos(),casilla.getTipoActual());
        casillaQueue.add(aux);
    }
    /*Devuelve la ultima casilla antes de haber sido modificada
    * En caso de no haber cambios devuelve null*/
    public RestoreCasilla getLastCasilla(){
        if(casillaQueue.empty()) return null;
        return casillaQueue.pop();
    }
    private Stack<RestoreCasilla> casillaQueue; //cola de casillas que han sido modificadas
}
