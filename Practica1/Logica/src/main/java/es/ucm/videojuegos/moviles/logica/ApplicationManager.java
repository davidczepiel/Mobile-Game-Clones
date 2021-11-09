package es.ucm.videojuegos.moviles.logica;

import com.sun.org.apache.bcel.internal.generic.Select;

import java.util.ArrayList;
import java.util.List;

import es.ucm.videojuegos.moviles.engine.Application;

/*Clase utilizada para guardar y crear las instancias de las aplicaciones con el fin de
 *reutilizar las aplicaciones de los menus y no crear nuevas instancias.
 */
public class ApplicationManager {

    public enum Scene{ MainMenu, SelectMenu, OhNo}
    public ApplicationManager(){
        _applications = new ArrayList<>();
        _applications.add(new MainMenu(this));
        _applications.add(new SelectMenu(this));
    }
    /*Devuelve la aplicacion a la que se quiere cambiar.
    * @param scene posicion en donde se encuentra almacenada la escena
    * @param sizeBoard tamanio del tablero a instanciar en caso de querer una Aplicacion con OhNo.
    * En caso de no querer este tipo de aplicacion el valor puesto no afecta en nada
    * @return devuelve la aplicacion que se ha elegido. En caso de querer OhNo se debe de especificar
    * el tamanio con un sizeBoard > 1. En caso contrario devuelve null*/
    public Application getAplication(Scene scene, int sizeBoard){
        if(scene == Scene.OhNo){
            if(sizeBoard > 1)
                return new OhNoGame(sizeBoard, this);
            else
                return null;
        }
        return this._applications.get(scene.ordinal());
    }


    List<Application> _applications;    //array donde se guardan tres aplicaciones
}
