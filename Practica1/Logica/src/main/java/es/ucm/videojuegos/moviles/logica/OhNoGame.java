package es.ucm.videojuegos.moviles.logica;

import java.util.List;
import es.ucm.videojuegos.moviles.engine.Application;
import es.ucm.videojuegos.moviles.engine.Engine;
import es.ucm.videojuegos.moviles.engine.Font;
import es.ucm.videojuegos.moviles.engine.Image;
import es.ucm.videojuegos.moviles.engine.Graphics;
import es.ucm.videojuegos.moviles.engine.Pair;
import es.ucm.videojuegos.moviles.engine.TouchEvent;

/*Clase que implementa el juego*/
public class OhNoGame implements Application {

    @Override
    public void onInit(Engine g) {
        this._sceneManager = new SceneManager(g);
        this._sceneManager.chargeResources();
        this._sceneManager.getCurrentScene().onInit(g);
    }

    @Override
    public void onUpdate(double deltaTime) {
        this._sceneManager.getCurrentScene().onUpdate(deltaTime);
    }

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
