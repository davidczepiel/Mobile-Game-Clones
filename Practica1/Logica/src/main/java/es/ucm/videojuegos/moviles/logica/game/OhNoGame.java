package es.ucm.videojuegos.moviles.logica.game;

import es.ucm.videojuegos.moviles.engine.Application;
import es.ucm.videojuegos.moviles.engine.Engine;

/*Clase que implementa el juego*/
public class OhNoGame implements Application {
    /*Crea el sceneManager y carga todos los recursos*/
    @Override
    public void onInit(Engine g) {
        this._sceneManager = new SceneManager(g);
        this._sceneManager.chargeResources();
        this._sceneManager.getCurrentScene().onInit(g);
    }
    /*Update de la escena actual*/
    @Override
    public void onUpdate(double deltaTime) {
        this._sceneManager.getCurrentScene().onUpdate(deltaTime);
    }
    /*Draw de la escena actual*/
    @Override
    public void onDraw() {
        this._sceneManager.getCurrentScene().onDraw();
    }

    @Override
    public String getName() {
        return "OhNoGame";
    }

    SceneManager _sceneManager;
}
