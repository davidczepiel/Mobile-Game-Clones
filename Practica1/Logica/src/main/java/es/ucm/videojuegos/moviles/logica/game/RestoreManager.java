package es.ucm.videojuegos.moviles.logica.game;

import java.util.Stack;

import es.ucm.videojuegos.moviles.logica.board.Square;

/*Clase encargada de guardar las casillas que han sido modificadas antes de su modificacion,
* con el fin de devolver el último cambio realizado*/
public class RestoreManager {

    public RestoreManager() {
        casillaQueue = new Stack<>();
    };
    /*Aniade a la cola de casillas la casilla dada
    * @param casilla Casilla sobre la que se quiere almacenar informacion*/
    public void addCasilla(Square square){
        RestoreSquare aux = new RestoreSquare(square.getPos(), square.getCurrentType());
        casillaQueue.add(aux);
    }

    /*Devuelve la ultima casilla antes de haber sido modificada
    * En caso de no haber cambios devuelve null
    * @return Informacion de la siguiente casilla a deshacer o null*/
    public RestoreSquare getLastCasilla(){
        if(casillaQueue.empty()) return null;
        return casillaQueue.pop();
    }
    private Stack<RestoreSquare> casillaQueue; //cola de casillas que han sido modificadas
}
