package es.ucm.videojuegos.moviles.logica;

import es.ucm.videojuegos.moviles.engine.Engine;

public interface Scene {
    /*Realiza la inicialización del objeto (inicialización en dos pasos).*/
    void onInit(Engine g);
    /*Realiza actualizaciones de la aplicación y comprueba estados*/
    void onUpdate(double deltaTime);
    /*Realiza el dibujado de la aplicacion*/
    void onDraw();
    /*Devuelve el nombre de la aplicacion*/
}
