package es.ucm.videojuegos.moviles.logica.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.ucm.videojuegos.moviles.logica.board.Board;
import es.ucm.videojuegos.moviles.logica.board.Square;

/*Clase encargada de manejar los fade-ins y fade-outs de las casillas y de las pistas mostradas*/
public class FadingManager {

    private static final float FADING_VEL = 7f;

    public FadingManager(Board board){
        this._board = board;
        this._fadingVelocity = FADING_VEL;
        this._squaresFading = new HashMap<Square, FadingInfo>();
    }

    /*Registrar una nueva casilla para hacer el fade*/
    public void startFading(Square square){
        //Si esta casilla ya estaba previamente en fading, la modificamos para empezar con el siguiente fade
        if(this._squaresFading.get(square) != null && !this._squaresFading.get(square).hasChanged())
            this._board.modifySquare(square);

        this._squaresFading.put(square, new FadingInfo());
    }

    /*Metodo para actualizar el fade de cada casilla registrada y modificar su color si ha alcanzado la mitad del fade*/
    public void updateFadings(double deltaTime){
        List<Square> squaresToRemove = new ArrayList<Square>();
        for (Map.Entry<Square, FadingInfo> entry : this._squaresFading.entrySet()) {
            FadingInfo squareFade = entry.getValue();
            //Sumamos el tiempo que ha pasado al fade
            squareFade.addFading(this._fadingVelocity * (float)deltaTime);

            //Comprobamos su el estado de fading de la casilla (0 -> comienzo, 2 -> final)
            //Si su fading ya ha concluido, ya no hay nada mas que hacer
            if(squareFade.getFading() >= 2f){
                squaresToRemove.add(entry.getKey());
                continue;
            }
            //Si ha llegado a la mitad del fading, cambiamos su color
            else if(squareFade.getFading() >= 1f && !squareFade.hasChanged()){
                this._board.modifySquare(entry.getKey());
                squareFade.changed();
            }
        }

        for(int i = 0; i < squaresToRemove.size(); ++i)
            this._squaresFading.remove(squaresToRemove.get(i));
    }

    /*Devuelve el fade actual de la casilla*/
    public float getFading(Square square){
        //Si esa casilla no esta registrada se devuelve 1 (opaco)
        if(this._squaresFading.get(square) == null) return 1f;

        //El fade va de 0 a 2, siendo 1 el punto donde cambia de color
        //Devolvemos el (valor - 1) para indicar el alpha que le toca independientemente de su color
        float f = Math.abs(this._squaresFading.get(square).getFading() - 1f);
        return f;
    }

    private Board _board;
    //Velocidad del fade
    private float _fadingVelocity;
    //Lista de casillas que tienen que actualizar su fading
    private Map<Square, FadingInfo> _squaresFading;
}

/*Clase para guardar informacion sobre el fading de una casilla*/
class FadingInfo{
    public FadingInfo(){
        this._changedType = false;
        this._currentFading = 0f;
    }

    public float getFading(){
        return this._currentFading;
    }

    public boolean hasChanged(){
        return this._changedType;
    }

    public void addFading(float fade){
        this._currentFading += fade;
    }

    public void changed(){
        this._changedType = true;
    }

    //Estado del fading
    private float _currentFading;
    //Indica si ya se ha modificado la casilla al siguiente color
    private boolean _changedType;
}
